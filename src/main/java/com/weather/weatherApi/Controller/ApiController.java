package com.weather.weatherApi.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.weather.weatherApi.Service.SearchApiService;
import com.weather.weatherApi.Service.WeatherApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.weather.weatherApi.Model.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api-v1")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    WeatherApiService weatherService;

    @Autowired
    SearchApiService searchService;

    @GetMapping(value="/get-location/{cityName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLocation(@PathVariable String cityName) throws JsonProcessingException {
        List<Location> locations = Arrays.stream(searchService.getLocations(cityName)).toList();
        if (locations.isEmpty()){
            log.error("No location found for parameter: {}", cityName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No location found for parameter: " + cityName);
        } else{
            log.info("Locations retrieved for parameter: {}", cityName);
            return ResponseEntity.ok(locations);
        }
    }

    @PostMapping(value = "/get-weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWeather(@RequestBody Location cityLocation) throws JsonProcessingException {
        Weather weather = weatherService.getWeather(cityLocation);
        if (weather == null){
            log.error("No weather information found for parameter: {}", cityLocation.getName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No weather information found for city: " + cityLocation.getName());
        } else{
            log.info("Weather information retrieved for parameter: {}", cityLocation.getName());
            return ResponseEntity.ok(weather);
        }
    }

}
