package cccevan.com.evan.evanweatherapp.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Weather implements Parcelable {

    public Weather(String timezone, CurrentWeather currentWeather) {
        this.timezone = timezone;
        this.currentWeather = currentWeather;
    }

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("currently")
    private CurrentWeather currentWeather;


    protected Weather(Parcel in) {
        timezone = in.readString();
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

    public String getTimezone() {
        return timezone;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timezone);
    }
}