package com.infiproton.springaidemo.tool;

import com.infiproton.springaidemo.model.ForecastResponse;
import com.infiproton.springaidemo.model.WeatherResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class WeatherTools {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = "b686f8b1e87349559e9140605250308";

    @Tool(description = "Get weather forecast for a given city and date (yyyy-MM-dd). If date is not provided, defaults to today.")
    public WeatherResult getWeather(String city, String date) {
        try {
            // Build API URL
            String url = UriComponentsBuilder
                    .fromHttpUrl("http://api.weatherapi.com/v1/forecast.json")
                    .queryParam("key", API_KEY)
                    .queryParam("q", city)
                    .queryParam("dt", date)
                    .toUriString();

            ForecastResponse apiResponse = restTemplate.getForObject(url, ForecastResponse.class);
            if (apiResponse == null) {
                return new WeatherResult(city, date, "N/A", "No data");
            }

            // Extract forecast
            ForecastResponse.ForecastDay forecastDay = apiResponse.getForecast().getForecastday().get(0);

            String condition = forecastDay.getDay().getCondition().getText();
            double tempC = forecastDay.getDay().getAvgtemp_c();
            return new WeatherResult(city, date, tempC + " °C", condition);
        } catch (Exception e) {
            log.error("Error fetching weather for {} on {}: {}", city, date, e.getMessage(), e);
            return new WeatherResult(city, date, "N/A", "No data");
        }
    }
}
