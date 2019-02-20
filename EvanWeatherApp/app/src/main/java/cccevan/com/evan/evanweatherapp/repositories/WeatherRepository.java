package cccevan.com.evan.evanweatherapp.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import cccevan.com.evan.evanweatherapp.models.Weather;
import cccevan.com.evan.evanweatherapp.requests.WeatherApiClient;

public class WeatherRepository {
    private WeatherApiClient mWeatherApiClient;
    private static WeatherRepository instance;

    public static WeatherRepository getInstance(){
        if(instance==null){
            instance = new WeatherRepository();
        }
        return instance;
    }
    public WeatherRepository (){
        mWeatherApiClient = WeatherApiClient.getInstance();

    }

    public LiveData<Weather> getmWeather(){
        return mWeatherApiClient.getWeather();
    }

    public void searchWeatherApi(){
        mWeatherApiClient.searchWeatherApi();

    }
}
