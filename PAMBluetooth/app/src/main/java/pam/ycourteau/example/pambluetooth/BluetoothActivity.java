package pam.ycourteau.example.pambluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BluetoothActivity extends AppCompatActivity {


    final int ACTION_REQUEST_ENABLE = 1;
    BluetoothAdapter mBluetoothAdapter;
    final int REQUEST_CODE_ENABLE_BLUETOOTH = 1438;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "BluetoothAdapter.getDefaultAdapter == null", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(getApplicationContext(), "BluetoothAdapter.getDefaultAdapter == 1438", Toast.LENGTH_LONG);
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ACTION_REQUEST_ENABLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            return;

        if (resultCode == RESULT_OK) {
            // L'utilisation a activé le bluetooth
        } else {
            // L'utilisation n'a pas activé le bluetooth
        }
        */
        switch (resultCode){
            case 1438:
                Toast.makeText(getApplicationContext(), "result code = 1438 OK", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (id){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            /*case R.id.connect_bluetooth:
                Toast.makeText(getApplicationContext(), "connect_bluetooth", Toast.LENGTH_LONG).show();

                Intent bluetoothIntent = new Intent(this, BluetoothActivity.class);
                bluetoothIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(bluetoothIntent);
                break;
            */
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
