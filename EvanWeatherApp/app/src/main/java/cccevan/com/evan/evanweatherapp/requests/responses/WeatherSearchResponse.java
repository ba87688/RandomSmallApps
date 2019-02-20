package cccevan.com.evan.evanweatherapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import cccevan.com.evan.evanweatherapp.models.CurrentWeather;
import cccevan.com.evan.evanweatherapp.models.Weather;

public class WeatherSearchResponse {

    @SerializedName("timezone")
    @Expose()
    private String timezone;

    @SerializedName("currently")
    @Expose()
    private CurrentWeather currentWeather;


    public String getTimezone() {
        return timezone;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    @Override
    public String toString() {
        return "WeatherSearchResponse{" +
                ", timezone='" + timezone + '\'' +
                ", currentWeather=" + currentWeather +
                '}';
    }
}
