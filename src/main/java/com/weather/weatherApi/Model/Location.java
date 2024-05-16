package com.weather.weatherApi.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Location {

    private String cityName;
    private String cityState;
    private String cityCountry;
    private String cityLatitude;
    private String cityLongitude;

}
