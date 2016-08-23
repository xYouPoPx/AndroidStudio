package com.example.ycourteau.jackoftools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class OrientationActivityFragment extends Fragment {

    private static SensorManager sManager;
    private Sensor sensor;
    TextView tv;
    private Float x, y, z;

    public OrientationActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orientation, container, false);

        tv = (TextView)rootView.findViewById(R.id.tv);
        //tv.setText("openSearch");

        return rootView;
    }

    public SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            sensor = event.sensor;

            if (sensor.getType() == Sensor.TYPE_ORIENTATION) {
                if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
                    return;
                }

                x = event.values[2];
                y = event.values[1];
                z = event.values[0];

                //else it will output the Roll, Pitch and Yawn values
                tv.setText("Orientation X (Roll) :" + x + "\n" +
                        "Orientation Y (Pitch) :" + y + "\n" +
                        "Orientation Z (Yaw) :" + z);

            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


}
