package com.example.ycourteau.jackoftools;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

/**
 * Created by ycourteau on 15-08-13.
 */
public  class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    /** Constructor used each time a new tab is created.
     * @param activity  The host Activity, used to instantiate the fragment
     * @param tag  The identifier tag for the fragment
     * @param clz  The fragment's Class, used to instantiate the fragment
     */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        switch (mTag){
            case "gyroscope":
                GyroscopeActivityFragment gyroFragment = new GyroscopeActivityFragment();
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.replace(R.id.item_detail_container, mFragment, mTag);
                break;
            case "orientation":
                OrientationActivityFragment orientationFragment = new OrientationActivityFragment();
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.replace(R.id.item_detail_container, mFragment, mTag);
                break;
            default:
                break;
        }
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}