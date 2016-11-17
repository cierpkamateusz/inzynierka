package com.example.mateusz.plant.DBconnection;

import android.util.Log;

import com.example.mateusz.plant.model.Credentials;
import com.example.mateusz.plant.model.DataBody;
import com.example.mateusz.plant.model.Plants;
import com.example.mateusz.plant.model.UploadResponse;
import com.example.mateusz.plant.model.User;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mateusz on 2016-09-03.
 */

public class DBConnection implements DBConnectionInt{
    private String auth = "";
    private Retrofit retrofit;
    private Endpoints api;
//    public static final String BASE_URL = "http://10.0.2.2/android_connect/";   //For emulator
//    public static final String PHOTO_URL = "http://10.0.2.2/flower_pictures/";
    public static final String BASE_URL = "http://192.168.0.103:8081/plant_application/v1/";   //For device
    public static final String PHOTO_URL = "http://192.168.0.103:8081/plant_application/images/";
    // File upload url (replace the ip with your server address)
    public static final String FILE_UPLOAD_URL = "http://192.168.0.103/plant_application/images/";
    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    public DBConnection() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder().header("authorization",auth).method(original.method(),original.body()).build();
                return chain.proceed(request);
            }

        });
        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();

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
                Log.d("asdasd2OOOOOOOOO",String.valueOf(response.code()));


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

    @Override
    public void login(String email, String password, final OnDownloadFinishedListener<User> listener) {
        Credentials credentials = new Credentials(email, password);
        Call<User> call = api.login(email,password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!=null){
                    Log.d("Login resp",response.body().getName());
                }
                listener.onSuccess(response.body());
                auth = response.body().getApiKey();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
               listener.onError();
            }
        });
    }

    @Override
    public void register(String name, String email, String password, final OnDownloadFinishedListener<ResponseBody> listener) {
        Call<ResponseBody> call = api.register(name,email,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==201){
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
