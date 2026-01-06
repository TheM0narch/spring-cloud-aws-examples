package com.springcloud.aws.examples.dynamodb.controller;

import com.springcloud.aws.examples.dynamodb.dto.MovieDTO;
import com.springcloud.aws.examples.dynamodb.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("dynamodb")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public MovieDTO getMovie(@RequestParam String movieName,
                             @RequestParam String releaseDate) {
        return movieService.getMovie(movieName, releaseDate);
    }

    @PostMapping
    public void postMovie(@RequestBody MovieDTO movieDTO) {
        movieService.postMovie(movieDTO);
    }

    @DeleteMapping
    public void deleteMovie(@RequestParam String movieName,
                            @RequestParam String releaseDate) {
        movieService.deleteMovie(movieName, releaseDate);
    }
}
