package com.example.mateusz.plant.activities.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.MyPlantsActivity.MyPlantsActivity;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private Button bGoToMyPlantsActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bGoToMyPlantsActivity = (Button) findViewById(R.id.goToMyPlants);



    }

    @Override
    public void goToMyPlantsActivity(View view) {
        Intent intent = new Intent(this, MyPlantsActivity.class);
        startActivity(intent);
    }

}
