package com.example.mateusz.plant.activities.PlantActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateusz.plant.ClickListener;
import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.R;
import com.example.mateusz.plant.RecyclerTouchListener;
import com.example.mateusz.plant.activities.MyActivity;
import com.example.mateusz.plant.activities.NewRemindActivity.NewRemindActivity;
import com.example.mateusz.plant.model.Message;
import com.example.mateusz.plant.model.Plant;
import com.example.mateusz.plant.model.Remind;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlantActivity extends MyActivity implements PlantInterface {

    private TextView plantName;
    private TextView latinName;
    private TextView description;

    private TextView labelDescription;
    private TextView labelReminds;
    private EditText location;
    private TextView date;
    private RecyclerView remindsRecycler;
    private RecyclerView.Adapter remindsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ImageView getPlantPhoto() {
        return plantPhoto;
    }


    HashMap<String, List<Remind>> remindMap;
    List<String> remindNames;
    private ImageView plantPhoto;
    protected Plant plant;

    private AlertDialog.Builder builder;
    AlertDialog pictureDialog;
    private ImageView imageView;
    private ImageButton cameraButton;
    private ImageButton deleteButton;
    DBConnection conn;
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
        conn = Factory.getApiConnection();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa Thin.ttf");
        Typeface typeBold = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa Bold.ttf");
        labelDescription = (TextView)findViewById(R.id.descriptionLabel);
        labelDescription.setTypeface(type);
        labelReminds = (TextView)findViewById(R.id.remindsLabel);
        labelReminds.setTypeface(type);
        plantName = (TextView)findViewById(R.id.plantName);
        plantName.setTypeface(typeBold);
        latinName = (TextView)findViewById(R.id.latinName);
        latinName.setTypeface(type);
        description = (TextView)findViewById(R.id.description);
        description.setTypeface(type);
        date = (TextView) findViewById(R.id.date);
        location = (EditText)findViewById(R.id.location);

        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    builder.setMessage("Czy chcesz zmienić lokalizację rośliny?").setTitle("Uwaga");
                    builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.locationChanged(plant.getIdUserPlant(),location.getText());
                        }
                    });
                    builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }
            }
        });

        location.setTypeface(type);

        presenter = new PlantPresenter(this);
        Intent intent = getIntent();
        plant = (Plant)intent.getSerializableExtra("Plant");
        location.setText(plant.getLocation());
        plantName.setText(plant.getPlant_name());
        latinName.setText(plant.getLatin_name());
        description.setText(plant.getDescription());
        date.setText(plant.getDate());
        date.setTypeface(type);
        plantPhoto = (ImageView) findViewById(R.id.plantPhoto);
        cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.isDeviceSupportCamera()) captureImage();
            }
        });
        builder = new AlertDialog.Builder(this);
        deleteButton = (ImageButton) findViewById(R.id.deleteButton) ;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Czy chcesz usunąć roślinę?").setTitle("Uwaga");
                builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        conn.deleteUserPlant(plant.getIdUserPlant(), new OnDownloadFinishedListener<Message>() {
                            @Override
                            public void onSuccess(Message arg) {
                                Log.d("deletePlant", arg.getMessage());
                                finish();
                            }

                            @Override
                            public void onError(Throwable t) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        plantPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog(plant.getImageAdress());
                loadPicture2(DBConnection.PHOTO_URL+plant.getImageAdress());
            }
        });

        recyclerInit();
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

    @Override
    public void recyclerInit() {
        remindsRecycler= (RecyclerView) findViewById(R.id.reminds_recycler);
        remindsRecycler.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        remindsRecycler.setLayoutManager(mLayoutManager);
        remindsRecycler.setItemAnimator(new DefaultItemAnimator());
        remindsRecycler.setAdapter(remindsAdapter);
        remindsRecycler.addOnItemTouchListener(new RecyclerTouchListener(this,remindsRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void loadReminds(List<Remind> reminds) {
        remindMap = presenter.fillRemindsList(reminds);
        remindNames = new ArrayList<String>(remindMap.keySet());
        remindsAdapter = new RemindsAdapter(remindNames, remindMap);
        remindsRecycler.setAdapter(remindsAdapter);
        remindsRecycler.setVisibility(View.VISIBLE);

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

//                 successfully captured the image
//                 launching upload activity
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
//                BitmapRotatorTask task = new BitmapRotatorTask(this);
//                task.execute(presenter.fileUri);
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
    protected void onResume() {
        super.onResume();
        presenter.loadPicture(DBConnection.PHOTO_URL+plant.getImageAdress());
        pictureDialogInit();

        presenter.getPlantReminds();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }



    public void initDialog(View view){
        Intent intent = new Intent(this, NewRemindActivity.class);
        intent.putExtra("idUserPlant", plant.getIdUserPlant());
        startActivityForResult(intent, 112);

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
