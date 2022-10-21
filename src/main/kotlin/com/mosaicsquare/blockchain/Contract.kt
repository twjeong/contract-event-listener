package com.mosaicsquare.blockchain

import io.reactivex.Flowable
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.response.BaseEventResponse
import org.web3j.tx.Contract

interface MergedEventsFlowable {

    val contract: Contract
    val name: String

    fun eventsFlowable(
        startBlock: DefaultBlockParameter,
        endBlock: DefaultBlockParameter
    ): List<Flowable<out BaseEventResponse>>
}

class Factory(factory: MosaicSquareFactory): MergedEventsFlowable {
    override val contract = factory
    override val name = "Factory"

    override fun eventsFlowable(
        startBlock: DefaultBlockParameter,
        endBlock: DefaultBlockParameter,
    ): List<Flowable<out BaseEventResponse>> = with(contract) {
        listOf(
            moneypipeCreatedEventFlowable(startBlock, endBlock),
            updatedMoneypipeEventFlowable(startBlock, endBlock)
        )
    }
}

class Admin(admin: MosaicSquareAdmin): MergedEventsFlowable {
    override val contract = admin
    override val name = "Admin"
    override fun eventsFlowable(
        startBlock: DefaultBlockParameter,
        endBlock: DefaultBlockParameter,
    ): List<Flowable<out BaseEventResponse>> = with(contract) {
        listOf(
            roleAdminChangedEventFlowable(startBlock, endBlock),
            roleGrantedEventFlowable(startBlock, endBlock),
            roleRevokedEventFlowable(startBlock, endBlock)
        )
    }
}

class Nft(nft: MosaicSquare): MergedEventsFlowable {
    override val contract = nft
    override val name = "Nft"
    override fun eventsFlowable(
        startBlock: DefaultBlockParameter,
        endBlock: DefaultBlockParameter,
    ): List<Flowable<out BaseEventResponse>> = with(contract) {
        listOf(
            mintedEventFlowable(startBlock, endBlock)
        )
    }
}

class PrimaryMarket(primaryMarket: MosaicSquare1stMarket): MergedEventsFlowable {

    override val contract = primaryMarket
    override val name = "PrimaryMarket"

    override fun eventsFlowable(
        startBlock: DefaultBlockParameter,
        endBlock: DefaultBlockParameter,
    ): List<Flowable<out BaseEventResponse>> = with(contract) {
        listOf(
            auctionCreatedEventFlowable(startBlock, endBlock),
            auctionBidPlacedEventFlowable(startBlock, endBlock),
            auctionFinalizedEventFlowable(startBlock, endBlock),
            auctionCanceledEventFlowable(startBlock, endBlock),
            fixedPriceSetEventFlowable(startBlock, endBlock),
            fixedPriceSoldEventFlowable(startBlock, endBlock),
            fixedPriceCanceledEventFlowable(startBlock, endBlock),
            withdrawPendingEventFlowable(startBlock, endBlock),
            withdrawalEventFlowable(startBlock, endBlock)
        )
    }
}

class SecondaryMarket(secondaryMarket: MosaicSquare2ndMarket): MergedEventsFlowable {

    override val contract = secondaryMarket
    override val name = "SecondaryMarket"

    override fun eventsFlowable(
        startBlock: DefaultBlockParameter,
        endBlock: DefaultBlockParameter,
    ): List<Flowable<out BaseEventResponse>> = with(contract) {
        listOf(
            offerMadeEventFlowable(startBlock, endBlock),
            offerAcceptedEventFlowable(startBlock, endBlock),
            offerRefundedEventFlowable(startBlock, endBlock),
            fixedPriceSetEventFlowable(startBlock, endBlock),
            fixedPriceSoldEventFlowable(startBlock, endBlock),
            fixedPriceCanceledEventFlowable(startBlock, endBlock),
            withdrawPendingEventFlowable(startBlock, endBlock),
            withdrawalEventFlowable(startBlock, endBlock),
            updateMinIncrementEventFlowable(startBlock, endBlock),
            updateMarketFeeEventFlowable(startBlock, endBlock),
            addDurationEventFlowable(startBlock, endBlock),
            removeDurationEventFlowable(startBlock, endBlock)
        )
    }
}