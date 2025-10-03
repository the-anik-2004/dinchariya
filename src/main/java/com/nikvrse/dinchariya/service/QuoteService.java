package com.nikvrse.dinchariya.service;

import com.nikvrse.dinchariya.api.response.QuoteResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {
    public static final String key="8J5SZJdCdGvZKjJ3OFqqcQ==i9gkeykoTc4OeVqg";
    public static final String API="https://api.api-ninjas.com/v1/quotes?x-api-key=APIKEY";


    private final RestTemplate restTemplate;

    public QuoteService(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    public QuoteResponse getQuote(){
        String finalAPI=API.replace("APIKEY",key);
        QuoteResponse[] quotes = restTemplate.getForObject(finalAPI, QuoteResponse[].class);

        if (quotes != null && quotes.length > 0) {
            return quotes[0];
        } else {
            return null;
        }


    }

}
