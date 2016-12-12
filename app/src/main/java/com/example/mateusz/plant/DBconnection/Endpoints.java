package com.example.mateusz.plant.DBconnection;

import com.example.mateusz.plant.model.ActionType;
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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


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
    @GET("user_reminds/{id}")
    Call<List<Remind>> getUserPlantReminds(@Path("id") Integer plantId);
    @GET("action")
    Call<List<ActionType>> getActions();
    @Multipart
    @POST("user_reminds")
    Call<Message> addNewReminds(@Part("idUserPlant") int idUserPlant, @Part("idAction") int idAction, @Part("date") String date,@Part("type") String type);
    @DELETE("user_reminds/{id}")
    Call<Message> deleteRemind(@Path("id") Integer idUserPlant);
    @Multipart
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @PUT("user_reminds")
    Call<Message> updateRemind(@Part("date") String date, @Part("idRemind") Integer idRemind);
    @DELETE("user_plants/{id}")
    Call<Message> deleteUserPlant(@Path("id") Integer idUserPlant);
}
