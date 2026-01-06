package com.springcloud.aws.examples.dynamodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDTO {

    @JsonProperty("movie_name")
    private String movieName;

    @JsonProperty("release_date")
    private String releaseDate;
    private String director;

    @JsonProperty("imdb_rating")
    private String imdbRating;
}
