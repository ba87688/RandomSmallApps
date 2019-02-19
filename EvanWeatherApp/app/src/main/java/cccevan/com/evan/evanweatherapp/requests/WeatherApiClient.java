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
import cccevan.com.evan.evanweatherapp.models.Weather;
import cccevan.com.evan.evanweatherapp.requests.responses.WeatherSearchResponse;
import cccevan.com.evan.evanweatherapp.util.Constants;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class WeatherApiClient {

    private static WeatherApiClient instance;
    private RetrieveWeatherRunnable mRetrieveWeatherRunnable;
    //keep livedata reference
    private MutableLiveData<List<Weather>> mWeather;
    public static WeatherApiClient getInstance(){
        if(instance==null){
            instance = new WeatherApiClient();
        }
        return instance;
    }

    private WeatherApiClient(){
        mWeather = new MutableLiveData<>();
    }
    public MutableLiveData<List<Weather>> getWeather(){

        return mWeather;
    }

    public  void searchWeatherApi(String query, String loglat){
        if(mRetrieveWeatherRunnable!=null){
            mRetrieveWeatherRunnable=null;
        }
        mRetrieveWeatherRunnable = new RetrieveWeatherRunnable(query, loglat);

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

        private String query;
        private String loglat;
        boolean cancelRequest;

        public RetrieveWeatherRunnable(String query, String loglat) {
            this.query = query;
            this.loglat = loglat;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getWeather(query,loglat).execute();
                if(cancelRequest){
                    return;
                }
                //            check if request was successful

                if(response.isSuccessful()){
//                    if successful, get list of current weather
                    List<Weather> list =
                            new ArrayList<>((((WeatherSearchResponse)response.body()).getCurrentWeather()));

                    mWeather.postValue(list);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling request");
            cancelRequest=true;
        }

        //this goes to the interface and does the search for us
        private Call<WeatherSearchResponse> getWeather(String query, String loglat){
            return ServiceGenerator.getWeatherApi().searchWeather(
                    Constants.API_KEY,
                    query,
                    loglat
            );

        }
    }

}
