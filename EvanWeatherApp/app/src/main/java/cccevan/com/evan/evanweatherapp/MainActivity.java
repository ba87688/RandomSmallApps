package cccevan.com.evan.evanweatherapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import cccevan.com.evan.evanweatherapp.models.Weather;
import cccevan.com.evan.evanweatherapp.repositories.WeatherRepository;
import cccevan.com.evan.evanweatherapp.viewModels.WeatherViewModel;

public class MainActivity extends BaseActivity {
    private WeatherViewModel mWeatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        subscribeObservers();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWeatherApi();

            }
        });


    }

    //observe and change the data of the recipe list. this activity is in charge of that
    private void subscribeObservers() {
        mWeatherViewModel.getWeather().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather weather) {

            }
        });
    }

    private void searchWeatherApi() {
        mWeatherViewModel.searchWeatherApi();
    }
}
