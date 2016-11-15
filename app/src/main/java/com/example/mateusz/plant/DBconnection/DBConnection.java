package com.example.mateusz.plant.DBconnection;

import android.util.Log;

import com.example.mateusz.plant.model.DataBody;
import com.example.mateusz.plant.model.Plants;
import com.example.mateusz.plant.model.UploadResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mateusz on 2016-09-03.
 */

public class DBConnection implements DBConnectionInt{
    private String cookie = "";
    private Retrofit retrofit;
    private Endpoints api;
//    public static final String BASE_URL = "http://10.0.2.2/android_connect/";   //For emulator
//    public static final String PHOTO_URL = "http://10.0.2.2/flower_pictures/";
    public static final String BASE_URL = "http://192.168.0.101:8081/android_connect/";   //For device
    public static final String PHOTO_URL = "http://192.168.0.101:8081/flower_pictures/";
    // File upload url (replace the ip with your server address)
    public static final String FILE_UPLOAD_URL = "http://192.168.0.103/AndroidFileUpload/fileUpload.php";
    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    public DBConnection() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        api = retrofit.create(Endpoints.class);
    }




    @Override
    public void getAllPlants(final OnDownloadFinishedListener listener) {
        Call<Plants> call = api.getPlants();
        Log.d("asdasd1","dsdsadsa1");
        call.enqueue(new Callback<Plants>() {
            @Override
            public void onResponse(Call<Plants> call, Response<Plants> response) {
                int statusCode = response.code();
                Log.d("asdasd2","dsdsadsa2");

                Plants plants = response.body();
                listener.onSuccess(plants);

            }

            @Override
            public void onFailure(Call<Plants> call, Throwable t) {
                Log.d("asdasd3","dsdsadsa3");
                listener.onError();
            }
        });
    }

    @Override
    public void getAllPersons(final OnDownloadFinishedListener<List<DataBody>> succes) {
        Call<List<DataBody>> call = api.getPersons();
        call.enqueue(new Callback<List<DataBody>>() {
            @Override
            public void onResponse(Call<List<DataBody>> call, Response<List<DataBody>> response) {
                List<DataBody> persons = response.body();
                succes.onSuccess(persons);
            }

            @Override
            public void onFailure(Call<List<DataBody>> call, Throwable t) {
                succes.onError();
            }
        });
    }

    @Override
    public void uploadFile(MultipartBody.Part body) {
        Call<UploadResponse> call = api.upload(body);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call,
                                   Response<UploadResponse> response) {
                Log.v("Upload", "success");
//                Log.d("Response", response.body().getFile_name());
                Log.d("OOOOOOOOOOOOOOOOOOOOOOO",response.body().getMessage());
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

}
