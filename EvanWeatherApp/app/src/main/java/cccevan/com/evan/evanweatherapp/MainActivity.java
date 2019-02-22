package cccevan.com.evan.evanweatherapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cccevan.com.evan.evanweatherapp.models.CurrentWeather;
import cccevan.com.evan.evanweatherapp.models.Weather;
import cccevan.com.evan.evanweatherapp.repositories.WeatherRepository;
import cccevan.com.evan.evanweatherapp.viewModels.WeatherViewModel;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity" ;

    private ArrayList<String> icons;


    private TextView temperature,summary,dateAndTime, apparentTemp, windSpeed;
    private TextView timeZone;
    private WeatherViewModel mWeatherViewModel;
    private LinearLayout linearLayout;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView =(ImageView) findViewById(R.id.image_weather);

        timeZone= (TextView)findViewById(R.id.location);


        temperature= (TextView)findViewById(R.id.temperature_text);
        summary= (TextView)findViewById(R.id.summary_text);
        dateAndTime= (TextView)findViewById(R.id.date_and_time);
        apparentTemp= (TextView)findViewById(R.id.feedslike_text);
        windSpeed= (TextView)findViewById(R.id.windspeed_text);

        //animate our background
        animateBackground();
        fillArrayList();

        mWeatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        subscribeObservers();
        searchWeatherApi();
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWeatherApi();

            }
        });


    }

    private void fillArrayList() {
        //rain,..// clear-day..//partly-cloudy-day..//partly-cloudy-night..
        //wind..//clear-night
        icons = new ArrayList();
        icons.add("rain");
        icons.add("clear-day");
        icons.add("partly-cloudy-day");
        icons.add("partly-cloudy-night");
        icons.add("wind");
        icons.add("clear-night");
        icons.add("everythingElse");

    }

    private void animateBackground() {
        linearLayout = (LinearLayout) findViewById(R.id.main_activity_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

    //observe and change the data of the recipe list. this activity is in charge of that
    private void subscribeObservers() {
        mWeatherViewModel.getWeather().observe(this, new Observer<Weather>() {

            @Override
            public void onChanged(@Nullable Weather weather) {
                Log.d(TAG, "onChanged: "+weather.getTimezone());
                Log.d(TAG, "onChanged: "+weather.getCurrentWeather().getTemperature());
                timeZone.setText(""+weather.getTimezone());

                //get the current weather from the api and set the user interface
                CurrentWeather cw =weather.getCurrentWeather();
                temperature.setText(""+Math.round(cw.getTemperature()) + " \u200E°F\u200E");
                summary.setText(""+cw.getSummary());
//                cw.getTime()
                java.util.Date time=new java.util.Date((long)cw.getTime()*1000);
                Calendar cal = Calendar.getInstance();
                cal.setTime(time);

                dateAndTime.setText(""+ time +" ");

                setIcons(cw.getIcon());
                windSpeed.setText(Math.round(cw.getWindSpeed())+" mph ");
                apparentTemp.setText(Math.round(cw.getApparentTemperature())+" \u200E°F\u200E");
            }
        });
    }

    private void setIcons(String iconName){
        if(iconName.equals(icons.get(0))){
            imageView.setImageResource(R.drawable.rain);
            }
        else if(iconName.equals(icons.get(1))){
            imageView.setImageResource(R.drawable.clearday);

        }
        else if(iconName.equals(icons.get(2))){
            imageView.setImageResource(R.drawable.partlycloudyday);

        }
        else if(iconName.equals(icons.get(3))){
            imageView.setImageResource(R.drawable.partlycloudynight);

        }
        else if(iconName.equals(icons.get(4))){
            imageView.setImageResource(R.drawable.windy);

        }
        else if(iconName.equals(icons.get(5))){
            imageView.setImageResource(R.drawable.partlycloudynight);

        }
        else{
            imageView.setImageResource(R.drawable.sunny);

        }
    }
    private void searchWeatherApi() {
        mWeatherViewModel.searchWeatherApi();
    }
}
