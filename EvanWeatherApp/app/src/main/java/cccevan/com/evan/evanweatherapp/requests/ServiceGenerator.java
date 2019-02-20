package cccevan.com.evan.evanweatherapp.requests;

import cccevan.com.evan.evanweatherapp.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static WeatherApi weatherApi = retrofit.create(WeatherApi.class);

    //this return weather object which has current weather and timezone
    public static WeatherApi getWeatherApi(){return weatherApi;}

}
