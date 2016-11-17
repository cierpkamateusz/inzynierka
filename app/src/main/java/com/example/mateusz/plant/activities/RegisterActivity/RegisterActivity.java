package com.example.mateusz.plant.activities.RegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.LoginActivity.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity{

    private RegisterPresenter presenter;
    private EditText emailText;
    private EditText passwordText;
    private EditText nameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailText = (EditText)findViewById(R.id.emailText);
        passwordText = (EditText)findViewById(R.id.passwordText);
        nameText = (EditText)findViewById(R.id.nameText);
        presenter = new RegisterPresenter(this);

    }

    @Override
    public String getEmail() {
        return emailText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordText.getText().toString();
    }

    @Override
    public String getName() {
        return nameText.getText().toString();
    }

    @Override
    public void onClickRegister(View view) {
        presenter.onClickRegister();
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
