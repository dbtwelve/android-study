package com.example.jupiter.mysensor;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class DataActivity extends AppCompatActivity implements SensorEventListener{
    TextView textView4;
    TextView textView5;
    TextView textView6;

    SensorManager manager;
    List<Sensor> sensors;

    int sensorIndex = 0;
    String sensorName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = manager.getSensorList(Sensor.TYPE_ALL);

        Intent passedIntent = getIntent();
        processCommand(passedIntent);
    }

    public void processCommand(Intent intent) {
        if (intent != null) {
            sensorIndex = intent.getIntExtra("SensorIndex", 0);
            sensorName = intent.getStringExtra("SensorName");
            textView4.setText(sensorName);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        manager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        manager.registerListener(this, sensors.get(sensorIndex),SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String timestamp = "Sensor Timestamp -> " + sensorEvent.timestamp;
        textView5.setText(timestamp);

        String sensorValue = sensorEvent.values[0] + ", " + sensorEvent.values[1] + ", " + sensorEvent.values[2];
        textView6.setText(sensorValue);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
