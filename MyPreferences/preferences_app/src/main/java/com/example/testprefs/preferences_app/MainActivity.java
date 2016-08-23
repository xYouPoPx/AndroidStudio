package com.example.testprefs.preferences_app;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;


/*
displays the main settings in PrefsFragment - uses preferences.xml
- contained intent starts HeadersActivity

            www.101apps.co.za
*/


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content,
                        new MainSettingsFragment()).commit();
    }

    public static class MainSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
