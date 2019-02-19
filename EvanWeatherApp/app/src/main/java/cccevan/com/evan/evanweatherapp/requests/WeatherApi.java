package cccevan.com.evan.evanweatherapp.requests;

import cccevan.com.evan.evanweatherapp.requests.responses.WeatherSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi {
//https://api.darksky.net/
// forecast/644703b9f55c84ecac524e1a2b3d2d85/37.8267,-122.4233
    @GET("{forecast}/{key}/{latitude}")
    Call<WeatherSearchResponse> searchWeather(
            @Path("key") String key,
            @Path("forecast") String forecast,
        @Path("latitude") String latitude

//        @Path("latitude") Double latitude,
//        @Path("longitude") Double longitude
    );
}
