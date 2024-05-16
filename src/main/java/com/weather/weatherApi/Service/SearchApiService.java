package com.weather.weatherApi.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SearchApiService {

    private final WebClient.Builder client;
    private final String apiKey;
    private final String apiURL;

    public SearchApiService(WebClient.Builder client, @Value("${weatherApiKey}") String apiKey, @Value("${weatherApiURL}") String apiURL){
        this.client = client;
        this.apiKey = apiKey;
        this.apiURL = apiURL;
    }

    public WebClient searchApiWebService(){
        return client
                .baseUrl(apiURL)
                .defaultHeader("key", apiKey)
                .build();
    }

    public String getLocation(String parameter){
        return searchApiWebService().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.json")
                        .queryParam("q", parameter)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String testResponse(){
        String cityName = "Vitoria de Santo Antao";
        return getLocation(cityName);
    }

}
