package com.example.mateusz.plant.DBconnection;

import com.example.mateusz.plant.model.DataBody;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Mateusz on 2016-09-09.
 */
public interface DBConnectionInt {

    public void getAllPlants(final OnDownloadFinishedListener listener);

    void getAllPersons(final OnDownloadFinishedListener<List<DataBody>> succes);

    void uploadFile( MultipartBody.Part body);
}
