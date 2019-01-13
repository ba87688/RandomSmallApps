package com.evan.catfacts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {

    public static boolean hasNetwork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean tf = false;
        try {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            tf = networkInfo!=null && networkInfo.isConnectedOrConnecting();
            return tf;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
