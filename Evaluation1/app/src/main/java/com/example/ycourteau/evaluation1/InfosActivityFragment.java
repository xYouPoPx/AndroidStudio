package com.example.ycourteau.evaluation1;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * A placeholder fragment containing a simple view.
 */
public class InfosActivityFragment extends Fragment {

    private TextView batteryLevel;

    public InfosActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_infos, container, false);

        batteryLevel = (TextView) rootView.findViewById(R.id.batteryLevel);
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = rootView.getContext().registerReceiver(null, iFilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        //registerReceiver(myBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        batteryLevel.setText("level " + level / (float) scale);
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}
