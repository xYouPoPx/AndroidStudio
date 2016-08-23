package pam.ycourteau.example.pampartage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PartageActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginManager loginManager;
    //See more at: http://simpledeveloper.com/how-to-share-an-image-on-facebook-in-android/#sthash.g6ZDtEKo.dpuf

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partage);



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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        List<String> permissionNeeds = Arrays.asList("publish_actions");

        //this loginManager helps you eliminate adding a LoginButton to your UI
        loginManager = LoginManager.getInstance();

        loginManager.logInWithPublishPermissions(this, permissionNeeds);

        loginManager.registerCallback(callbackManager, new FacebookCallback()
        {
           /**
             * Called when the dialog completes without error.
             * <p/>
             * Note: This will be called instead of {@link #onCancel()} if any of the following conditions
             * are true.
             * <ul>
             * <li>
             * {@link MessageDialog} is used.
             * </li>
             * <li>
             * The logged in Facebook user has not authorized the app that has initiated the dialog.
             * </li>
             * </ul>
             *
             * @param o Result from the dialog
             */
            @Override
            public void onSuccess(Object o) {
                //sharePhotoToFacebook();
            }

            @Override
            public void onCancel()
            {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception)
            {
                System.out.println("onError");
            }
        });
        //See more at: http://simpledeveloper.com/how-to-share-an-image-on-facebook-in-android/#sthash.g6ZDtEKo.dpuf
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.open:
                performFileSearch();
                break;

            default:
                //super.onOptionsItemSelected(item);
                //Toast.makeText(getApplicationContext(), "aucune action", Toast.LENGTH_LONG).show();
                //break;
                return true;
            //return super.onOptionsItemSelected(item);
        }

        return false;
    }



    // L'objectif d'un code de demande est de faire correspondre le résultat d'une "startActivityForResult" avec
    // le type de la demande initiale. Choisissez une valeur quelconque.
    private static final int READ_REQUEST_CODE = 1337;
    public static final String TAG = "PartageActivityFragment";

    public Bitmap getBitmapFromUri(Uri uri) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (Exception e) {
            Log.e(TAG, "Impossible d'ouvrir le fichier");
            return null;
        } finally {
            try {
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error closing ParcelFile Descriptor");
            }
        }
    }
    /**
     * Donné le Uri de l'image, montre à l'écran en utilisant un DialogFragment.
     *
     * @param uri l'URI de l'image à afficher.
     */
    public void showImage(Uri uri) {

        if (uri != null) {
            // Obtenir l'image à partir du URI.
            Bitmap image = getBitmapFromUri(uri);

            ShareDialog shareDialog;
            shareDialog = new ShareDialog(this);
            // Une fois que nous avons l'image, il faut construire le SharePhotoContent
            // en utilisant le SDK de FaceBook
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();
            // Ouvre la page de connexion à FaceBook et affiche la page de partage
            // avec l'image choisie.
            //shareDialog.show(content);

        }
    }

    private void sharePhotoToFacebook(Bitmap image){
        //Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);

    }
    // ee more at: http://simpledeveloper.com/how-to-share-an-image-on-facebook-in-android/#sthash.g6ZDtEKo.dpuf

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        // BEGIN_INCLUDE (parse_open_document_response)
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code READ_REQUEST_CODE.
        // Si le code de demande vu ici ne correspond pas, il est la réponse à une autre intent,
        // et le code ci-dessous ne doit pas fonctionner du tout.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Le document sélectionné par l'utilisateur ne sera pas retourné dans le intent.
            // Au lieu de cela, un URI à ce document sera contenue dans le retour intent
            //à condition que ce procédé en tant que paramètre. Tirez que uri aide "resultData.getData()"
            Uri uri = null;
            if (resultData != null) {
                this.uri = resultData.getData();
                showImage(uri);
            }
            // END_INCLUDE (parse_open_document_response)
        }


        // http://simpledeveloper.com/how-to-share-an-image-on-facebook-in-android/
        super.onActivityResult(requestCode, resultCode, resultData);
        callbackManager.onActivityResult(requestCode, resultCode, resultData);
    }


    /**
     * Tire une intention de tourner jusqu'à l'interface utilisateur "sélecteur de fichier" et sélectionnez une image.
     */
    public void performFileSearch() {

        // BEGIN_INCLUDE (use_open_document_intent)
        //ACTION_OPEN_DOCUMENT est l'intention de choisir un fichier via le navigateur de fichiers du système.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filtrez uniquement les résultats montrent que l'on peut "ouverts", comme un fichier (par opposition à une liste
        // de contacts ou de fuseaux horaires)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        //Filtre pour afficher uniquement les images, en utilisant le type de données MIME d'image.
        //Si l'on voulait chercher des fichiers Ogg Vorbis, le type serait "audio / ogg".
        //Pour rechercher tous les documents disponibles par l'intermédiaire de fournisseurs de stockage installés, il serait
        //"*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
        // END_INCLUDE (utiliser document ouvert)
    }

}
