package weatherapp.cccevan.com.evan.fitness12;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GeofenceService extends IntentService {
 public static final String TAG ="GeofenceService";

    private TimeAsyncTask mTask;

    public GeofenceService( ) {
        super(TAG);
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        Log.d(TAG, "yo you: error occured geofence ");

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        if(event.hasError()){
            Log.d(TAG, "onHandleIntent: error occured geofence ");

        }
        else{
            int transition = event.getGeofenceTransition();


            //add any custom logic you  want here.
            if(transition==Geofence.GEOFENCE_TRANSITION_ENTER){


                Log.d(TAG, "onHandleIntent: entered geofence " );
                List<Geofence> geofences = event.getTriggeringGeofences();
                Geofence geofence = geofences.get(0);
                String requestId = geofence.getRequestId();
                Log.d(TAG, "onHandleIntent: entered geofence "+requestId );

                mTask= new TimeAsyncTask();
                mTask.execute();


            }
            else if(transition==Geofence.GEOFENCE_TRANSITION_EXIT){
                Log.d(TAG, "onHandleIntent: existed area");
                if(mTask!=null) {
                    mTask.cancel(true);
                }
            }
        }

    }
    private class TimeAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Timer timer = new Timer();
            final int[] minutesPassed = {0};

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    minutesPassed[0]++;
                    Log.d(TAG, "run: time passed "+ minutesPassed[0]+ " Minutes");
                    if(minutesPassed[0]%60==0){

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "HERCHANNEL1")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Sitting for over an hour!!")
                                .setContentText("Walk a little bit!")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                                .build();
                        notificationManager.notify(1,notification);
                    }

                }
            };
            timer.scheduleAtFixedRate(task,60000,60000);
            return null;
        }
    }


}
