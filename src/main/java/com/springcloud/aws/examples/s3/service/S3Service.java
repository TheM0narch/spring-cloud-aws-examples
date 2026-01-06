package com.springcloud.aws.examples.s3.service;

import com.springcloud.aws.examples.AppConfig;
import com.springcloud.aws.examples.dynamodb.dto.MovieDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final File testFile = new File("src/main/resources/templates/movieFromJava.json");

    private final S3Client s3Client;
    private final S3AsyncClient s3AsyncClient;
    private final S3Template s3Template;
    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;

    @Value("s3://tsetsi-movie-bucket}/test.json")
    Resource s3Resource;

    public MovieDTO readFile() throws IOException {
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(
                request -> request.bucket(appConfig.getAws().getS3().getBucket()).key(testFile.getName()));

        return objectMapper.readValue(StreamUtils.copyToString(response, StandardCharsets.UTF_8), MovieDTO.class);
    }

    public MovieDTO readFileFromResource() throws IOException {
        InputStream inputStream = s3Resource.getInputStream();

        return objectMapper.readValue(StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8), MovieDTO.class);
    }


    public void uploadFile() {
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(appConfig.getAws().getS3().getBucket())
                .key(testFile.getName())
                .build(), RequestBody.fromFile(testFile));
    }

    public void deleteFile() {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(appConfig.getAws().getS3().getBucket())
                .key(testFile.getName())
                .build());
    }


    //For more information regarding the CTR client - https://aws.amazon.com/blogs/developer/introducing-crt-based-s3-client-and-the-s3-transfer-manager-in-the-aws-sdk-for-java-2-x/
    public void writeFileCTR() {
        s3AsyncClient.putObject(PutObjectRequest.builder()
                .bucket(appConfig.getAws().getS3().getBucket())
                .key(testFile.getName()).build(), AsyncRequestBody.fromFile(testFile)
        );
    }

    public MovieDTO readFileWithTemplate() {
        return s3Template.read(appConfig.getAws().getS3().getBucket(), testFile.getName(), MovieDTO.class);
    }

    public void uploadFileWithTemplate() throws FileNotFoundException {
        s3Template.upload(appConfig.getAws().getS3().getBucket(), testFile.getName(), new FileInputStream(testFile));
    }

    public void deleteFileWithTemplate() {
        s3Template.deleteObject(appConfig.getAws().getS3().getBucket(), testFile.getName());
    }

}
