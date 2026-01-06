package com.springcloud.aws.examples.sqs;

import com.springcloud.aws.examples.AppConfig;
import com.springcloud.aws.examples.exception.RetriableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.ListenerMode;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.listener.errorhandler.ErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SqsConsumerConfig {

    private final AppConfig appConfig;

    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory
                .builder()
                .configure(options -> options
                        .acknowledgementMode(AcknowledgementMode.ON_SUCCESS)
                        .maxMessagesPerPoll(1)
                        .messageVisibility(Duration.ofSeconds(appConfig.getAws().getSqsProperties().getMessageVisibility()))
                        .pollTimeout(Duration.ofSeconds(appConfig.getAws().getSqsProperties().getPollTimeout()))
                        .maxConcurrentMessages(appConfig.getAws().getSqsProperties().getMaxConcurrentMessages())
                        .maxDelayBetweenPolls(Duration.ofSeconds(appConfig.getAws().getSqsProperties().getMaxDelayBetweenPolls()))
                        .listenerMode(ListenerMode.SINGLE_MESSAGE)
                )
                .errorHandler(errorHandler())
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }

    @Bean
    public ErrorHandler<Object> errorHandler() {
        return new ErrorHandler<>() {
            @Override
            public void handle(Message<Object> message, Throwable t) {
                if (ExceptionUtils.getRootCause(t) instanceof RetriableException) {
                    log.info("Message with payload: {}, will not be acknowledged and is sent back to the sqs because: ", message.getPayload(), ExceptionUtils.getRootCause(t));
                    throw new RetriableException(ExceptionUtils.getRootCause(t).getMessage());
                } else {
                    log.info("Message with payload: {}, is not eligible for retries", message.getPayload(), ExceptionUtils.getRootCause(t));
                }
            }
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
