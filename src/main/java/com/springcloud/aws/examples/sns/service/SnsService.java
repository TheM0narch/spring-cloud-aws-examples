package com.springcloud.aws.examples.sns.service;

import com.springcloud.aws.examples.AppConfig;
import com.springcloud.aws.examples.dynamodb.dto.MovieDTO;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;

@Service
@RequiredArgsConstructor
public class SnsService {

    private final SnsTemplate snsTemplate;
    private final SnsClient snsClient;
    private final AppConfig appConfig;

    public void sendNotificationWithTemplate() {
        MovieDTO movieDTO = MovieDTO.builder()
                .movieName("Send from Java")
                .releaseDate("01/01/2024")
                .director("Java")
                .imdbRating("1")
                .build();

        snsTemplate.sendNotification(appConfig.getAws().getSns().getArn(), movieDTO, movieDTO.getMovieName());
    }

    public void sendNotificationWithClient() {
        MovieDTO movieDTO = MovieDTO.builder()
                .movieName("Send from Java")
                .releaseDate("01/01/2024")
                .director("Java")
                .imdbRating("1")
                .build();

        snsClient.publish(request -> request.topicArn(appConfig.getAws().getSns().getArn())
                .message(String.valueOf(movieDTO)));
    }
}
