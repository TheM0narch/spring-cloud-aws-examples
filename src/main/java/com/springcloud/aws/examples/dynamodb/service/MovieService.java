package com.springcloud.aws.examples.dynamodb.service;

import com.springcloud.aws.examples.dynamodb.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final DynamoDbClient dynamoDbClient;

    //The get method requires both movie_name and release_date because the db is created with a composite key(primary + sort key)
    //If the table was defined with only primary key the get and delete functions would only require it
    public MovieDTO getMovie(String movieName, String releaseDate) {
        GetItemResponse response = dynamoDbClient.getItem(GetItemRequest.builder()
                .tableName("Movies")
                .key(Map.of("movie_name", AttributeValue.builder().s(movieName).build(),
                        "release_date", AttributeValue.builder().s(releaseDate).build()))
                .build());

        return convertItemToMovieDTO(response.item());
    }

    public void postMovie(MovieDTO movieDTO) {
        //The putItem is used for both update and create
        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName("Movies")
                .item(Map.of("movie_name", AttributeValue.builder().s(movieDTO.getMovieName()).build(),
                        "release_date", AttributeValue.builder().s(movieDTO.getReleaseDate()).build(),
                        "director", AttributeValue.builder().s(movieDTO.getDirector()).build(),
                        "imdb_rating", AttributeValue.builder().s(movieDTO.getImdbRating()).build()))
                .build());
    }

    public void deleteMovie(String movieName, String releaseDate) {
        dynamoDbClient.deleteItem(DeleteItemRequest.builder()
                .tableName("Movies")
                .key(Map.of("movie_name", AttributeValue.builder().s(movieName).build(),
                        "release_date", AttributeValue.builder().s(releaseDate).build()))
                .build());
    }

    private MovieDTO convertItemToMovieDTO(Map<String, AttributeValue> item) {
        return MovieDTO.builder()
                .movieName(item.get("movie_name").s())
                .releaseDate(item.get("release_date").s())
                .director(item.get("director").s())
                .imdbRating(item.get("imdb_rating").n())
                .build();
    }

}
