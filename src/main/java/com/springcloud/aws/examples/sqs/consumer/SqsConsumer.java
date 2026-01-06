package com.springcloud.aws.examples.sqs.consumer;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class SqsConsumer {

    private static final String RECEIVE_MESSAGE = "Received message with MessageId: {} from SqsInvoiceCreationCommand: {}";

    @SqsListener(queueNames = "${application.aws.sqsProperties.queueName}", factory = "defaultSqsListenerContainerFactory")
    public void listen(@Header("id") UUID messageId, String message) {
        log.info(RECEIVE_MESSAGE, messageId, message);
    }

}
