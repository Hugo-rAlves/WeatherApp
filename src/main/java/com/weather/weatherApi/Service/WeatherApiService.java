package com.weather.weatherApi.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherApiService {

    private final WebClient.Builder client;
    private final String apiKey;

    private final String apiURL;


    public WeatherApiService(WebClient.Builder client, @Value("${weatherApiKey}") String apiKey, @Value("${weatherApiURL}") String apiURL) {
        this.client = client;
        this.apiKey = apiKey;
        this.apiURL = apiURL;
    }

    public WebClient WeatherApiWebService() {
        return client
                .baseUrl(apiURL)
                .defaultHeader("key", apiKey)
                .build();
    }

    public String getCurrentWeather(String parameter) {
        return WeatherApiWebService().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("q", parameter)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String testResponse() {
        String coordinates = "-8.1213,-35.3120";
        return getCurrentWeather(coordinates);
    }
}
