package com.pam.ycourteau.exemplecapteurs;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class CapteurActivity extends AppCompatActivity implements SensorEventListener{

    // service pour les capteurs
    private SensorManager mSensorManager;

    private Sensor mSensor;
    String name;
    String manuf;
    String version;

    // textview pour faire afficher les capteurs
    TextView tv_capteurInfos = null;
    TextView tv_capteurDonnees = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // demarrer service de capteurs
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //capteur geomagnetique
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);

        // associer la variable \ la boite de texte dans le layout
        tv_capteurInfos = (TextView) findViewById(R.id.tv_capteurInfos);
        tv_capteurDonnees = (TextView) findViewById(R.id.tv_capteurDonnees);

        // ajouter du texte dans les boites de texte
        tv_capteurInfos.append("\n" + mSensor.getName() + "\n" + mSensor.getVendor() + "\n" + mSensor.getVersion());
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public final void onSensorChanged(SensorEvent event){
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = event.values[0];
        tv_capteurDonnees.setText("\n" + event.values[0]);

        int length = event.values.length;

        // Do something with this sensor value.

        switch(event.sensor.getType()){
            /*case Sensor.TYPE_LIGHT:
                Toast.makeText(getApplicationContext(), "case Sensor.TYPE_LIGHT:",Toast.LENGTH_LONG).show();
                Log.println(Log.INFO, "tag", "case Sensor.TYPE_LIGHT:");
                break;
*/
            case Sensor.TYPE_MAGNETIC_FIELD:
                break;

            default:
                break;
        }




    }

    /**
     * Called when the accuracy of the registered sensor has changed.
     * <p/>
     * <p>See the SENSOR_STATUS_* constants in
     * {@link SensorManager SensorManager} for details.
     *
     * @param sensor
     * @param accuracy The new accuracy of this sensor, one of
     *                 {@code SensorManager.SENSOR_STATUS_*}
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
