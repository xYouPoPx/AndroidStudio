package com.palachance.myapplication;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * Created by Pier-Alain on 2015-08-31.
 */




public class DetecteurMetalFragment extends Fragment {
    public Sensor metal = null;
    public TextView tv;
    ToggleButton btnOnOff;
    Thread monThread;
    Runnable runnable;
    Button btntreshold;
    EditText edtTreshold;
    int EPSILON;
    MediaPlayer mediaPlayer = null;
    SensorManager sManager;

    int resultant;
    int resultantCare;

    public DetecteurMetalFragment(){

    }
    SensorEventListener sel = new SensorEventListener() {

        @Override

        public void onSensorChanged(SensorEvent event) {
            Sensor metal = null;

            metal = event.sensor;

            if(metal.getType() == Sensor.TYPE_MAGNETIC_FIELD){

                int x = (int)event.values[0];
                int y = (int)event.values[1];
                int z = (int)event.values[2];

                //resultantCare = ((int)Math.pow(Double.valueOf(event.values[0]) ,2.0) + (int)Math.pow(Double.valueOf(event.values[1]) ,2.0) + (int)Math.pow(Double.valueOf(event.values[2]) ,2.0));
                resultantCare = ( x*x)+ (y*y) + (z*z);
                resultant = (int)Math.sqrt(resultantCare);

                tv.setText(String.valueOf(resultant));

            }
            if (resultant > EPSILON){
                tv.setTextColor(Color.parseColor("#DF0101"));

                if (mediaPlayer != null ) {
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }


                mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.bleepmetal);

                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }

            }
            else if (EPSILON-resultant < 10) {
                tv.setTextColor(Color.parseColor("#FFFF00"));
            }
            else{
                tv.setTextColor(Color.parseColor("#51BC3B"));
            }



        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detecteurmetal, container, false);

        tv = (TextView) rootView.findViewById(R.id.tv);

        btnOnOff = (ToggleButton) rootView.findViewById(R.id.toggleButton);
        btnOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
                    sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
                }
                else{
                    sManager.unregisterListener(sel);
                    mediaPlayer.stop();

                }

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




    @Override
    public void onPause() {
        if(sManager != null) {
            sManager.unregisterListener(sel);
            mediaPlayer.stop();
        }

        super.onPause();
    }
    @Override
    public void onResume(){

        if(btnOnOff.isChecked()){
            sManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
            sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        }

        super.onResume();
    }

}
