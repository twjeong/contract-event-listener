package com.mosaicsquare.blockchain

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class StartupApplication (
    val adminReceiver: EventReceiver,
    val nftReceiver: EventReceiver,
    val factoryReceiver: EventReceiver,
    val primaryMarketReceiver: EventReceiver,
    val secondaryMarketReceiver: EventReceiver
) {
    private val log: Logger = LogManager.getLogger(StartupApplication::class)

    @PostConstruct
    fun init() {

        try {
            //adminReceiver.processLastEvents()
            factoryReceiver.processLastEvents()
            nftReceiver.processLastEvents()
            primaryMarketReceiver.processLastEvents()
            secondaryMarketReceiver.processLastEvents()

            //adminReceiver.listenEvents()
            factoryReceiver.listenEvents()
            nftReceiver.listenEvents()
            primaryMarketReceiver.listenEvents()
            secondaryMarketReceiver.listenEvents()
        }
        catch (e: Exception) {
            log.error(e.message)
        }
    }
}
