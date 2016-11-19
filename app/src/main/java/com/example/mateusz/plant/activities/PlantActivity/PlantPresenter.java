package com.example.mateusz.plant.activities.PlantActivity;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.activities.MainActivity.MainActivity;
import com.example.mateusz.plant.model.UploadResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Mateusz on 2016-09-13.
 */
public class PlantPresenter implements PlantPresenterInt {

    PlantActivity view;
    DBConnection conn;
    protected Uri fileUri;

    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();
    public PlantPresenter(PlantActivity plantActivity) {
        this.view = plantActivity;
        this.conn = Factory.getApiConnection();
    }

    @Override
    public void loadPicture(String url) {
        Picasso
                .with(view)
                .load(url)
                .into(view.getPlantPhoto());
        Log.d("URRRRRRRLLLL", url);
    }

    public boolean isDeviceSupportCamera() {
        if (view.getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    public void uploadFileToServer(){
        DBConnection service = Factory.getApiConnection();

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUri.getPath());

        Log.d("FilePathOOOOOOOO", fileUri.getPath());

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
        Log.d("FileNameOOOOOOOOOOOOO", file.getName());


//        // add another part within the multipart request
//        String descriptionString = "hello, this is description speaking";
//        RequestBody description =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), descriptionString);

        // finally, execute the request
        Log.d("Uploading file","zaraz");
        service.uploadFile(body, view.plant.getIdPlant(), new OnDownloadFinishedListener<UploadResponse>() {
            @Override
            public void onSuccess(UploadResponse arg) {
                view.plant.setImageAdress(arg.getFile_name());
            }

            @Override
            public void onError() {

            }
        });
    }
    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private File getOutputMediaFile(int type) {
        File mediaStorageDir;

            // External sdcard location
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    DBConnection.IMAGE_DIRECTORY_NAME);

        String status = Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED_READ_ONLY));
        Log.d("PPPPPPPP","readonly");
        mediaStorageDir.setReadable(true);
        mediaStorageDir.setWritable(true);



        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            Log.d("AAAAAAAbsolutepath2", mediaStorageDir.getAbsolutePath());
            if (!mediaStorageDir.mkdir()) {
                Log.d("AAAAAAAbsolutepath3", mediaStorageDir.getAbsolutePath());
                Log.d(TAG, "Oops! Failed create "
                        + DBConnection.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        Log.d("AAAAAAAbsolutepath4", view.getFilesDir().getAbsolutePath());
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == PlantActivity.MEDIA_TYPE_IMAGE) {
            Log.d("AAAAAAAbsolutepath5", view.getFilesDir().getAbsolutePath());
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
        else {
            Log.d("AAAAAAAbsolutepath6", view.getFilesDir().getAbsolutePath());
            return null;
        }
        Log.d("mediaStoragePATHHHHHHH6", mediaFile.getAbsolutePath());

        return mediaFile;
    }
}
