package pam.ycourteau.example.partagedonnees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * http://developer.telerik.com/featured/social-media-integration-android/
 */
public class ShareText extends ActionBarActivity{

    private EditText share_text_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_text);

        share_text_entry = (EditText) findViewById(R.id.share_text_entry);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share_text, menu);
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

        return super.onOptionsItemSelected(item);
    }


    public void shareGenericOnClick(View v) {
        String userEntry = share_text_entry.getText().toString();
        //String userEntry = "http://stackoverflow.com/questions/7545254/android-and-facebook-share-intent";

        Intent textShareIntent = new Intent(Intent.ACTION_SEND);
        textShareIntent.putExtra(Intent.EXTRA_TEXT, userEntry);
        textShareIntent.setType("text/plain");
        startActivity(textShareIntent);
    }
}
