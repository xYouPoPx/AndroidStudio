package pam.ycourteau.example.pampartage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.FacebookSdk;

//import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends ActionBarActivity {

    Uri uri;
    Bitmap image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
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

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();
                break;

            case R.id.save: {
                Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_LONG).show();
                //return super.onOptionsItemSelected(item);
                break;
            }

            case R.id.open:
                Intent openIntent = new Intent(this, FileActivity.class);
                startActivity(openIntent);
                break;

            case R.id.action_share:
                Intent shareIntent = new Intent(this, PartageActivity.class);
                startActivity(shareIntent);
                break;

            default:
                Toast.makeText(getApplicationContext(), "aucune action", Toast.LENGTH_LONG).show();
                break;
        }

        return false;
        //return super.onOptionsItemSelected(item);
    }

}
