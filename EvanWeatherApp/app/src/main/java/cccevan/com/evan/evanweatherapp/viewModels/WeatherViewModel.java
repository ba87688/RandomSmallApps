package cccevan.com.evan.evanweatherapp.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import cccevan.com.evan.evanweatherapp.models.Weather;
import cccevan.com.evan.evanweatherapp.repositories.WeatherRepository;

//class gets data of weather and updates it
public class WeatherViewModel extends ViewModel {

    private WeatherRepository mWeatherRepository;

    public WeatherViewModel(){
        mWeatherRepository = WeatherRepository.getInstance();
    }

    public LiveData <Weather> getWeather(){
//        get livedata from the repository.
        return mWeatherRepository.getmWeather();
    }
    public void searchWeatherApi(){
        mWeatherRepository.searchWeatherApi();
    }

}
