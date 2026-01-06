package com.springcloud.aws.examples.sns;

import com.springcloud.aws.examples.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.retry.RetryMode;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.time.Duration;

/*
    This is a configuration example for more fine-tuned sns client
 */

@Configuration
@RequiredArgsConstructor
public class SnsClientConfig {

    private final AppConfig appConfig;

    //@Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                //.defaultsMode(DefaultsMode.STANDARD) provides some default configurations
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("accessKey", "secretAccessKey")))
                .overrideConfiguration(builder -> builder
                        .apiCallAttemptTimeout(Duration.ofSeconds(appConfig.getAws().getSns().getApiCallAttemptTimeout()))
                        .apiCallTimeout(Duration.ofSeconds(appConfig.getAws().getSns().getApiCallTimeout()))
                        .retryPolicy(RetryMode.STANDARD)) //can be a custom RetryPolicy
                .build();
    }

}
