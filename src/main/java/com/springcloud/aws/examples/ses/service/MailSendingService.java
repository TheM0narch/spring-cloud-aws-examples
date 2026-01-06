package com.springcloud.aws.examples.ses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class MailSendingService {

    private final String TEST_FILE_PATH = "src/main/resources/templates/test.txt";
    private final String CAT_FILE_PATH = "src/main/resources/templates/cat.html";

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;

    public void sendSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("senderEmail");
        simpleMailMessage.setTo("recipientEmail");
        simpleMailMessage.setSubject("test subject");
        simpleMailMessage.setText("test text");
        mailSender.send(simpleMailMessage);
    }

    public void sendMimeMailMessage() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        String catFileContent = Files.readString(Path.of(CAT_FILE_PATH), StandardCharsets.UTF_8);

        javaMailSender.send(mimeMessage -> {
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.addTo("recipientEmail");
            helper.setFrom("senderEmail");
            helper.addAttachment("test.txt", testFile);
            helper.setSubject("test subject with attachment");
            helper.setText(catFileContent, true);
        });
    }
}
