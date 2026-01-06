package com.springcloud.aws.examples.dynamodb.model;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Setter
public class Movie {
    String movieName;
    String releaseDate;
    String director;
    Double imdbRating;

    @DynamoDbPartitionKey
    public String getMovieName() {
        return movieName;
    }

    @DynamoDbSortKey
    public String getReleaseDate() {
        return releaseDate;
    }

    @DynamoDbAttribute("director")
    public String getDirector() {
        return director;
    }

    @DynamoDbAttribute("imdb_rating")
    public Double getImdbDirector() {
        return imdbRating;
    }
}
