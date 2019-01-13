package com.evan.ondraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void moveClick(View view) {
        final FaceView faceView =(FaceView) findViewById(R.id.myFaceView);
        //have the program redraw itself, this will recall onDraw.

        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {

                    faceView.doAnimate();



            }
        });
        thread.start();
    }
}
