package com.example.mateusz.plant.activities.RegisterActivity;

import android.util.Log;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;

import okhttp3.ResponseBody;

/**
 * Created by Mateusz on 2016-11-17.
 */
public class RegisterPresenter implements IRegisterPresenter {
    RegisterActivity view;
    DBConnection conn;
    public RegisterPresenter(RegisterActivity view) {
        this.view=view;
        this.conn= Factory.getApiConnection();
    }

    @Override
    public void onClickRegister() {
        String name = view.getName();
        String password = view.getPassword();
        String email = view.getEmail();
        conn.register(name, email, password, new OnDownloadFinishedListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody arg) {
                Log.d("register",arg.toString());
                view.navigateToLoginActivity();
            }

            @Override
            public void onError() {

            }
        });

    }
}
