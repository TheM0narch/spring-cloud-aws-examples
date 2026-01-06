package com.springcloud.aws.examples.parameterstore.controller;

import com.springcloud.aws.examples.parameterstore.service.ParameterStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("parameterstore")
public class ParameterStoreController {

    private final ParameterStoreService parameterStoreService;

    @GetMapping("/client")
    public String getParameter() {
        return parameterStoreService.getParameter();
    }

    @GetMapping("/injected")
    public String getParameterFromInjectedProperty() {
        return parameterStoreService.getParameterFromInjectedProperty();
    }
}
