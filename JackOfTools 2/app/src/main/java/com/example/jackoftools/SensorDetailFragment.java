package com.example.jackoftools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jackoftools.dummy.DummyContent;


/**
 * A fragment representing a single Sensor detail screen. This fragment is
 * either contained in a {@link SensorListActivity} in two-pane mode (on
 * tablets) or a {@link SensorDetailActivity} on handsets.
 */
public class SensorDetailFragment extends Fragment implements SensorEventListener{
	
	//Variable holding an instance of the sensor service
	private SensorManager mSensorManager;
	//Variable holding a view to display sensor data
	private TextView tv_sensor;
	//Variable holding an instance of accelerometer sensor
	private Sensor mAccelerometer;
	//Variable holding an instance of orientation sensor
	private Sensor mOrientation;
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SensorDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
			//Instantiation of a sensor service object
			mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
			
			//Instantiation of specific sensors objects
			mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

	}
	
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            
            //Monitoring data of sensors when data is changing
            if(mItem.id == "1" && sensor.getType() == Sensor.TYPE_ACCELEROMETER) 
            {
            	// Show the dummy content as text in a TextView.
            	tv_sensor.setText("Accelerometer sensor : " + "\nX : " + event.values[0] + "\nY : " + event.values[1] + "\nZ : " + event.values[2]);
            } 
            if (mItem.id == "2" && sensor.getType() == Sensor.TYPE_ORIENTATION) 
            {
            	tv_sensor.setText("Orientation sensor : " +  "\nX : " + event.values[0] + "\nY : " + event.values[1] + "\nZ : " + event.values[2]);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Accuracy is not managed in this app
        }
    
        @Override
    	public void onResume() {
    	    super.onResume();
    	    //Register sensor's listeners
    	    mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    	    mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    	    
    	  }
	
	  @Override
	  	public void onPause() {
	    super.onPause();
	    //Unregister sensor's listener
	    mSensorManager.unregisterListener(this);
	  }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sensor_detail,
				container, false);
		
		tv_sensor = ((TextView) rootView.findViewById(R.id.sensor_detail));
		
		return tv_sensor;
	}
}
