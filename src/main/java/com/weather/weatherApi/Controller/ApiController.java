package com.weather.weatherApi.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @GetMapping("/")
    public String indexPage(Model model){
        String weatherHello = "Hello Weather!";
        model.addAttribute("weather", weatherHello);
        return "index";
    }

}
