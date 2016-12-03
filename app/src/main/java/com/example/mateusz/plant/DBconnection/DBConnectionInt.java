package com.example.mateusz.plant.DBconnection;

import com.example.mateusz.plant.model.DataBody;
import com.example.mateusz.plant.model.Message;
import com.example.mateusz.plant.model.Remind;
import com.example.mateusz.plant.model.UploadResponse;
import com.example.mateusz.plant.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

/**
 * Created by Mateusz on 2016-09-09.
 */
public interface DBConnectionInt {

    void getAllPlants(final OnDownloadFinishedListener listener);
    void getUserPlants(final OnDownloadFinishedListener listener);
    void addUserPlant(int idPlant, final OnDownloadFinishedListener <Message> listener);
    void getAllPersons(final OnDownloadFinishedListener<List<DataBody>> succes);

    void uploadFile( MultipartBody.Part body, int idPlant, final OnDownloadFinishedListener<UploadResponse> listener);
    void login(final String email, final String password, final OnLoginListener<User> listener);
    void register(final String name, final String email, final String password, final OnDownloadFinishedListener<ResponseBody> listener);

    void getReminds(OnDownloadFinishedListener<List<Remind>> onDownloadFinishedListener);
}
