package com.weather.weatherApi.Model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Weather {

    private Location cityLocation;
    private LocalDateTime localDateTime;
    private LocalDateTime lastUpdatedTime;
    private Double temperatureCelsius;
    private Double temperatureFahrenheit;
    private boolean isDay;
    private String climateCondition;
    private Double wind_mph;
    private Double wind_kph;
    private int humidity;
    private Double feelsLikeCelsius;
    private Double feelsLikeFahrenheit;
    private Double uv;

}
