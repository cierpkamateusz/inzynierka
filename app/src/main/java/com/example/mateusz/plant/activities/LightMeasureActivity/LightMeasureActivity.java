package com.example.mateusz.plant.activities.LightMeasureActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.MyActivity;

public class LightMeasureActivity extends MyActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private int bottom;
    private int maxTop;
    private int left;
    private int right;
    private int top;
    private int good;
    private int medium;
    private int low;
    private int leftText;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LightLevel lightLevel = new LightLevel(this);
        setContentView(lightLevel);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        width = size.x;
        height = size.y;
        bottom = size.y*4/5;
        maxTop = size.y*1/5;
        left = size.x*2/5;
        right = size.x*3/5;
        good = size.y*3/10;
        medium = size.y*5/10;
        low = size.y*7/10;
        leftText = size.x*1/8;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_LIGHT){
            float brightnessLevel = event.values[0];
//            sensorValue.setText(String.valueOf(brightnessLevel));
            if(brightnessLevel<=maxTop){
                top = bottom - (int)Math.abs(brightnessLevel)*5;
            }
            else top=maxTop;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_GAME);
    }
    private class LightLevel extends View {

        Paint paint = new Paint();

        public LightLevel(Context context) {

            super(context);

        }
        @Override
        protected void onDraw(Canvas canvas) {

            Drawable d = getResources().getDrawable(R.drawable.level);
            d.setBounds(left, maxTop, right, bottom);

            d.draw(canvas);

            paint.setColor(Color.WHITE);

            canvas.drawRect(left,maxTop,right,top,paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(48f);
            canvas.drawText("Dobre",leftText,good,paint);
            canvas.drawText("Średnie",leftText,medium,paint);
            canvas.drawText("Słabe",leftText, low,paint);
            canvas.drawLine(width*3/10,maxTop,width*7/10,maxTop,paint);
            canvas.drawLine(width*3/10,height*2/5,width*7/10,height*2/5,paint);
            canvas.drawLine(width*3/10,height*3/5,width*7/10,height*3/5,paint);
            canvas.drawLine(width*3/10,bottom,width*7/10,bottom,paint);
            invalidate();
        }
    }
}
