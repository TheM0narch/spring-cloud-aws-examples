package com.springcloud.aws.examples.sns.controller;

import com.springcloud.aws.examples.sns.service.SnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("sns")
public class SnsController {

    private final SnsService snsService;

    @PostMapping("/template")
    public void sendNotificationWithTemplate() {
        snsService.sendNotificationWithTemplate();
    }

    @PostMapping("/client")
    public void sendNotificationWithClient() {
        snsService.sendNotificationWithClient();
    }
}
