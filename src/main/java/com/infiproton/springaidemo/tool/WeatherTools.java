package com.infiproton.springaidemo.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherTools {

    @Tool(description = "Get tomorrow's weather forecast for a given city")
    public String getWeather(String city) {
        switch (city.toLowerCase()) {
            case "paris":
                return "Tomorrow in Paris: 22°C, partly cloudy.";
            case "tokyo":
                return "Tomorrow in Tokyo: 28°C, light rain.";
            case "dubai":
                return "Tomorrow in Dubai: 36°C, sunny.";
            default:
                return "Sorry, I don’t have weather info for " + city;
        }
    }
}
