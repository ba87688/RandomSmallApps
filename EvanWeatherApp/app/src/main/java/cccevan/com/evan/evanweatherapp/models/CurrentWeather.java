package cccevan.com.evan.evanweatherapp.models;

public class CurrentWeather {
    private  int time;
    private double temperature;
    private String summary;
    private double apparentTemperature;
    private String icon;

    private double windSpeed;

    public double getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }


    public String getSummary() {
        return summary;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }
    //return either
    // rain,..
    // clear-day..
    //partly-cloudy-day..
    //partly-cloudy-night..
    //wind..
    //clear-night



    public String getIcon() {
        return icon;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
