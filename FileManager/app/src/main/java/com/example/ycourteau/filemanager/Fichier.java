package com.example.ycourteau.filemanager;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ycourteau on 15-09-14.
 */
public class Fichier {


    public static void writeFileToInternalDirectory(Context context) {

        String filename = "myfile";
        String filename2 = "myfile2";
        String string = "Hello world!";
        FileOutputStream outputStream;

        File file2 = new File(context.getFilesDir(), filename2);

        try {
            outputStream = context.openFileOutput(filename2, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getTempFile(Context context, String url) {
        File file = null;

        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e("getAlbumStorageDir", "Directory not created");
        }
        return file;
    }

    public static File getAlbumStorageDirContext(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("StorageDirContext", "Directory not created");
        }
        return file;
    }

    public static void writeFileToExternalDirectory(Context context) {

        String filename = "myfile.txt";
        String filename2 = "myfile2";
        String string = "Hello world!";
        FileOutputStream outputStream;

        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), filename);

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFileToExternalDirectoryDocuments(Context context) {

        //File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        try {

            FileOutputStream fos = null;

            fos = context.openFileOutput("fichierExternePublic1.txt", Context.MODE_APPEND | Context.MODE_WORLD_READABLE);
            fos.write("fos testFichierExternePublic.txt".getBytes());
            fos.close();

            String storageState = Environment.getExternalStorageState();

            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                //File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "fichierExternePublic3.txt");

                FileOutputStream fos2 = null;
                try {
                    fos2 = new FileOutputStream(file);


                    fos2.write("fos 2testFichierExternePublic".getBytes());

                    fos2.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


}
