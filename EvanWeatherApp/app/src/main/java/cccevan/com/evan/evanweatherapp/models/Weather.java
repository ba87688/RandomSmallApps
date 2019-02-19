package cccevan.com.evan.evanweatherapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {

    private double latitude;
    private double longitude;
    private String timezone;

    //current weather
    private double time;
    private String summary; //example: partly cloudy
    private double temperature;
    private double apparentTemperature;

    public Weather(double latitude, double longitude, String timezone,
                   double time, String summary, double temperature, double apparentTemperature) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
        this.time = time;
        this.summary = summary;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
    }

    public Weather() {
    }

    protected Weather(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        timezone = in.readString();
        time = in.readDouble();
        summary = in.readString();
        temperature = in.readDouble();
        apparentTemperature = in.readDouble();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }


    @Override
    public String toString() {
        return "Weather{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", timezone='" + timezone + '\'' +
                ", time=" + time +
                ", summary='" + summary + '\'' +
                ", temperature=" + temperature +
                ", apparentTemperature=" + apparentTemperature +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(timezone);
        dest.writeDouble(time);
        dest.writeString(summary);
        dest.writeDouble(temperature);
        dest.writeDouble(apparentTemperature);
    }
}
