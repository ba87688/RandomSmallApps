package cccevan.com.evan.evanweatherapp.requests;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import cccevan.com.evan.evanweatherapp.AppExecutors;
import cccevan.com.evan.evanweatherapp.models.CurrentWeather;
import cccevan.com.evan.evanweatherapp.models.Weather;
import cccevan.com.evan.evanweatherapp.requests.responses.WeatherSearchResponse;
import cccevan.com.evan.evanweatherapp.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class WeatherApiClient {

    private RetrieveWeatherRunnable mRetrieveWeatherRunnable;


    //keep livedata reference
    private MutableLiveData <Weather> mWeather;


    //creating a singleton class
    private static WeatherApiClient instance;
    public static WeatherApiClient getInstance(){
        if(instance==null){
            instance = new WeatherApiClient();
        }
        return instance;
    }


    private WeatherApiClient(){

        mWeather = new MutableLiveData<>();
    }


    public MutableLiveData <Weather> getWeather(){

        return mWeather;
    }

    public  void searchWeatherApi(){
        if(mRetrieveWeatherRunnable!=null){
            mRetrieveWeatherRunnable=null;
        }
        mRetrieveWeatherRunnable = new RetrieveWeatherRunnable();

        //executors in the background tasks
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveWeatherRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        },Constants.NETWORK_TIMEOUT,TimeUnit.MILLISECONDS);
    }



    private class RetrieveWeatherRunnable implements  Runnable{

        boolean cancelRequest;

        public RetrieveWeatherRunnable() {

            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getW().execute();
                if(!response.isSuccessful()){
                    Log.d(TAG, "one response: call: " + response.code());
                    return;
                }
                String w = ((WeatherSearchResponse)response.body()).getTimezone();
                Log.d(TAG, "one response: call: " + w);


                CurrentWeather weather = ((WeatherSearchResponse)response.body()).getCurrentWeather();

                Weather weather1 = new Weather(((WeatherSearchResponse)response.body()).getTimezone(),
                        ((WeatherSearchResponse)response.body()).getCurrentWeather());

                mWeather.postValue(weather1);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling request");
            cancelRequest=true;
        }

        //this goes to the interface and.gets the Weather which has timezone and currentweather
        private WeatherApi getWeather(){
            return ServiceGenerator.getWeatherApi();

        }
        private Call<WeatherSearchResponse> getW(){
            return ServiceGenerator.getWeatherApi().getWeather();
        }
//        private Call<WeatherSearchResponse> getWeathers(){
//            return ServiceGenerator.getWeatherApi().;
//        }
    }

}
