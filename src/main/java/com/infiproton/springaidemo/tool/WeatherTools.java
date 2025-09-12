package com.infiproton.springaidemo.tool;

import com.infiproton.springaidemo.model.ForecastResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class WeatherTools {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${app.weather.api-key}")
    private String apiKey;

    @Tool(description = "Get weather forecast for a given city and date (yyyy-MM-dd). If date is not provided, defaults to today.")
    public String getWeather(String city, String date) {
        try {
            // Build API URL
            String url = UriComponentsBuilder
                    .fromHttpUrl("http://api.weatherapi.com/v1/forecast.json")
                    .queryParam("key", apiKey)
                    .queryParam("q", city)
                    .queryParam("dt", date)
                    .toUriString();

            ForecastResponse apiResponse = restTemplate.getForObject(url, ForecastResponse.class);
            if (apiResponse == null) {
                return "No response from Weather API";
            }

            // Extract forecast
            ForecastResponse.ForecastDay forecastDay = apiResponse.getForecast().getForecastday().get(0);

            String condition = forecastDay.getDay().getCondition().getText();
            double tempC = forecastDay.getDay().getAvgtemp_c();

            return String.format("Weather in %s on %s: %.1f°C, %s", city, date, tempC, condition);

        } catch (Exception e) {
            log.error("Error fetching weather for {} on {}: {}", city, date, e.getMessage(), e);
            return "Could not fetch weather for " + city + " on " + date + ": " + e.getMessage();
        }
    }
}
