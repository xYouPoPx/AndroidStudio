package pam.ycourteau.example.laboratoire_1_fichiers_sqlite.controleur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import pam.ycourteau.example.laboratoire_1_fichiers_sqlite.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class FileActivityFragment extends Fragment implements TextWatcher, View.OnClickListener {

    // A request code's purpose is to match the result of a "startActivityForResult" with
    // the type of the original request.  Choose any value.
    private static final int READ_REQUEST_CODE = 1337;
    public static final String TAG = "FileActivityFragment";

    private EditText etFile;
    private Button btnSave;
    private TextView tvFileName;

    private Uri uri;

    public FileActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_file, container, false);

        etFile = (EditText) rootView.findViewById(R.id.etFile);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        tvFileName = (TextView) rootView.findViewById(R.id.tvFileName);

        etFile = (EditText) rootView.findViewById(R.id.etFile);
        etFile.addTextChangedListener(this);

        performFileSearch();
        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
                return true;
                //return super.onOptionsItemSelected(item);
        }

        //return false;
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
        //intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
        // END_INCLUDE (use_open_document_intent)
    }

    @Override
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

    /**
     *
     * @author ycourteau
     * @param uri chemin fichier
     *
     *
     * description de la fonction
     */
    public void LoadTextFile(Uri uri) {

        String path = Uri.fromFile(new File(uri.toString())).getLastPathSegment();

        try {

            FileReader fr = new FileReader(path);

            BufferedReader txtReader = new BufferedReader(fr);
            String s = "";
            while ((s = txtReader.readLine()) != null) {
                etFile.append(s);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String content = readFileContent(uri);
            etFile.setText(content.toString());
            tvFileName.setText(path);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String readFileContent(Uri uri) throws IOException {

        InputStream inputStream =
                getActivity().getContentResolver().openInputStream(uri);
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

    @Override
    public void afterTextChanged(Editable s) {
        Toast.makeText(getActivity().getApplicationContext(), "afterTextChanged", Toast.LENGTH_LONG).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Toast.makeText(getActivity().getApplicationContext(), "beforeTextChanged", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        Toast.makeText(getActivity().getApplicationContext(), "onTextChanged", Toast.LENGTH_LONG).show();

        if (before != 0) {
            btnSave.setVisibility(View.VISIBLE);
            btnSave.setOnClickListener(this);
        } else {
            btnSave.setVisibility(View.GONE);

        }

    }

    @Override
    public void onClick(View v) {

        Context context;
        context = getActivity().getApplicationContext();
        String storageState = Environment.getExternalStorageState();

        Toast.makeText(context, "onSaveClick", Toast.LENGTH_LONG).show();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            try {
                OutputStream outpuStream = getActivity().getContentResolver().openOutputStream(uri);

                String text = etFile.getText().toString();

                outpuStream.write(text.getBytes());

                outpuStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
