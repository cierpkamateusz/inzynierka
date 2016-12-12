package com.example.mateusz.plant.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.LoginActivity.LoginActivity;


public abstract class MyActivity extends AppCompatActivity {

    public AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Sprawdz swoje połączenie z internetem i spróbuj ponownie")
                .setTitle("Błąd połączenia");

        builder.setNegativeButton("Sprobuj ponownie", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                refresh();
            }
        });

        builder.setPositiveButton("Zamknij", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                moveTaskToBack(true);
            }
        });

        alertDialog = builder.create();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.logout:

                signOut();
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    private void signOut(){

        //TODO finish session on server
        DBConnection apiConnection = Factory.getApiConnection();
        apiConnection.closeSession();
        navigateToLoginActivity();
        removeAllCredentials();


    }


    public void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToLoginActivity(){

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void removeAllCredentials(){
        SharedPreferences settings = this.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }

    public void refresh() {

        //TODO think about refreshing it in child class
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void showAlertDialog() {
        alertDialog.show();
    }

}