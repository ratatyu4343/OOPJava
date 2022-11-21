package com.example.oop_lab3;

import DTW.*;
import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnTouchListener {
    private TextView labelX;
    private TextView labelY;
    private TextView labelZ;
    private Button button;
    private Button saveBtn;
    private EditText editText;
    private TextView resultsInfo;
    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor  sensor;
    private boolean isRecording = false;
    DTW dtwAlg;
    TimeSerie timeSerie;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelX = findViewById(R.id.textView);
        labelY = findViewById(R.id.textView2);
        labelZ = findViewById(R.id.textView3);
        resultsInfo = findViewById(R.id.resultInfo);

        button = findViewById(R.id.button);
        button.setOnTouchListener(this);

        saveBtn = findViewById(R.id.saveNameBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editText.getText().toString();
                if(!newName.equals("")) {
                    timeSerie.setName(newName);
                    dtwAlg.addTimeSerie(timeSerie);
                }
                editText.setText("");
            }
        });
        editText = findViewById(R.id.saveName);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        timeSerie = new TimeSerie("myTimeSerie");
        dtwAlg = new DTW();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                saveBtn.setEnabled(false);
                editText.setEnabled(false);
                isRecording = true;
                timeSerie = new TimeSerie("myTimeSerie");
                resultsInfo.setText("Визначаємо...");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(5);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                isRecording = false;
                if(timeSerie.getSize() > 3) {
                    saveBtn.setEnabled(true);
                    editText.setEnabled(true);
                    if(dtwAlg.getTimeSeries().size() >= 1){
                        Pair<TimeSerie, Float> res = dtwAlg.findBestTransformation(timeSerie);
                        if(res.second <= 30) {
                            resultsInfo.setText("Наймовірніше це " + res.first.getName() + "\n DTW оцінка: " + res.second);
                        } else if(res.second <= 100){
                            resultsInfo.setText("Малоймовірно, але це " + res.first.getName() + "\n DTW оцінка: " + res.second);
                        } else {
                            resultsInfo.setText("Неможливо визначити точно, найбільш підходить "+ res.first.getName()  +
                                    ", DTW оцінка: " + res.second + ", спробуйте ще раз...");
                        }
                    } else {
                        resultsInfo.setText("Ще немає з чим порівнювати...");
                    }
                } else {
                    resultsInfo.setText("Занадто мало даних...");
                }
                labelX.setText("X: ");
                labelY.setText("Y: ");
                labelZ.setText("Z: ");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(5);
                }
                break;
            }
            default: {
                break;
            }
        }
        return true;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensorNow = sensorEvent.sensor;
        if(sensorNow.getType() == Sensor.TYPE_ACCELEROMETER) {
            Point point = new Point();
            point.x = sensorEvent.values[0];
            point.y = sensorEvent.values[1];
            point.z = sensorEvent.values[2];
            labelX.setText(String.format("X: %f", point.x));
            labelY.setText(String.format("Y: %f", point.y));
            labelZ.setText(String.format("Z: %f", point.z));
            if(isRecording) {
                if (timeSerie.getSize() > 1) {
                    if (Point.distant(timeSerie.getPoint(timeSerie.getSize() - 1), point) > 0.000001) {
                        timeSerie.addPoint(point);
                    }
                } else
                    timeSerie.addPoint(point);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}