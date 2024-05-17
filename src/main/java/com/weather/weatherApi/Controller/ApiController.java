package com.weather.weatherApi.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.weather.weatherApi.Service.SearchApiService;
import com.weather.weatherApi.Service.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.weather.weatherApi.Model.*;

@Controller
public class ApiController {

    @Autowired
    WeatherApiService weatherService;

    @Autowired
    SearchApiService searchService;

    @GetMapping("/")
    public String indexPage(Model model) throws JsonProcessingException {
        Location[] locations = searchService.getLocations("Buenos Aires");
        String weather = weatherService.testResponse(locations[0]);
        model.addAttribute("weather", weather);
        return "index";
    }

}
