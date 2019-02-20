package cccevan.com.evan.evanweatherapp.requests;

import com.google.gson.annotations.Expose;

import cccevan.com.evan.evanweatherapp.models.Weather;
import cccevan.com.evan.evanweatherapp.requests.responses.WeatherSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi {
    public final static String forecast = "forecast";
    public final static String key = "644703b9f55c84ecac524e1a2b3d2d85";
    public final static String lat = "forecast";

    @GET("forecast/644703b9f55c84ecac524e1a2b3d2d85/37.8267,-122.4233")
    Call<WeatherSearchResponse> getWeather(
    );
}
