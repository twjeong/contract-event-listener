package com.mosaicsquare.blockchain

import aws.sdk.kotlin.services.sqs.SqsClient
import aws.sdk.kotlin.services.sqs.model.*
import aws.smithy.kotlin.runtime.auth.awscredentials.CredentialsProvider
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.math.BigInteger
import java.time.LocalDateTime

class Queue (
    private val regionVal: String,
    private val credentials: CredentialsProvider,
    val queueUrlVal: String
) {

    private val log: Logger = LogManager.getLogger(Queue::class)

    private val sqsClient = SqsClient {
        region = regionVal
        credentialsProvider = credentials
    }

    suspend fun sendMessage(message: String, deduplicationId: String) {

        sqsClient.sendMessage (
            SendMessageRequest {
                queueUrl = queueUrlVal
                messageBody = message
                messageGroupId = "mos"
                messageDeduplicationId = deduplicationId
        })
        log.debug("$queueUrlVal: sendMessage: ${message.replace("\n", " ")}")
    }

    suspend fun receiveMessage(waitTimeSecondsVal: Int, maxNumberOfMessagesVal: Int) = sqsClient.receiveMessage(
        ReceiveMessageRequest {
            queueUrl = queueUrlVal
            waitTimeSeconds = waitTimeSecondsVal
            maxNumberOfMessages = maxNumberOfMessagesVal
        }
    )
    suspend fun receiveMessage() : ReceiveMessageResponse = receiveMessage(5, 10)

    suspend fun deleteMessage(message: Message) = sqsClient.deleteMessage(
        DeleteMessageRequest {
            queueUrl = queueUrlVal
            receiptHandle = message.receiptHandle
        }
    )

    suspend fun purgeQueue() = sqsClient.purgeQueue(
        PurgeQueueRequest {
            queueUrl = queueUrlVal
        }
    )

    suspend fun listQueueTags() : ListQueueTagsResponse {

        return sqsClient.listQueueTags(
            ListQueueTagsRequest {
                queueUrl = queueUrlVal
        })
    }

    suspend fun tagQueue(tagsVal: Map<String, String>) {

        sqsClient.tagQueue(
            TagQueueRequest {
                queueUrl = queueUrlVal
                tags = tagsVal
            }
        )
    }

    suspend fun readBlockNumber() : BigInteger? {
        return listQueueTags().tags?.get("latestBlockNumber")?.toBigInteger()
    }

    suspend fun writeBlockNumber(blockNumber: BigInteger) {
        tagQueue(mapOf("latestBlockNumber" to blockNumber.toString()))
    }

}