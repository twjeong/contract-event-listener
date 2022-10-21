package com.mosaicsquare.blockchain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import kotlinx.coroutines.runBlocking
import org.web3j.protocol.Web3j
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.BaseEventResponse
import java.math.BigInteger
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class EventReceiver(
    private val defaultStartBlockNumber: BigInteger,
    private val forceStartBlock: Boolean,
    val contractEvent: MergedEventsFlowable,
    val queue: Queue,
    private val web3j: Web3j
){
    private val log: Logger = LogManager.getLogger(EventReceiver::class)
    private lateinit var latestBlockNumber: BigInteger

    fun processLastEvents() {

        log.info("Process last events of ${contractEvent.name} contract...")

        runBlocking {
            latestBlockNumber = when(forceStartBlock) {
                true -> defaultStartBlockNumber
                false -> queue.readBlockNumber() ?: defaultStartBlockNumber
            }
        }
        val currentBlockNumber = web3j.ethBlockNumber().send().blockNumber

        log.info("${queue.queueUrlVal} blockNumber: latest[$latestBlockNumber] current[$currentBlockNumber]")

        val events = mutableListOf<BaseEventResponse>()

        contractEvent.eventsFlowable(
            DefaultBlockParameter.valueOf(latestBlockNumber.add(BigInteger.ONE)),
            DefaultBlockParameter.valueOf(currentBlockNumber)
        ).forEach {
            it.subscribe { event ->
                events.add(event)
            }
        }

        events.sortBy { it.log.blockNumber }

        runBlocking {
            events.forEach {
                queue.sendMessage(makeEventToMessage(it), it.log.transactionHash)
                queue.writeBlockNumber(it.log.blockNumber)
            }
        }
    }

    fun listenEvents() {

        log.info("Listen to event of ${contractEvent.name}(${contractEvent.contract.contractAddress}) contract...")

        runBlocking {
            latestBlockNumber = queue.readBlockNumber() ?: web3j.ethBlockNumber().send().blockNumber
        }

        contractEvent.eventsFlowable(
            DefaultBlockParameter.valueOf(latestBlockNumber.add(BigInteger.ONE)),
            DefaultBlockParameterName.LATEST
        ).forEach {
            it.subscribe { event ->
                //GlobalScope.launch {
                runBlocking {
                    queue.sendMessage(makeEventToMessage(event), event.log.transactionHash)
                    queue.writeBlockNumber(event.log.blockNumber)
                }
            }
        }
    }

    private fun makeEventToMessage(event: BaseEventResponse) : String {

        val mapper = ObjectMapper()
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(SimpleModule()
                .addSerializer(BigInteger::class.java, ToStringSerializer())
            )

        val map: MutableMap<String, Any> = HashMap()

        val receipt = web3j.ethGetTransactionReceipt(event.log.transactionHash).send().transactionReceipt.get()
        val utc = web3j.ethGetBlockByNumber(
            DefaultBlockParameter.valueOf(event.log.blockNumber),
            false
        ).send().block.timestamp.longValueExact() * 1000

        map["name"] = event.javaClass.simpleName.substring(0, event.javaClass.simpleName.length - 13) // cut ...EventResponse
        map["transactionHash"] = event.log.transactionHash
        map["blockNumber"] = event.log.blockNumber.toString()
        map["contractAddress"] = event.log.address
        map["from"] = receipt.from
        map["timestamp"] = LocalDateTime.ofInstant(Instant.ofEpochMilli(utc), ZoneId.systemDefault()).toString()
        map["arguments"] = event

        val log = event.log
        event.log = null;
        val value = mapper.convertValue(map, JsonNode::class.java)
        event.log = log

        return mapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(value)
    }
}