package com.weather.weatherApi.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherApi.Model.Location;
import com.weather.weatherApi.Model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherApiService {

    private static final Logger log = LoggerFactory.getLogger(WeatherApiService.class);
    private final WebClient.Builder client;
    private final String apiKey;
    private final String apiURL;

    public WeatherApiService(WebClient.Builder client, @Value("${weatherApiKey}") String apiKey, @Value("${weatherApiURL}") String apiURL) {
        this.client = client;
        this.apiKey = apiKey;
        this.apiURL = apiURL;
    }

    public WebClient weatherApiWebService() {
        return client
                .baseUrl(apiURL)
                .defaultHeader("key", apiKey)
                .build();
    }

    public String getCurrentWeather(String parameter) {
        log.info("Fetching current weather for parameter {}", parameter);
        String response = weatherApiWebService().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("q", parameter)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.debug("Response from Weather API: {}", response);
        return response;
    }

    public Weather getWeather(Location location) throws JsonProcessingException {
        log.info("Getting weather for location: {}", location);
        ObjectMapper objectMapper = new ObjectMapper();
        String coordinates = location.getLat().toString() + "," + location.getLon().toString();
        Weather weather = objectMapper.readValue(getCurrentWeather(coordinates), Weather.class);
        weather.setCityLocation(location);
        log.debug("Weather data retrieved: {}", weather);
        return weather;
    }
}
