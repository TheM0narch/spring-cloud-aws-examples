package com.springcloud.aws.examples.ses.controller;

import com.springcloud.aws.examples.ses.service.MailSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ses")
@RequiredArgsConstructor
public class MailSendingController {

   public final MailSendingService mailSendingService;

    @PostMapping("/simple")
    public void sendSimpleMail() {
        mailSendingService.sendSimpleMailMessage();
    }

    @PostMapping("/mime")
    public void sendMimeMail() throws IOException {
        mailSendingService.sendMimeMailMessage();
    }

}
