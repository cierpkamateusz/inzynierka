package com.example.mateusz.plant.activities.PlantActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.activities.MainActivity.MainActivity;
import com.example.mateusz.plant.model.Remind;
import com.example.mateusz.plant.model.UploadResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    List<Remind> reminds = null;


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

    @Override
    public void getPlantReminds() {
        conn.getUserPlantReminds(view.plant.getIdUserPlant(), new OnDownloadFinishedListener<List<Remind>>() {
            @Override
            public void onSuccess(List<Remind> arg) {
                Log.d("getPlantReminds", "success");
                if(!arg.isEmpty()){
                    Log.d("PlantName from remind",arg.get(0).getName());

                    view.loadReminds(arg);
                }

            }

            @Override
            public void onError(Throwable t) {
                Log.d("getPlantReminds", "error");
            }
        });

    }
    public HashMap<String,List<Remind>> fillRemindsList(List<Remind> arg) {
        HashMap<String,List<Remind>> map = new HashMap<String,List<Remind>>();
        for(Remind remind : arg){
            String name = String.valueOf(remind.getName());
            if(!map.containsKey(name)){
                map.put(name,new ArrayList<Remind>());
                map.get(name).add(remind);
            }
            else{
                map.get(name).add(remind);
            }
        }
        return map;
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
                RequestBody.create(MediaType.parse("image/jpeg"), file);

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
        service.uploadFile(body, view.plant.getIdUserPlant(), new OnDownloadFinishedListener<UploadResponse>() {
            @Override
            public void onSuccess(UploadResponse arg) {
                view.plant.setImageAdress(arg.getFile_name());
                loadPicture(DBConnection.PHOTO_URL+view.plant.getImageAdress());
                view.pictureDialogInit();

            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 2048;
        int MAX_WIDTH = 2048;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(img, selectedImage);
        return img;
    }
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }
    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
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
