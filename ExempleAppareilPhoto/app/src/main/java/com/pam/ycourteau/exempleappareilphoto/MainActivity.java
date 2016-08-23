package com.pam.ycourteau.exempleappareilphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;
    private static final int ACTION_TAKE_PICTURE = 1;
    private static final int ACTION_SHOW_GALLERY = 2;

    private Button btn_Picture;
    private Button btn_Gallery;
    private ImageView iv_MonImage;
    private Bitmap mImageBitmap;

    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_MonImage = (ImageView)findViewById(R.id.iv_MonImage);

        btn_Picture = (Button)findViewById(R.id.btn_Picture);
        btn_Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchIntent(ACTION_TAKE_PICTURE);
            }
        });
        btn_Gallery = (Button)findViewById(R.id.btn_Gallery);
        btn_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchIntent(ACTION_SHOW_GALLERY);

            }
        });

    }

    private void afficherPhoto(){
        Toast.makeText(this, "afficherPhoto()", Toast.LENGTH_LONG).show();

        iv_MonImage.setImageURI(uri);
        iv_MonImage.setVisibility(View.VISIBLE);

    }

    /**
     *
     * @param actionCode
     */
    private void dispatchIntent(int actionCode){

        Intent intent = null;

        switch(actionCode) {

            case ACTION_TAKE_PICTURE:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                break;

            case ACTION_SHOW_GALLERY:

                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");

                break;

            default:
                break;
        } // switch

        startActivityForResult(intent, actionCode);

    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){
            case ACTION_SHOW_GALLERY:
                Toast.makeText(this, "case ACTION_SHOW_GALLERY", Toast.LENGTH_LONG);

                if(resultCode == RESULT_OK) {
                    uri = data.getData();
                    Toast.makeText(this, "case ACTION_SHOW_GALLERY", Toast.LENGTH_LONG);
                }

                afficherPhoto(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
