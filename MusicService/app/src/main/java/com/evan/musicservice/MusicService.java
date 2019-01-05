package com.evan.musicservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MusicService extends Service {
    private MediaPlayer player;
    public static String DOWNLOAD_BROADCAST_ACTION = "this downloading stuff is done";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                player = MediaPlayer.create(MusicService.this,R.raw.bonjour );
                player.setLooping(true);
                player.start();

                //broadcast taht we are finished
                Intent done = new Intent();
                done.putExtra("url", "you are done");
                done.setAction(DOWNLOAD_BROADCAST_ACTION);
                sendBroadcast(done);


                //set a notification taht music is playing
                Notification.Builder builder = new Notification.Builder(MusicService.this).
                        setContentTitle("Music has started").
                        setContentText("Bonjour").
                        setSmallIcon(R.drawable.ic_launcher_background);


                //make the notification clickable by builidng an intent
                Intent intent1 = new Intent(MusicService.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.
                        getActivity(MusicService.this, 0,intent1,0);
                builder.setContentIntent(pendingIntent);


                Notification notification = builder.build();

                NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(1234, notification);
            }

        });
        thread.start();



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
