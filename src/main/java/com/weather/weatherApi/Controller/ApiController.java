package com.weather.weatherApi.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.weather.weatherApi.Service.SearchApiService;
import com.weather.weatherApi.Service.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.weather.weatherApi.Model.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api-v1")
public class ApiController {

    @Autowired
    WeatherApiService weatherService;

    @Autowired
    SearchApiService searchService;

    @GetMapping(value="/get-location/{cityName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLocation(@PathVariable String cityName) throws JsonProcessingException {
        List<Location> locations = Arrays.stream(searchService.getLocations(cityName)).toList();
        return ResponseEntity.ok(locations);
    }

    @PostMapping(value = "/get-weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWeather(@RequestBody Location cityLocation) throws JsonProcessingException {
        Weather weather = weatherService.getWeather(cityLocation);
        return ResponseEntity.ok(weather);
    }

}
