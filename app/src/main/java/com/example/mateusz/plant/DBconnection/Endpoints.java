package com.example.mateusz.plant.DBconnection;

import com.example.mateusz.plant.model.DataBody;
import com.example.mateusz.plant.model.Message;
import com.example.mateusz.plant.model.Plants;
import com.example.mateusz.plant.model.Remind;
import com.example.mateusz.plant.model.UploadResponse;
import com.example.mateusz.plant.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface Endpoints {
    @Multipart
    @POST("login")
    Call<User> login(@Part("email") String email, @Part("password") String password);
    @Multipart
    @POST("register")
    Call<ResponseBody> register(@Part("name") String name, @Part("email") String email, @Part("password") String password);
    @GET("user_plants")
    Call<Plants> getUserPlants();
    @Multipart
    @POST("user_plants")
    Call<Message> addUserPlant(@Part("idPlant") int idPlant);
    @GET("plant")
    Call<Plants> getAllPlants();
    @GET("ArrayWithObjects.txt")
    Call<List<DataBody>> getPersons();
    @Multipart
    @POST("image")
    Call<UploadResponse> upload(@Part MultipartBody.Part file, @Part("idUserPlant") int idUserPlant);
    @GET("user_reminds")
    Call<List<Remind>> getUserReminds();
}
