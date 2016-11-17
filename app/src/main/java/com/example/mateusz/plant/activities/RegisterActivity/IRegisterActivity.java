package com.example.mateusz.plant.activities.RegisterActivity;

import android.view.View;

/**
 * Created by Mateusz on 2016-11-17.
 */
public interface IRegisterActivity {
    String getEmail();
    String getPassword();
    String getName();
    void onClickRegister(View view);
    void navigateToLoginActivity();
}
