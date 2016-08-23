package pam.ycourteau.example.partagedonneesetudiants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().setHomeButtonEnabled(true);
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

        switch (id){

            case R.id.action_settings:
                Toast.makeText(this, "MainActivity", Toast.LENGTH_LONG).show();
                break;

            case R.id.action_test:
                Toast.makeText(this, "MainActivity", Toast.LENGTH_LONG).show();
                break;

            default:
                return true;
                //break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * bouton pour demarrer l'activite pour le partage de texte
     * @author ycourteau
     * @param v
     */
    public void btnPartageTexteOnClick(View v){
        Intent intent = new Intent(MainActivity.this, PartageTexte.class);
        startActivity(intent);
    }
}
