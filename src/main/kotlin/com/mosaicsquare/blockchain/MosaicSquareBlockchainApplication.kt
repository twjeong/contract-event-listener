package com.mosaicsquare.blockchain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class MosaicSquareBlockchainApplication

fun main(args: Array<String>) {
    runApplication<MosaicSquareBlockchainApplication>(*args)
}
