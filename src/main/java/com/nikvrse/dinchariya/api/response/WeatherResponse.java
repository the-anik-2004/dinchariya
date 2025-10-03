package com.nikvrse.dinchariya.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

@Getter
@Setter
public class WeatherResponse{
    private Current current;
    private Location location;

    @Getter
    @Setter
    public class Current{
        private int temperature;
        @JsonProperty("weather_code")
        private int weatherCode;
        @JsonProperty("weather_icons")
        private ArrayList<String> weatherIcons;
        @JsonProperty("weather_descriptions")
        private ArrayList<String> Descriptions;
        @JsonProperty("wind_speed")
        private int windSpeed;
        @JsonProperty("wind_degree")
        private int windDegree;
        @JsonProperty("wind_dir")
        private String winDir;
        private int pressure;
        private double precip;
        private int humidity;
        private int cloudcover;
        private int feelslike;
        @JsonProperty("uv_index")
        private int uvIndex;
        private int visibility;
        @JsonProperty("is_day")
        private String isDay;
    }

    @Getter
    @Setter
    public class Location{
        private String name;
        private String country;
        private String region;
        private String lat;
        private String lon;
        @JsonProperty("timezone_id")
        private String timezoneId ;
        private String localtime;
        @JsonProperty("localtimeEpoch")
        private int localtime_epoch;
        @JsonProperty("utc_offset")
        private String utcOffset;
    }
}
