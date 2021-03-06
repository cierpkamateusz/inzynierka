package com.example.mateusz.plant.activities.RegisterActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.LoginActivity.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity{

    private RegisterPresenter presenter;
    private EditText emailText;
    private EditText passwordText;
    private EditText nameText;
    private TextView registerLabel;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerLabel = (TextView)findViewById(R.id.registerLabel);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa Thin.ttf");
        registerLabel.setTypeface(type);
        button = (Button) findViewById(R.id.RegisterButton);
        button.setTypeface(type);
        emailText = (EditText)findViewById(R.id.emailText);
        emailText.setTypeface(type);
        passwordText = (EditText)findViewById(R.id.passwordText);
        passwordText.setTypeface(type);
        nameText = (EditText)findViewById(R.id.nameText);
        nameText.setTypeface(type);
        nameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
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
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
