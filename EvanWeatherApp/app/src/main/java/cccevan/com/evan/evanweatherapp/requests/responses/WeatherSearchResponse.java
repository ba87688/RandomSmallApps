package cccevan.com.evan.evanweatherapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import cccevan.com.evan.evanweatherapp.models.Weather;

public class WeatherSearchResponse {

    @SerializedName("latitude")
    @Expose()
    private double latitude;

    @SerializedName("longitude")
    @Expose()
    private double longitude;

    @SerializedName("timezone")
    @Expose()
    private String timezone;

    @SerializedName("currently")
    @Expose()
    private List<Weather> currentWeather;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public List<Weather> getCurrentWeather() {
        return currentWeather;
    }

    @Override
    public String toString() {
        return "WeatherSearchResponse{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", timezone='" + timezone + '\'' +
                ", currentWeather=" + currentWeather +
                '}';
    }
}
