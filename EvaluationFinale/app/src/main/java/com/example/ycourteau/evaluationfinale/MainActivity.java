package com.example.ycourteau.evaluationfinale;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {
    Thread myThread;

    private final int EPSILON = 1;
    AudioRecord audioRecord;
    MediaPlayer mediaPlayer = null;
    ToggleButton btnOnOff;
    private int isRecording = 1;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int bufferSize = audioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            //ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
            short[] byteBuffer = new short[bufferSize];
            int testRead;
            int testPosMarker;
            int testPosPeriod;
            int moyenneBuffer = 0;
            double moy = 0;
            int j = 0;
            audioRecord.startRecording();


            //if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {

            while (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {

                testRead = audioRecord.read(byteBuffer, 0, bufferSize);

                moy = 0;
                moyenneBuffer = 0;
                for (int i = 0; i < bufferSize; i++) {
                    moyenneBuffer += byteBuffer[i];
                }
                moy = moyenneBuffer / bufferSize;


                //if (mediaPlayer.isPlaying()) {
                //    mediaPlayer.pause();
                //}

                if (moy > EPSILON) {

                    //mediaPlayer.setLooping(true);

                    mediaPlayer.start();

                } else {
                    //mediaPlayer.setLooping(false);
                }

                Log.d("YOLAINE", "" + testRead);


            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setup action bar for tabs
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab();


        tab = actionBar.newTab()
                .setText(R.string.detecteurMetal)
                .setTabListener(new TabListener<DetecteurMetalActivityFragment>(
                        this, "metal", DetecteurMetalActivityFragment.class));
        actionBar.addTab(tab);


        tab = actionBar.newTab()
                .setText(R.string.detecteurElectricite)
                .setTabListener(new TabListener<DetecteurElectriciteActivityFragment>(
                        this, "electricite", DetecteurElectriciteActivityFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.detecteurSon)
                .setTabListener(new TabListener<DetecteurSonActivityFragment>(
                        this, "son", DetecteurSonActivityFragment.class));
        actionBar.addTab(tab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
