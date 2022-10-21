package com.mosaicsquare.blockchain

import aws.sdk.kotlin.services.sqs.model.ReceiveMessageResponse
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.util.AssertionErrors.assertEquals
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//@EnableConfigurationProperties(Web3jProperties::class, CloudProperties::class)
//@TestPropertySource("classpath:application.yml")
@ActiveProfiles("local")
class MosaicSquareBlockchainApplicationTests(
    private val admin: Admin,
    private val nft: Nft,
    private val factory: Factory,
    private val primaryMarket: PrimaryMarket,
    private val primaryMarketForBuyer: PrimaryMarket,
    private val adminQueue: Queue,
    private val factoryQueue: Queue,
    private val nftQueue: Queue,
    private val primaryMarketQueue: Queue,
    private val web3j: Web3j,
) {

    private val log: Logger = LogManager.getLogger(MosaicSquareBlockchainApplicationTests::class)
    val userAddress = "0xF76c9B7012c0A3870801eaAddB93B6352c8893DB"

    fun compareTransactionHashAndGetValue(queue: Queue, hash: String, key: String? = null) : String {

        var response: ReceiveMessageResponse

        runBlocking {
            log.debug("receive massage...")
            response = queue.receiveMessage(20, 1)
            log.debug("received massage")
        }

        val message = response.messages?.first()!!
        assertNotNull(message)
        assertEquals("match trxHash", hash, JSONObject(message.body).getString("transactionHash"))
        runBlocking {
            queue.deleteMessage(message)
        }

        return if (key == null) "" else
            JSONObject(message.body).getJSONObject("arguments").getString(key)
    }

    @Test
    fun `1차마켓 테스트`() {

        runBlocking {

            adminQueue.purgeQueue()
            factoryQueue.purgeQueue()
            nftQueue.purgeQueue()
            primaryMarketQueue.purgeQueue()
        }

        log.debug("current gasPrice: ${web3j.ethGasPrice().send().gasPrice}")

        assertEquals("isAdmin", admin.contract.isAdmin(userAddress).send(), true)

        log.debug("minting... ")
        var receipt = nft.contract.mintToMarketWithUriAndRoyalty(
            userAddress,
            userAddress,
            BigInteger("1000"),
            "aaa"
        ).send()

        log.debug("minted: ${receipt.transactionHash}")

        val tokenId = compareTransactionHashAndGetValue(nftQueue, receipt.transactionHash, "tokenId").toBigInteger()

        log.debug("setTreasury...")
        primaryMarket.contract.setTreasury(userAddress).send()
        log.debug("setTreasury")

        log.debug("createAuction...")
        receipt = primaryMarket.contract.createAuction(
            nft.contract.contractAddress,
            tokenId,
            userAddress,
            BigInteger("20000000000000000"),
            BigInteger.valueOf(System.currentTimeMillis() / 1000),
            BigInteger.valueOf(600),
            BigInteger.valueOf(1000)
        ).send()
        log.debug("createdAuction")

        compareTransactionHashAndGetValue(primaryMarketQueue, receipt.transactionHash, null)

        log.debug("placeBid...")
        receipt = primaryMarketForBuyer.contract.placeBid(
            nft.contract.contractAddress,
            tokenId,
            BigInteger("21000000000000000")
        ).send()
        log.debug("BidPlaced")

        compareTransactionHashAndGetValue(primaryMarketQueue, receipt.transactionHash, null)
    }


}
