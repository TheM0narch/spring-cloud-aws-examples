package com.springcloud.aws.examples;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "application")
@ConfigurationPropertiesScan
@Component
@Validated
public class AppConfig {

    private final Aws aws = new Aws();

    public Aws getAws() {return aws;}

    @ConfigurationProperties(prefix = "aws")
    @Data
    public static class Aws {
        private SqsProperties sqsProperties;
        private S3 s3;
        private Sns sns;
    }

    @Data
    public static class SqsProperties {

        @NotNull(message = "maxMessagesPerPoll should not be null")
        private Integer maxMessagesPerPoll;

        @NotNull(message = "messageVisibility should not be null")
        private Integer messageVisibility;

        @NotNull(message = "pollTimeout should not be null")
        private Integer pollTimeout;

        @NotNull(message = "maxConcurrentMessages should not be null")
        private Integer maxConcurrentMessages;

        @NotNull(message = "maxDelayBetweenPolls should not be null")
        private Integer maxDelayBetweenPolls;

        @NotNull(message = "queueName should not be null")
        private String queueName;

        @NotNull(message = "messageGroupId should not be null")
        private String messageGroupId;
    }

    @Data
    public static class S3 {

        @NotNull(message = "bucket should not be null")
        private String bucket;

        @NotNull(message = "apiCallAttemptTimeout should not be null")
        private Integer apiCallAttemptTimeout;

        @NotNull(message = "apiCallTimeout should not be null")
        private Integer apiCallTimeout;
    }

    @Data
    public static class Sns {

        @NotNull(message = "arn should not be null")
        private String arn;

        @NotNull(message = "apiCallAttemptTimeout should not be null")
        private Integer apiCallAttemptTimeout;

        @NotNull(message = "apiCallTimeout should not be null")
        private Integer apiCallTimeout;
    }

}
