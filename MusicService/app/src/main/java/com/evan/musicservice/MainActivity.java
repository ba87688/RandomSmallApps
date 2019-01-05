package com.evan.musicservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intent filter to tell me when service is started/done
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicService.DOWNLOAD_BROADCAST_ACTION);
        registerReceiver(new MyBroadcast(), filter);



        findViewById(R.id.startMusic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                startService(intent);
            }
        });

        findViewById(R.id.stopMusic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, MusicService.class));
            }
        });

    }

    //code runs when service sends a broadcast to tell you its done working
    private class MyBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            Toast.makeText(MainActivity.this,"The crappy music player",Toast.LENGTH_LONG).show();
        }
    }
}
