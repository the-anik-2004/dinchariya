package com.nikvrse.dinchariya.service;

import com.nikvrse.dinchariya.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey="9e9d12309c79c7c1a2f3bfc5136050f8";
    private static final String API="https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RedisService redisService;

    private final RestTemplate restTemplate;
    public WeatherService(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    public WeatherResponse getWeather(String city){
       WeatherResponse weatherResponse= redisService.getRedisData(city,WeatherResponse.class);
       if(weatherResponse!=null){
           return weatherResponse;
       }else{
           String finalApiUrl= API.replace("API_KEY",apiKey).replace("CITY",city);
           WeatherResponse body = restTemplate.exchange(finalApiUrl, HttpMethod.GET,null, WeatherResponse.class).getBody();
           if(body!=null){
               redisService.setRedisData(city,body,300l);
           }
           return body;
       }
    }
}
