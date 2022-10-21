package com.mosaicsquare.blockchain

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.protocol.websocket.WebSocketService
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger

@Configuration
class Config (
    val web3jProperties: Web3jProperties,
    val cloudProperties: CloudProperties
){
    private val log: Logger = LogManager.getLogger(EventReceiver::class)

    private val httpService = HttpService(web3jProperties.clientAddress.https, false)
    private val webSocketService = WebSocketService(web3jProperties.clientAddress.wss, false)
    private var web3j: Web3j = when (web3jProperties.protocol) {
        "wss" -> {
            log.info("Connect ethereum node ${web3jProperties.clientAddress.wss}")
            webSocketService.connect()
            Web3j.build(webSocketService)
        }
        else -> {
            log.info("Connect ethereum node ${web3jProperties.clientAddress.https}")
            Web3j.build(httpService)
        }
    }
    private val web3jCredentials = Credentials.create(web3jProperties.credentials.privateKey)
    private val gasProvider = StaticGasProvider(BigInteger.valueOf(10_000_000_000L), DefaultGasProvider.GAS_LIMIT)
    //private val eiP1559GasProvider = StaticEIP1559GasProvider()
    private val awsCredentials = StaticCredentialsProvider {
        accessKeyId = cloudProperties.aws.credentials.accessKeyId
        secretAccessKey = cloudProperties.aws.credentials.secretAccessKey
    }

    @Bean
    fun web3j(): Web3j = web3j

    @Bean
    fun admin(): Admin = Admin(
        MosaicSquareAdmin.load(
            web3jProperties.contractAddress.admin,
            web3j, web3jCredentials, gasProvider
        )
    )

    @Bean
    fun nft(): Nft = Nft(
        MosaicSquare.load(
            web3jProperties.contractAddress.nft,
            web3j, web3jCredentials, gasProvider
        )
    )

    @Bean
    fun factory(): Factory = Factory(
        MosaicSquareFactory.load(
            web3jProperties.contractAddress.factory,
            web3j, web3jCredentials, gasProvider
        )
    )

    @Bean
    fun primaryMarket(): PrimaryMarket = PrimaryMarket(
        MosaicSquare1stMarket.load(
            web3jProperties.contractAddress.primaryMarket,
            web3j, web3jCredentials, gasProvider
        )
    )

    @Bean
    fun secondaryMarket(): SecondaryMarket = SecondaryMarket(
        MosaicSquare2ndMarket.load(
            web3jProperties.contractAddress.secondaryMarket,
            web3j, web3jCredentials, gasProvider
        )
    )

    @Bean
    fun primaryMarketForBuyer(): PrimaryMarket = PrimaryMarket(
        MosaicSquare1stMarket.load(
            web3jProperties.contractAddress.primaryMarket,
            web3j,
            Credentials.create("f3a2b6281569e73e73a2511c97bcc01c8bbf80acf18d23022b341c1631134de6"),
            gasProvider
        )
    )

    @Bean
    fun adminQueue(): Queue = Queue(
        cloudProperties.aws.region,
        awsCredentials,
        "${cloudProperties.aws.sqs.endPoint}/${cloudProperties.aws.sqs.eth.admin}"
    )
    @Bean
    fun nftQueue(): Queue = Queue(
        cloudProperties.aws.region,
        awsCredentials,
        "${cloudProperties.aws.sqs.endPoint}/${cloudProperties.aws.sqs.eth.nft}"
    )

    @Bean
    fun factoryQueue(): Queue = Queue(
        cloudProperties.aws.region,
        awsCredentials,
        "${cloudProperties.aws.sqs.endPoint}/${cloudProperties.aws.sqs.eth.factory}"
    )

    @Bean
    fun primaryMarketQueue(): Queue = Queue(
        cloudProperties.aws.region,
        awsCredentials,
        "${cloudProperties.aws.sqs.endPoint}/${cloudProperties.aws.sqs.eth.primaryMarket}"
    )

    @Bean
    fun secondaryMarketQueue(): Queue = Queue(
        cloudProperties.aws.region,
        awsCredentials,
        "${cloudProperties.aws.sqs.endPoint}/${cloudProperties.aws.sqs.eth.secondaryMarket}"
    )

    @Bean
    fun adminReceiver(): EventReceiver = EventReceiver(
        web3jProperties.startBlock.number,
        web3jProperties.startBlock.force,
        admin(),
        adminQueue(),
        web3j
    )

    @Bean
    fun nftReceiver(): EventReceiver = EventReceiver(
        web3jProperties.startBlock.number,
        web3jProperties.startBlock.force,
        nft(),
        nftQueue(),
        web3j
    )

    @Bean
    fun factoryReceiver(): EventReceiver = EventReceiver(
        web3jProperties.startBlock.number,
        web3jProperties.startBlock.force,
        factory(),
        factoryQueue(),
        web3j
    )

    @Bean
    fun primaryMarketReceiver(): EventReceiver = EventReceiver(
        web3jProperties.startBlock.number,
        web3jProperties.startBlock.force,
        primaryMarket(),
        primaryMarketQueue(),
        web3j
    )

    @Bean
    fun secondaryMarketReceiver(): EventReceiver = EventReceiver(
        web3jProperties.startBlock.number,
        web3jProperties.startBlock.force,
        secondaryMarket(),
        secondaryMarketQueue(),
        web3j
    )
}