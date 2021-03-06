package com.palachance.myapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setup action bar for tabs
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.title_activity_detecteurmetal)
                .setTabListener(new TabListener<DetecteurMetalFragment>(
                        this, "artist", DetecteurMetalFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.title_activity_detecteurson)
                .setTabListener(new TabListener<DetecteurSonActivityFragment>(
                        this, "album", DetecteurSonActivityFragment.class));
        actionBar.addTab(tab);
        tab = actionBar.newTab()
                .setText(R.string.title_activity_detecteurelectrique)
                .setTabListener(new TabListener<DetecteurElectriqueFragment>(
                        this, "album", DetecteurElectriqueFragment.class));
        actionBar.addTab(tab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /* if (id == R.id.action_settings) {
            return true;
        } */

        return super.onOptionsItemSelected(item);
    }
}