package com.weather.weatherApi.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    private String name;
    private String region;
    private String country;
    private Double lat;
    private Double lon;

}
