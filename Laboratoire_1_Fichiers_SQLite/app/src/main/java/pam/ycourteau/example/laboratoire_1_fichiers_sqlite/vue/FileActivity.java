package pam.ycourteau.example.laboratoire_1_fichiers_sqlite.vue;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import pam.ycourteau.example.laboratoire_1_fichiers_sqlite.R;


public class FileActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                //Intent intent = new Intent(this, MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
                return false;

            case R.id.action_settings:
                //Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();
                //break;
                return false;

            case R.id.save:
                //super.onOptionsItemSelected(item);
                //break;
                return true;
            //return super.onOptionsItemSelected(item);


            case R.id.open:
                //Intent i = new Intent(this, FileActivity.class);
                //startActivity(i);
                //break;
                return false;
            default:
                //super.onOptionsItemSelected(item);
                //Toast.makeText(getApplicationContext(), "aucune action", Toast.LENGTH_LONG).show();
                //break;
                return false;
            //return super.onOptionsItemSelected(item);
        }

        //return false;
    }
}
