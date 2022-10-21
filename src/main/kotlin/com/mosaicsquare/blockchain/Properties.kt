package com.mosaicsquare.blockchain

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.math.BigInteger

@ConstructorBinding
@ConfigurationProperties(prefix = "web3j")
data class Web3jProperties (
    val protocol: String,
    val clientAddress: ClientAddress,
    val contractAddress: ContractAddress,
    val startBlock: StartBlock,
    val credentials: Credentials
) {
    data class ClientAddress(
        val https: String,
        val wss: String
    )

    data class ContractAddress(
        val nft: String,
        val factory: String,
        val admin: String,
        val primaryMarket: String,
        val secondaryMarket: String
    )

    data class StartBlock (
        val number: BigInteger,
        val force: Boolean
    )

    data class Credentials(
        val privateKey: String
    )
}

@ConstructorBinding
@ConfigurationProperties(prefix = "cloud")
data class CloudProperties (
    val aws: Aws
) {
    data class Aws (
        val region: String,
        val sqs: Sqs,
        val credentials: Credentials
    ) {
        data class Sqs (
            val endPoint: String,
            val eth: Eth
        ) {
            data class Eth (
                val admin: String,
                val nft: String,
                val factory: String,
                val primaryMarket: String,
                val secondaryMarket: String
            )
        }

        data class Credentials (
            val accessKeyId: String,
            val secretAccessKey: String
        )
    }
}