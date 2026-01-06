package com.springcloud.aws.examples.sqs.service;

import com.springcloud.aws.examples.exception.ApplicationException;
import com.springcloud.aws.examples.sqs.dto.SqsMessageDTO;
import com.springcloud.aws.examples.sqs.producer.SqsProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SqsService {

    private static final String JSON_PARSING_ERROR_MESSAGE = "Something went wrong while transferring the Object: %s to json";

    private final SqsProducer sqsProducer;
    private final ObjectMapper objectMapper;

    public void send(SqsMessageDTO sqsMessageDTO) {

        String message;

        try {
            message = objectMapper.writeValueAsString(sqsMessageDTO);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(String.format(JSON_PARSING_ERROR_MESSAGE, sqsMessageDTO), e);
        }

        sqsProducer.produceMessage(String.valueOf(UUID.randomUUID()), message);
    }
}
