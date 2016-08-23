package com.pam.ycourteau.exemplemediaplayer;

import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    int currentPosition;
    String thisTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        long id = 0; // = /* retrieve it from somewhere */; // le id doit provenir du content provider lors du clic sur un item du listview

        // ici utiliser plut[ot le FileChooser provrnant de ExempleFileChooser! l'uri provient du onActivityResult
        Uri contentUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(getApplicationContext(), contentUri);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // ...prepare and start...
        // utiliser la fonction prepare au lieu de create
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.toilet);
        mediaPlayer.setVolume(0.9f, 0.9f);
        //mediaPlayer.start(); // no need to call prepare(); create() does that for you




    }

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();

        mediaPlayer.pause();

    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();

        mediaPlayer.start();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // toujours release le media player afin de liberer la memoire
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }


    }
}
