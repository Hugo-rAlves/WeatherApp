package com.weather.weatherApi.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherApi.Model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SearchApiService {

    private static final Logger log = LoggerFactory.getLogger(SearchApiService.class);

    private final WebClient.Builder client;
    private final String apiKey;
    private final String apiURL;

    public SearchApiService(WebClient.Builder client, @Value("${weatherApiKey}") String apiKey, @Value("${weatherApiURL}") String apiURL){
        this.client = client;
        this.apiKey = apiKey;
        this.apiURL = apiURL;
    }

    private WebClient searchApiWebService(){
        return client
                .baseUrl(apiURL)
                .defaultHeader("key", apiKey)
                .build();
    }

    private String getLocation(String parameter){
        log.info("Fetching locations for parameter: {}", parameter);
        String response = searchApiWebService().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.json")
                        .queryParam("q", parameter)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.debug("Response from search API: {}", response);
        return response;
    }

    public Location[] getLocations(String city) throws JsonProcessingException {
        log.info("Getting locations for parameter: {}", city);
        ObjectMapper objectMapper = new ObjectMapper();
        String location = getLocation(city);
        Location[] locations = objectMapper.readValue(location, Location[].class);
        log.info("Locations retrieved: {}", (Object) locations);
        return locations;
    }

}
