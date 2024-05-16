package com.weather.weatherApi.Controller;
import com.weather.weatherApi.Service.SearchApiService;
import com.weather.weatherApi.Service.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @Autowired
    WeatherApiService weatherService;

    @Autowired
    SearchApiService searchService;

    @GetMapping("/")
    public String indexPage(Model model){
        String weatherConditions = weatherService.testResponse();
        String weatherLocation = searchService.testResponse();
        model.addAttribute("city", weatherLocation);
        model.addAttribute("weather", weatherConditions);
        return "index";
    }

}
