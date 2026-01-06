package com.springcloud.aws.examples.s3.controller;

import com.springcloud.aws.examples.dynamodb.dto.MovieDTO;
import com.springcloud.aws.examples.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("s3")
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/client")
    public MovieDTO readFile() throws IOException {
        return s3Service.readFile();
    }

    @PostMapping("/client")
    public void uploadFile() {
        s3Service.uploadFile();
    }

    @DeleteMapping("/client")
    public void deleteFile() {
        s3Service.deleteFile();
    }

    @GetMapping("/resource")
    public MovieDTO readFileFromResource() throws IOException {
        return s3Service.readFileFromResource();
    }

    @GetMapping("/template")
    public MovieDTO readFileWithTemplate() {
        return s3Service.readFileWithTemplate();
    }

    @PostMapping("/template")
    public void uploadFileWithTemplate() throws FileNotFoundException {
        s3Service.uploadFileWithTemplate();
    }

    @DeleteMapping("/template")
    public void deleteFileWithTemplate() {
        s3Service.deleteFileWithTemplate();
    }
}
