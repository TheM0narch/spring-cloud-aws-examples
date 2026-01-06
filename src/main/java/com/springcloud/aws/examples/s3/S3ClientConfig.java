package com.springcloud.aws.examples.s3;

import com.springcloud.aws.examples.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryMode;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class S3ClientConfig {

    private final AppConfig appConfig;

    //@Bean
    public S3Client s3ClientClient() {
        return S3Client.builder()
                //.defaultsMode(DefaultsMode.STANDARD) provides some default configurations
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("accessKey", "secretAccessKey")))
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                        .apiCallTimeout(Duration.ofSeconds(appConfig.getAws().getS3().getApiCallAttemptTimeout()))
                        .apiCallAttemptTimeout(Duration.ofSeconds(appConfig.getAws().getS3().getApiCallTimeout()))
                        .retryPolicy(RetryMode.STANDARD)
                        .build())
                .serviceConfiguration(S3Configuration.builder() //provides some specific S3 configurations
                        .accelerateModeEnabled(true)
                        .build())
                .build();
    }

}
