package com.example.mateusz.plant.DBconnection;

import com.example.mateusz.plant.model.DataBody;
import com.example.mateusz.plant.model.Plants;
import com.example.mateusz.plant.model.UploadResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface Endpoints {

    @GET("get_all_product.php")
    Call<Plants> getPlants();
    @GET("ArrayWithObjects.txt")
    Call<List<DataBody>> getPersons();
    @Multipart
    @POST("upload_file.php")
    Call<UploadResponse> upload(@Part MultipartBody.Part file);
}
