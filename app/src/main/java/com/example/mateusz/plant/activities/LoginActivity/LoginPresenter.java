package com.example.mateusz.plant.activities.LoginActivity;

import android.util.Log;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.model.User;

/**
 * Created by Mateusz on 2016-11-16.
 */
public class LoginPresenter implements ILoginPresenter {
    LoginActivity view;
    DBConnection conn;
    public LoginPresenter(LoginActivity view) {
        this.view=view;
        this.conn = Factory.getApiConnection();
    }

    @Override
    public void onClickLogin() {
        final String email = view.getEmail();
        final String password = view.getPassword();

        conn.login(email, password, new OnDownloadFinishedListener<User>() {
            @Override
            public void onSuccess(User arg) {
                Log.d("onClickLogin in prese", email);
                view.navigateToMainActivity(arg);

            }

            @Override
            public void onError() {
                Log.d("onClickLogin false", email);
            }
        });
    }

    @Override
    public void onClickRegister() {
        view.navigateToRegisterActivity();
    }
}
