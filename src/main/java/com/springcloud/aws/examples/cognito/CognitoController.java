package com.springcloud.aws.examples.cognito;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("cognito")
public class CognitoController {

    @GetMapping
    public String secureGet() {
        return "Hello securely!";
    }
}
