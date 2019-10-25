package com.example.getweather.retrofit;

import com.example.getweather.models.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {


    String BASE_URL = "http://api.openweathermap.org/";
    String API_KEY = "c9737f0742612b80d89f965cd648c87a";

    //For temperature in Celsius
    String UNITS_METRIC = "metric";
    //For temperature in Fahrenheit
    String UNITS_IMPERIAL = "imperial";


    @GET("data/2.5/weather")
    Call<Response> getWeather(@Query("q") String query,
                              @Query("appid") String appId,
                              @Query("units") String units);


    class Factory {
        private static WeatherAPI service;
        public static WeatherAPI getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(WeatherAPI.class);
                return service;
            } else {
                return service;
            }
        }
    }


}
