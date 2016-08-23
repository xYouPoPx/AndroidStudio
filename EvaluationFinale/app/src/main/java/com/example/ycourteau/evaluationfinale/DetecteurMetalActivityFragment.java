package com.example.ycourteau.evaluationfinale;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetecteurMetalActivityFragment extends Fragment {

    int EPSILON;

    private static SensorManager sManager;
    private Sensor sensor;
    TextView tv;
    private Float x, y, z, magneticStrength, magneticDelta;
    MediaPlayer mediaPlayer = null;

    ToggleButton btnOnOff;
    Button btntreshold;
    EditText edtTreshold;

    public DetecteurMetalActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EPSILON = 67;

    }

    public SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {


            sensor = event.sensor;

            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
                    return;
                }

                x = event.values[2];
                y = event.values[1];
                z = event.values[0];

                magneticStrength = (float) Math.sqrt((x * x) + (y * y) + (z * z));

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }

                if (magneticStrength > EPSILON) {

                    mediaPlayer.setLooping(true);

                    mediaPlayer.start();

                } else {
                    mediaPlayer.setLooping(false);
                }

                tv.setText("Magnetic Y (Roll) :" + y + "\n" + " magneticStrength = " + magneticStrength);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detecteur_electricite, container, false);

        tv = (TextView) rootView.findViewById(R.id.tv);

        btnOnOff = (ToggleButton) rootView.findViewById(R.id.toggleButton);
        btnOnOff.setChecked(false);
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOnOffClicked(v);
            }
        });

        btntreshold = (Button) rootView.findViewById(R.id.btnTreshold);
        btntreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EPSILON = Integer.parseInt(edtTreshold.getText().toString());
            }
        });

        edtTreshold = (EditText) rootView.findViewById(R.id.edtTreshold);
        edtTreshold.setText(String.valueOf(EPSILON));

        edtTreshold.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                EPSILON = Integer.parseInt(edtTreshold.getText().toString());
                return true;
            }
        });

        return rootView;
    }

    /**
     * Fonction du ToggleButton pour demarrer/arreter le detecteur de sons
     *
     * @param view
     */
    public void onOnOffClicked(View view) {

        if (btnOnOff.isChecked()) {
            sManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
            sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }

            mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.beep);
            mediaPlayer.setLooping(true);
        } else {

            sManager.unregisterListener(sel, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
            mediaPlayer.stop();
        }


    }

    /**
     * Arreter le thread si un autre tab est clique ou si l'app est en pause
     */
    @Override
    public void onPause() {

        if(sManager != null) {
            sManager.unregisterListener(sel, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        }
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (btnOnOff.isChecked()) {
            sManager.unregisterListener(sel, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));

            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }

            mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.beep);
            mediaPlayer.setLooping(true);
        }
        super.onResume();
    }

}
