package com.springcloud.aws.examples.sqs.producer;

import com.springcloud.aws.examples.AppConfig;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SqsProducer extends GenericSqsProducer<String, String> {

    private final AppConfig appConfig;

    public SqsProducer(SqsTemplate sqsTemplate, AppConfig appConfig) {
        super(sqsTemplate);
        this.appConfig = appConfig;
    }

    @Override
    protected String getQueue() {
        return appConfig.getAws().getSqsProperties().getQueueName();
    }

    @Override
    protected String getMessageGroupId() {
        return appConfig.getAws().getSqsProperties().getMessageGroupId();
    }

    public void produceMessage(String key, String payload) {
        log.info("Publishing to sqs queue: {} with key: {}", getQueue(), key);

        super.publishMessage(key, payload);
    }
}
