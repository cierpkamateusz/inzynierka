package com.example.mateusz.plant.activities.LoginActivity;

import android.util.Log;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.DBconnection.OnLoginListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.model.Credentials;
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

        conn.login(email, password, new OnLoginListener<User>() {
            @Override
            public void onWrongCredentials() {
                Log.d("onClickLogin","wrong");
            }

            @Override
            public void onSuccess(User arg) {
                Log.d("onClickLogin in prese", email);
                view.saveCredentials(new Credentials(email,password));
                view.navigateToMainActivity(arg);
            }

            @Override
            public void onError(Throwable t) {
                Log.d("onClickLogin", t.getMessage());
                Log.d("onClickLogin","error");
            }
        });
    }

    @Override
    public void onClickRegister() {
        view.navigateToRegisterActivity();
    }

    @Override
    public void tryLogin() {
//        view.showProgressBar();
        Credentials credentials = view.getCredentials();

        if(credentials == null) {
//            loginView.hideProgressBar();
            return;
        }
        else {
            conn.login(credentials.getEmail(), credentials.getPassword(), new OnLoginListener<User>() {
                @Override
                public void onWrongCredentials() {

                }

                @Override
                public void onSuccess(User arg) {
                    view.navigateToMainActivity(arg);

                }

                @Override
                public void onError(Throwable t) {

                }
            });
        }
    }

}
