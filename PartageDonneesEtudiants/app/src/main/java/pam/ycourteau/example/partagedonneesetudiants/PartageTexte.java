package pam.ycourteau.example.partagedonneesetudiants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class PartageTexte extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partage_texte);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        switch (id) {

            case android.R.id.home:

                //Intent intent = new Intent(this, MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);

                // OU

                NavUtils.navigateUpFromSameTask(this);

                break;

            case R.id.action_settings:

                Toast.makeText(this, "PartageTexte", Toast.LENGTH_LONG).show();
                break;

            case R.id.action_test:
                Toast.makeText(this, "PartageTexte", Toast.LENGTH_LONG).show();
                break;

            default:
                return true;
            //break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnPartageTexteOnClick(View v){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        //startActivity(sendIntent);

        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }
}
