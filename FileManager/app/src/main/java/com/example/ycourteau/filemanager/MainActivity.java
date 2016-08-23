package com.example.ycourteau.filemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        switch (item.getItemId()) {
            case R.id.action_settings:

                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                String strUserName = SP.getString("username", "NA");
                boolean bAppUpdates = SP.getBoolean("applicationUpdates",false);
                String downloadType = SP.getString("downloadType","1");
/*

                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content,
                                new SettingsFragment()).commit();
*/

                // Lance settings activity
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
            // plus de code...
        }
        return true;
    }

    public void writeFileToInternalDirectoryOnClick(View v) {

        Fichier.writeFileToInternalDirectory(getApplicationContext());
    }

    public void getTempFileOnClick(View v) {

        File myFile = Fichier.getTempFile(getApplicationContext(), "aagfhgf");
    }

    public void isExternalStorageWritableOnClick(View v) {

        Boolean bool = Fichier.isExternalStorageWritable();
    }

    public void isExternalStorageReadableOnClick(View v) {

        Boolean bool = Fichier.isExternalStorageReadable();
    }

    public void getAlbumStorageDirOnClick(View v) {
        Fichier.getAlbumStorageDir("test.txt");
    }

    public void getAlbumStorageDirContextOnClick(View v) {
        Fichier.getAlbumStorageDirContext(getApplicationContext(), "test2");
    }

    public void writeFileToExternalDirectoryOnClick(View v) {
        Fichier.writeFileToExternalDirectory(getApplicationContext());
    }

    public void writeFileToExternalDirectoryDocumentsOnClick(View v){
        Fichier.writeFileToExternalDirectoryDocuments(getApplicationContext());
    }


}
