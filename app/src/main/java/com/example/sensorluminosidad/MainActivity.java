package com.example.sensorluminosidad;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;
    private Sensor lightsen;
    private SensorEventListener lightevlist;
    private View root;
    private float maxvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById((R.id.root));
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);


            //codigo que revisa que sensores tiene el telefono
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);

        if (sensors.size() > 0){
            for (Sensor s : sensors){
                Log.i("sensors", String.valueOf(s.getName()));
            }
        } else {
            Log.i("sensors", "no hay sensor de luminosidad");
        }




      lightsen = sm.getDefaultSensor(Sensor.TYPE_LIGHT);



  if (lightsen == null){
            Toast.makeText(this, "El dispositivo no tiene sensor de luz",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        maxvalue = lightsen.getMaximumRange();

        lightevlist = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                float value = sensorEvent.values[0];
                getSupportActionBar().setTitle("Luminosidad" + value + "lx");

                int newValue = (int)(255f * value / maxvalue);
                root.setBackgroundColor(Color.rgb(newValue, newValue, newValue));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

}
        @Override
        protected void onResume() {
            super.onResume();
           sm.registerListener(lightevlist, lightsen, SensorManager.SENSOR_DELAY_FASTEST);
        }
        @Override
        protected void onPause() {
            super.onPause();
            sm.unregisterListener(lightevlist);
        }

}
