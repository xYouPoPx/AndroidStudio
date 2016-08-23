package com.example.testprefs.preferences_app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.util.Set;

/**
 * Created by clive on 16-May-14.
 *
 * gets the shared preferences and shows them in a table
 */
public class ShowPreferences extends Activity {

    private static final String PARENT_EXPLORER_PREFERENCE = "parent_explorer_preference";
    private static final String CHILD_EXPLORER = "child_explorer_preference";
    private static final String GUIDE_PREFERENCE = "guide_preference";
    private static final String CITIZEN_PREFERENCE = "citizen_preference";
    private static final String TRANSPORT_PREFERENCE = "transport_preference";
    private static final String FOOD_PREFERENCE = "food_preference";
    private static final String NAME_PREFERENCE = "name_preference";
    private static final String MULTI_CHOICE_PREFS = "multi_choice_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpreferences);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        boolean parentExplorerPref = sharedPreferences
                .getBoolean(PARENT_EXPLORER_PREFERENCE, true);

        boolean childExplorer = sharedPreferences.getBoolean(CHILD_EXPLORER, true);
        boolean guidePref = sharedPreferences.getBoolean(GUIDE_PREFERENCE, true);
        boolean citizenPref = sharedPreferences.getBoolean(CITIZEN_PREFERENCE, true);
        String transportPref = sharedPreferences.getString(TRANSPORT_PREFERENCE, "empty");
        String foodPref = sharedPreferences.getString(FOOD_PREFERENCE, "empty");
        String namePref = sharedPreferences.getString(NAME_PREFERENCE, "empty");

        Set<String> multiChoicePrefs = sharedPreferences.getStringSet(MULTI_CHOICE_PREFS, null);
        String multiChoiceString = "";
        for(String s:multiChoicePrefs) {
            multiChoiceString = multiChoiceString + "\n" + s;
        }

        TextView textViewMultiChoice = (TextView) findViewById(R.id.multi_choice_pref_text);
        textViewMultiChoice.setText(multiChoiceString);

        TextView textViewParent = (TextView) findViewById(R.id.parent_pref_text);
        textViewParent.setText(String.valueOf(parentExplorerPref));

        TextView textViewChild = (TextView) findViewById(R.id.child_pref_text);
        textViewChild.setText(String.valueOf(childExplorer));

        TextView textViewGuide = (TextView) findViewById(R.id.guide_pref_text);
        textViewGuide.setText(String.valueOf(guidePref));

        TextView textViewCitizen = (TextView) findViewById(R.id.citizen_pref_text);
        textViewCitizen.setText(String.valueOf(citizenPref));

        TextView textViewTransport = (TextView) findViewById(R.id.transport_pref_text);
        textViewTransport.setText(String.valueOf(transportPref));

        TextView textViewFood = (TextView) findViewById(R.id.food_pref_text);
        textViewFood.setText(String.valueOf(foodPref));

        TextView textViewName = (TextView) findViewById(R.id.name_pref_text);
        textViewName.setText(String.valueOf(namePref));
    }

}
