package pam.ycourteau.example.laboratoire_1_fichiers_sqlite.controleur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ycourteau on 15-09-24.
 */
public class Fichier {

    private Uri uri;
    private String text;
    private Context context;

    // A request code's purpose is to match the result of a "startActivityForResult" with
    // the type of the original request.  Choose any value.
    private static final int READ_REQUEST_CODE = 1337;
    public static final String TAG = "FileActivityFragment";

    public boolean Sauvegarder(Context context, Uri uri, String text){

        String storageState = Environment.getExternalStorageState();

        Toast.makeText(context, "onSaveClick", Toast.LENGTH_LONG).show();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            try {
                //OutputStream outpuStream = getActivity().getContentResolver().openOutputStream(uri);
                OutputStream outpuStream = context.getContentResolver().openOutputStream(uri);

                //String text = etFile.getText().toString();

                outpuStream.write(text.getBytes());
                outpuStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return true;
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
        intent.setType("text/plain");
        intent.setClass(context, FileActivityFragment.class);

        ((Activity)context).startActivityForResult(intent, READ_REQUEST_CODE);
        // END_INCLUDE (use_open_document_intent)
    }

    //@Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        Log.i(TAG, "Received an \"Activity Result\"");
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
                this.uri = uri;
                Log.i(TAG, "Uri: " + uri.toString());


                LoadTextFile(uri);
                //showImage(uri);
            }
            // END_INCLUDE (parse_open_document_response)
        }
    }

    public void LoadTextFile(Uri uri) {

        //File file = new File(uri.toString());
        String path = Uri.fromFile(new File(uri.toString())).getLastPathSegment();
        String text = "";

        try {
            //FileReader fr = new FileReader(file.getPath());
            FileReader fr = new FileReader(path);
            //BufferedReader txtReader = new BufferedReader(new FileReader(path.toString()));
            BufferedReader txtReader = new BufferedReader(fr);
            String s = "";
            while ((s = txtReader.readLine()) != null) {
                text.concat(s);//.append(s);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.text = text;
/*
        try {
            String content = readFileContent(uri);
            etFile.setText(content.toString());
            tvFileName.setText(path);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /*
    private String readFileContent(Uri uri) throws IOException {

        InputStream inputStream =
                ((Activity)context).getContentResolver().openInputStream(uri);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(
                        inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentline;
        while ((currentline = reader.readLine()) != null) {
            stringBuilder.append(currentline + "\n");
        }
        inputStream.close();
        return stringBuilder.toString();
    }


*/


}
