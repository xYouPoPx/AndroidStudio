package com.example.ycourteau.filesmanager;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ycourteau on 15-09-12.
 */
public class InternalExternalStorage {


    public static void ecrireFichierInternal(Context context) {
        String filename = "fichierInterne";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {

            // getApplicationContext() ou passer le contexte en parametre (moins complique)
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();

            Toast.makeText(context, "Fichier Interne Ecrit", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ecrireFichierExternal(Context context) {
        String filename = "fichierExterne";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {

            // getApplicationContext() ou passer le contexte en parametre (moins complique)
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();

            Toast.makeText(context, "Fichier Externe Ecrit", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ecrireFichierExternalPrivate(Context context) {


        try {

            FileOutputStream fos = context.openFileOutput("fichierExternePublic1",

                    Context.MODE_APPEND | Context.MODE_WORLD_READABLE);

            fos.write("fos testFichierExternePublic.txt".getBytes());

            fos.close();


            String storageState = Environment.getExternalStorageState();

            if (storageState.equals(Environment.MEDIA_MOUNTED)) {

                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "fichierExternePublic2.txt");

                FileOutputStream fos2 = new FileOutputStream(file);

                fos2.write("fos 2testFichierExternePublic".getBytes());

                fos2.close();

                Toast.makeText(context, "fichier " + file.getName() + " ecrit dans " + context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), Toast.LENGTH_LONG);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    public static void ecrireFichierExternalPublic(Context context) {


        String content = "hello world";
        File file;
        FileOutputStream outputStream;

        try {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "MyCache.txt");

            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File creerDossierExternalPublic(String dirName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), dirName);
        if (!file.mkdirs()) {
            Log.e("creerDossierExternalPublic", "Directory not created");
        }
        return file;
    }
}
