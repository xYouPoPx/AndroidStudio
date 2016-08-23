package com.pam.ycourteau.exempleintentservicebackground;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String ACTION_FOO = "com.pam.ycourteau.exempleintentservicebackground.action.FOO";
    private static final String ACTION_BAZ = "com.pam.ycourteau.exempleintentservicebackground.action.BAZ";

    Intent mServiceIntent;
    private String dataUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
 * Creates a new Intent to start the RSSPullService
 * IntentService. Passes a URI in the
 * Intent's "data" field.
 */
        mServiceIntent = new Intent(this, MyIntentService.class);
        //mServiceIntent.setData(Uri.parse(dataUrl));
        mServiceIntent.setAction(ACTION_FOO);

        // Starts the IntentService
        this.startService(mServiceIntent);

    }


}
