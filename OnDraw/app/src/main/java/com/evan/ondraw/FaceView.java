package com.evan.ondraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class FaceView extends View {
    private int faceX = 100;

    //ball starting positions
    private float ballX = 10;
    private float ballY = 10;

    //adding movement
    private float velocityX = 5;
    private float velocityY = 5;

    private static final float BALL_SIZE = 60;



    public FaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // drawFace( canvas);


        drawBouncingBall(canvas);
    }


    public void drawBouncingBall(Canvas canvas) {

        float w = canvas.getWidth();
        float h = canvas.getHeight();

        //making red color object
        Paint redPaint = new Paint();
        redPaint.setARGB(255,255,0,0);
        redPaint.setStyle(Paint.Style.FILL);

        //making a ball
        canvas.drawOval(new RectF(ballX, ballY, ballX+BALL_SIZE, ballY+BALL_SIZE),redPaint);

        ballX+=velocityX;
        ballY+=velocityY;

        if(ballX + BALL_SIZE >= w || ballX <=0){
            velocityX = -velocityX;

        }
        if (ballY +BALL_SIZE >= h || ballY<=0){
            velocityY= -velocityY;
        }
    }



    public void doAnimate() {
        while (true){
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                break;
            }
            postInvalidate();
        }
    }




//        public void drawFace(Canvas canvas){
//        Paint yellowPaint = new Paint();
//        yellowPaint.setARGB(255,255,255,0);
//        yellowPaint.setStyle(Paint.Style.FILL);
//        canvas.drawOval(new RectF(faceX,100,faceX+200,300), yellowPaint);
//        faceX+=20;
//
//        //eyes are blue
//        Paint bluePaint = new Paint();
//        bluePaint.setARGB(255,0,0,255);
//        bluePaint.setStyle(Paint.Style.FILL);
//        canvas.drawOval(new RectF(140,140,170,170),bluePaint);
//        canvas.drawOval(new RectF(230,140,260,170),bluePaint);
//
//
//        Paint redPaint = new Paint();
//        redPaint.setARGB(255,255,0,0);
//        redPaint.setStyle(Paint.Style.FILL);
//    //    canvas.drawRect(10,50,370,245,redPaint);
//
//        Paint blackPaint = new Paint();
//        blackPaint.setARGB(255,0,0,0);
//        blackPaint.setStyle(Paint.Style.FILL);
//        blackPaint.setStrokeWidth(5);
//
//        //nose draw
//        canvas.drawOval(new RectF(190,180,210,200),blackPaint);
//
//        //draw mouth
//        canvas.drawRect(170,230,230,250, redPaint);
//      //  canvas.drawRect(10,50,370,245,blackPaint);
//
//
//
//    }



}
