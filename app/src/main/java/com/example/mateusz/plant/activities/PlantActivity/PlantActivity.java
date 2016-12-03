package com.example.mateusz.plant.activities.PlantActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.R;
import com.example.mateusz.plant.model.Plant;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PlantActivity extends AppCompatActivity implements PlantInterface, SensorEventListener {

    private TextView plantName;
    private TextView latinName;
    private TextView description;
    private TextView sensorValue;

    public ImageView getPlantPhoto() {
        return plantPhoto;
    }

    private SensorManager sensorManager;
    private Sensor lightSensor;

    private ImageView plantPhoto;
    protected Plant plant;
    AlertDialog pictureDialog;
    private ImageView imageView;
    private ImageButton cameraButton;
    // Activity request codes
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private PlantPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        plantName = (TextView)findViewById(R.id.plantName);
        latinName = (TextView)findViewById(R.id.latinName);
        description = (TextView)findViewById(R.id.description);
        sensorValue = (TextView)findViewById(R.id.sensorValue);
        presenter = new PlantPresenter(this);
        Intent intent = getIntent();
        plant = (Plant)intent.getSerializableExtra("Plant");
        plantName.setText(plant.getPlant_name());
        latinName.setText(plant.getLatin_name());
        description.setText(plant.getDescription());
        plantPhoto = (ImageView) findViewById(R.id.plantPhoto);
        cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.isDeviceSupportCamera()) captureImage();
            }
        });


        plantPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog(plant.getImageAdress());
                loadPicture2(DBConnection.PHOTO_URL+plant.getImageAdress());
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }


    @Override
    public void pictureDialogInit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                pictureDialog.dismiss();
            }
        });

        pictureDialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_picture,null);
        pictureDialog.setView(dialogLayout);
    }

    public void showPictureDialog(String plantName) {
        pictureDialog.setTitle(plantName);
        pictureDialog.show();
    }



    public void loadPicture2(String url){
        imageView = (ImageView) pictureDialog.findViewById(R.id.flowerPicture);
        if(pictureDialog.isShowing()) {
            Picasso
                    .with(this)
                    .load(url)
                    .into(imageView);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", presenter.fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        presenter.fileUri = savedInstanceState.getParcelable("file_uri");
    }



    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
//                launchUploadActivity(true);
                try {
                    Bitmap bitmap = presenter.handleSamplingAndRotationBitmap(getApplicationContext(),presenter.fileUri);
                    File file = new File(presenter.fileUri.getPath());
                    if(!file.exists()){
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else{
                        file.delete();
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                presenter.uploadFileToServer();

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // video successfully recorded
                // launching upload activity
                launchUploadActivity(false);

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void captureImage() {

        int permissionRead = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionRead != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        presenter.fileUri = presenter.getOutputMediaFileUri(PlantActivity.MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, presenter.fileUri);

        // start the image capture Intent
        startActivityForResult(intent,PlantActivity.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    private void launchUploadActivity(boolean isImage){
        Intent i = new Intent(PlantActivity.this, UploadActivity.class);
        i.putExtra("filePath", presenter.fileUri.getPath());
        i.putExtra("isImage", isImage);
        startActivity(i);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float brightnessLevel = event.values[0];
        sensorValue.setText(String.valueOf(brightnessLevel));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadPicture(DBConnection.PHOTO_URL+plant.getImageAdress());
        pictureDialogInit();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
