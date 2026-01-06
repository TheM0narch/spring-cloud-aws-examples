package com.springcloud.aws.examples.parameterstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest;
import software.amazon.awssdk.services.ssm.model.Parameter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParameterStoreService {

    private final SsmClient ssmClient;

    @Value("${test}")
    private String testValue;


    public String getParameter() {
        List<Parameter> parameterList = ssmClient.getParametersByPath(GetParametersByPathRequest.builder()
                .path("/spring-cloud-aws-examples").build()).parameters();

        return parameterList.get(0).value();
    }

    public String getParameterFromInjectedProperty() {
        return testValue;
    }


}
