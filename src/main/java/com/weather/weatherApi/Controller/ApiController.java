package com.weather.weatherApi.Controller;
import com.weather.weatherApi.Service.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @Autowired
    WeatherApiService weatherService;

    @GetMapping("/")
    public String indexPage(Model model){
        String weatherHello = weatherService.testResponse();
        model.addAttribute("weather", weatherHello);
        return "index";
    }

}
