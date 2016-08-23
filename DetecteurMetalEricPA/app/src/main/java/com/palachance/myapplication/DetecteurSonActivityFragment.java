package com.palachance.myapplication;

import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * @author ycourteau 2015-08-29
 *         A placeholder fragment containing a simple view.
 */
public class DetecteurSonActivityFragment extends Fragment {
    TextView tv;

    Thread myThread;
    Runnable runnable;
    int EPSILON;

    AudioRecord audioRecord;
    MediaPlayer mediaPlayer = null;

    ToggleButton btnOnOff;
    Button btntreshold;
    EditText edtTreshold;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();

            tv = (TextView)getActivity().findViewById(R.id.tv);
            tv.setText(bundle.getString("buffer"));
        }
    };

    Message msg;
    Bundle bundle;


    public DetecteurSonActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // redemarrer le player s'il existe encore
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.beepsound);

        // activer le son en provenance du micro
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT));

        if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {

            // demarrer l'enregistrement dans le buffer
            audioRecord.startRecording();


            EPSILON = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detecteurson, container, false);

        tv = (TextView) rootView.findViewById(R.id.tv);

        btnOnOff = (ToggleButton) rootView.findViewById(R.id.toggleButton);
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
        edtTreshold.setText("" +EPSILON);

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

        // si le thread n'existe pas encore, le creer
        if (myThread == null) {


            audioRecord.startRecording();

            // objet qui sera execute par le thread (la fonction run)
            runnable = new Runnable() {
                @Override
                public void run() {

                    int bufferSize = audioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
                    short[] byteBuffer = new short[bufferSize];
                    int moyenneBuffer = 0;
                    double moy = 0;
                    int j = 0;

                    audioRecord.startRecording();

                    while (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {

                        audioRecord.read(byteBuffer, 0, bufferSize);
                        moy = 0;
                        moyenneBuffer = 0;
                        for (int i = 0; i < bufferSize; i++) {
                            moyenneBuffer += byteBuffer[i];
                        }
                        moy = moyenneBuffer / bufferSize; // la moyenne des valeurs du buffer pour comparaison avec EPSILON
                        //tv.setText("Moyenne Buffer :" + String.valueOf(moy));

                        msg = handler.obtainMessage();
                        bundle = new Bundle();
                        bundle.putString("buffer","" + (int)moy);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                        // threshold pour eviter les bruits ambiants, ou detecter seulement les bruis forts
                        if (moy > EPSILON) {

                            mediaPlayer.start();

                        }
                    }

                }
            };
            // demarrer l'execution du thread
            myThread = new Thread(runnable);
            myThread.start();
        }
        // si le thread existe encore, le terminer pour arreter la detection
        else {
            if (myThread.isAlive()) {
                try {
                    audioRecord.stop();
                    myThread.interrupt();
                    myThread.join();
                    myThread = null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * Arreter le thread si un autre tab est clique ou si l'app est en pause
     */
    @Override
    public void onPause() {
        if(myThread != null) {
            if (myThread.isAlive()) {
                try {
                    btnOnOff.setChecked(false);
                    audioRecord.stop();
                    myThread.interrupt();
                    myThread.join();
                    myThread = null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop(){

        super.onStop();
    }
}

