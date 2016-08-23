package com.example.testprefs.preferences_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import java.util.List;

/**
 * Created by clive on 16-May-14.
 *
 *          www.101apps.co.za
 *
 *          displays preference_headers.xml - selection of either Header
 *          displays appropriate (determined by extra argument)
 *          preferences in HeadersFragment
 */
public class HeadersActivity extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers,target);
    }

    public static class HeadersFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            String settings = getArguments().getString("header");
            if ("one".equals(settings)) {
                addPreferencesFromResource(R.xml.preference_header_one);
            } else if ("two".equals(settings)) {
                addPreferencesFromResource(R.xml.preference_header_two);
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences
                                                sharedPreferences, String key) {
            if (key.equals("food_preference")) {
                Preference foodPref = findPreference(key);
                foodPref.setSummary(sharedPreferences.getString(key, ""));
            }
        }
    }
}
