package pam.ycourteau.example.partagedonnees;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * http://developer.telerik.com/featured/social-media-integration-android/
 */
public class SharePicture extends ActionBarActivity {

    Button cameraButton;
    ImageView thumbnail;
    Bitmap picture;
    Uri pictureUri;

    private static final int PHOTO_ID = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_picture);

        cameraButton = (Button) findViewById(R.id.share_picture_camera_button);
        thumbnail = (ImageView) findViewById(R.id.share_picture_thumbnail);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share_picture, menu);
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

    public void cameraButtonOnClick(View v) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PHOTO_ID);
    }

    public void sharePictureOnClick(View v) {

        if (picture == null) {
            Toast.makeText(getApplicationContext(), "Take a valid picture first!", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, pictureUri);
            startActivity(Intent.createChooser(intent, "Share picture with..."));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PHOTO_ID) {
            if (resultCode == RESULT_OK) {
                // we got a picture back
                this.showPicture(intent);
            }
        }
    }

    private void showPicture(Intent intent) {
        Bundle intentExtras = intent.getExtras();
        picture = (Bitmap) intentExtras.get("data");
        pictureUri = intent.getData();

        if (picture != null) {
            thumbnail.setImageBitmap(picture);
        }
    }

}
