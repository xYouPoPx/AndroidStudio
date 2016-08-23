package com.example.ycourteau.actionbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ycourteau on 15-08-13.
 */
public class AlbumFragment extends Fragment {

    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //tv = (TextView)findViewById(R.id.tvFrag1);
        //tv.setText("openSearch");

        return inflater.inflate(R.layout.fragment_album, container, false);
    }
}
