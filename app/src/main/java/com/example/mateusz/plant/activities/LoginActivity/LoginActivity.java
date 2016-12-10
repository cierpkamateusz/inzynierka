package com.example.mateusz.plant.activities.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.MainActivity.MainActivity;
import com.example.mateusz.plant.activities.RegisterActivity.RegisterActivity;
import com.example.mateusz.plant.model.Credentials;
import com.example.mateusz.plant.model.User;

public class LoginActivity extends AppCompatActivity implements ILoginActivity{
    private LoginPresenter presenter;
    private EditText emailText;
    private EditText passText;
    private Button loginButton;
    private TextView register;
    private TextView loginLabel;
    private TextView question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginLabel = (TextView)findViewById(R.id.loginLabel);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa Thin.ttf");
        Typeface typeBold = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa Bold.ttf");
        loginLabel.setTypeface(type);
        emailText = (EditText)findViewById(R.id.emailText);
        emailText.setTypeface(type);
        passText = (EditText)findViewById(R.id.passwordText);
        passText.setTypeface(type);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setTypeface(typeBold);
        question = (TextView) findViewById(R.id.lRegisterText);
        question.setTypeface(type);
        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        passText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        register = (TextView) findViewById(R.id.lGoToRegister);
        register.setTypeface(typeBold);
        presenter = new LoginPresenter(this);
        presenter.tryLogin();
    }

    @Override
    public String getEmail() {
        Log.d("getEmail", emailText.getText().toString());
        return emailText.getText().toString();
    }

    @Override
    public String getPassword() {
        Log.d("getPass", passText.getText().toString());
        return passText.getText().toString();
    }

    @Override
    public void onClickLogin(View view) {
        Log.d("occlicklogin","clicklogin");
        presenter.onClickLogin();
    }

    @Override
    public void navigateToMainActivity(User user) {
        Log.d("navigate", "naviaget do main");
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



    }
    public void saveCredentials(Credentials credentials){
        SharedPreferences sharedPref = this.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", credentials.getEmail());
        editor.putString("password", credentials.getPassword());
        editor.apply();
    }


    public Credentials getCredentials(){
        SharedPreferences sharedPref = this.getSharedPreferences("CREDENTIALS",Context.MODE_PRIVATE);
        String password = sharedPref.getString("password", "");
        String email = sharedPref.getString("email", "");

        if(password.equals("") || email.equals(""))
            return null;
        else
            return new Credentials(email, password);

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
