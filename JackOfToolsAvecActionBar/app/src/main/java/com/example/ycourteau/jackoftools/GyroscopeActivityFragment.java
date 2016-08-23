package com.example.ycourteau.jackoftools;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class GyroscopeActivityFragment extends Fragment {


    private static SensorManager sManager;
    private Sensor sensor;
    TextView tv;
    private Float x, y, z;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gyroscope, container, false);

        tv = (TextView)rootView.findViewById(R.id.tv);
        //tv.setText("openSearch");

        return rootView;
    }

    public SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {


            sensor = event.sensor;

            if (sensor.getType() == Sensor.TYPE_GYROSCOPE ) {
                if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
                    return;
                }

                x = event.values[2];
                y = event.values[1];
                z = event.values[0];

                //else it will output the Roll, Pitch and Yawn values
                tv.setText("Gyroscope X (Roll) :" + x + "\n" +
                        "Gyroscope Y (Pitch) :" + y + "\n" +
                        "Gyroscope Z (Yaw) :" + z);

            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


}
