package pam.ycourteau.example.partagedonnees;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final int READ_REQUEST_CODE = 1337;

    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        return super.onOptionsItemSelected(item);
    }

    public void shareTextOnClick(View view) {
        Intent intent = new Intent(this, ShareText.class);
        startActivity(intent);

    }

    public void shareImageOnClick(View view) {
        Intent intent = new Intent(this, SharePicture.class);
        startActivity(intent);

    }

    public void onClickbtnSendIntent(View v){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    /**
     * Cree un chooser pour envoyer sur une plateforme quelconque
     * @param v
     */
    public void onClickbtnSendIntentChooser(View v){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        //sendIntent.setType("text/plain");
        sendIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)), READ_REQUEST_CODE);
    }

    public void onClickbtnSendMultiplePieces(View v){

        //performFileSearch();

        Uri imageUri1 = Uri.parse(Uri.parse("content://com.android.providers.media.documents/document/image%3A23531").getLastPathSegment());
        Uri imageUri2 = Uri.parse(Uri.parse("content://com.android.providers.media.documents/document/image%3A23532").getLastPathSegment());
        Uri imageUri3 = Uri.parse(Uri.parse("content://com.android.providers.media.documents/document/image%3A23533").getLastPathSegment());

        //File dir = getFilesDir();

         //Uri uri = Uri.fromFile(new File(getFilesDir(), "foo.jpg"));

        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        imageUris.add(imageUri1); // Add your image URIs here
        imageUris.add(imageUri2);
        imageUris.add(imageUri3);


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");
        //shareIntent.setType("*/*");

        startActivity(Intent.createChooser(shareIntent, "Share images to.."));


    }

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void performFileSearch() {

        // BEGIN_INCLUDE (use_open_document_intent)
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a file (as opposed to a list
        // of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers, it would be
        //intent.setType("*/*");
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
        // END_INCLUDE (use_open_document_intent)
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
       // Log.i(TAG, "Received an \"Activity Result\"");
        // BEGIN_INCLUDE (parse_open_document_response)
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code READ_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent,
        // and the below code shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.  Pull that uri using "resultData.getData()"
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();


                String mediaPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                //Uri uri2 = Uri.fromFile(new File(getFilesDir(), "foo.jpg"));
                Uri uri1 = Uri.parse(uri.getLastPathSegment());
                String path = String.valueOf(Uri.parse(uri1.toString()));
                String path2 = String.valueOf(Uri.parse(uri.getLastPathSegment().toString()));
                Uri uri2 = Uri.fromFile(new File(getFilesDir(), path));
                Uri uri3 = Uri.fromFile(new File(getFilesDir(), path2));
                String path3 = Uri.decode(Uri.fromFile(new File(getFilesDir(), path2)).toString());
                String path4 = Uri.fromFile(new File(getFilesDir(), path)).toString();

                this.uri = uri;
                //Log.i(TAG, "Uri: " + uri.toString());


                //LoadTextFile(uri);
                //showImage(uri);
            }
            // END_INCLUDE (parse_open_document_response)
        }
    }



}
