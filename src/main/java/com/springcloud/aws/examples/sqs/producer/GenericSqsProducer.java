package com.springcloud.aws.examples.sqs.producer;

import com.springcloud.aws.examples.exception.ApplicationException;
import io.awspring.cloud.sqs.operations.MessagingOperationFailedException;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.util.concurrent.CompletionException;

@RequiredArgsConstructor
@Slf4j
public abstract class GenericSqsProducer<K, V> {

    private static final String FIFO_SUFFIX = ".fifo";
    private static final String CORRELATIONID_HEADER = "CorrelationId";

    private final SqsTemplate sqsTemplate;

    protected abstract String getQueue();

    protected abstract String getMessageGroupId();

    protected void publishMessage(K key, V value) {
        log.info("Produce message for key: {} with value: {} in queue: {}", key, value, getQueue());

        try {
            if (StringUtils.right(getQueue(), 5).equals(FIFO_SUFFIX)) {
                sendFifoMessage(key, value);
            } else {
                sendNonFifoMessage(key, value);
            }
        } catch (MessagingOperationFailedException | CompletionException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private SendResult<Object> sendFifoMessage(K key, V value) {
        return sqsTemplate.send(stringSqsSendOptions -> stringSqsSendOptions.queue(getQueue())
                .payload(value)
                .header(CORRELATIONID_HEADER, key)
                .messageGroupId(getMessageGroupId()));
                //messageDeduplicationId() can be added if the queue doesn't have ContentBasedDeduplication enabled
    }

    private SendResult<Object> sendNonFifoMessage(K key, V value) {
        return sqsTemplate.send(stringSqsSendOptions -> stringSqsSendOptions.queue(getQueue())
                .payload(value)
                .header(CORRELATIONID_HEADER, key));
    }
}
