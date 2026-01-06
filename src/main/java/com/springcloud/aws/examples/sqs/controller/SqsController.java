package com.springcloud.aws.examples.sqs.controller;

import com.springcloud.aws.examples.sqs.dto.SqsMessageDTO;
import com.springcloud.aws.examples.sqs.service.SqsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("sqs")
public class SqsController {

    private final SqsService sqsService;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody SqsMessageDTO sqsMessageDTO) {
        sqsService.send(sqsMessageDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Message Send");
    }
}
