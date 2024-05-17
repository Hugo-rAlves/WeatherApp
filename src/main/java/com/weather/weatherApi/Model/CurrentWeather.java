package com.weather.weatherApi.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {

        private String last_updated;
        private Double temp_c;
        private Double temp_f;
        private int is_day;
        @JsonProperty("condition")
        private ConditionWeather condition;
        private Double wind_mph;
        private Double wind_kph;
        private int humidity;
        private Double feelslike_c;
        private Double feelslike_f;
        private Double uv;

}
