package com.example.mateusz.plant.activities.LoginActivity;

import android.view.View;

import com.example.mateusz.plant.model.User;

/**
 * Created by Mateusz on 2016-11-15.
 */
public interface ILoginActivity {
    String getEmail();
    String getPassword();
    void onClickLogin(View view);
    void navigateToMainActivity(User user);
    void onClickRegister(View view);
    void navigateToRegisterActivity();
}
