package com.example.mateusz.plant.DBconnection;

import com.example.mateusz.plant.model.DataBody;
import com.example.mateusz.plant.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

/**
 * Created by Mateusz on 2016-09-09.
 */
public interface DBConnectionInt {

    public void getAllPlants(final OnDownloadFinishedListener listener);

    void getAllPersons(final OnDownloadFinishedListener<List<DataBody>> succes);

    void uploadFile( MultipartBody.Part body);
    void login(final String email, final String password, final OnDownloadFinishedListener<User> listener);
    void register(final String name, final String email, final String password, final OnDownloadFinishedListener<ResponseBody> listener);
}
