package com.example.mateusz.plant.activities.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.MainActivity.MainActivity;
import com.example.mateusz.plant.activities.RegisterActivity.RegisterActivity;
import com.example.mateusz.plant.model.User;

public class LoginActivity extends AppCompatActivity implements ILoginActivity{
    private LoginPresenter presenter;
    private EditText emailText;
    private EditText passText;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText = (EditText)findViewById(R.id.emailText);
        passText = (EditText)findViewById(R.id.passwordText);
        loginButton = (Button)findViewById(R.id.loginButton);
        presenter = new LoginPresenter(this);
//        presenter.tryLogin();
    }

    @Override
    public String getEmail() {
        return emailText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passText.getText().toString();
    }

    @Override
    public void onClickLogin(View view) {
        presenter.onClickLogin();
    }

    @Override
    public void navigateToMainActivity(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("User",user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClickRegister(View view) {
        presenter.onClickRegister();
    }

    @Override
    public void navigateToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
