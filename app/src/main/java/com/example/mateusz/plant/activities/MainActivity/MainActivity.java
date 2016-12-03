package com.example.mateusz.plant.activities.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.MyPlantsActivity.MyPlantsActivity;
import com.example.mateusz.plant.activities.RemindActivity.RemindsActivity;
import com.example.mateusz.plant.model.User;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private Button bGoToMyPlantsActivity;
    private User user;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bGoToMyPlantsActivity = (Button) findViewById(R.id.goToMyPlants);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        name = (TextView)findViewById(R.id.nameText);
        user = (User) getIntent().getSerializableExtra("User");
        name.setText("Witaj \n" + user.getName());




    }

    @Override
    public void goToMyPlantsActivity(View view) {
        Intent intent = new Intent(this, MyPlantsActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToMyRemindsActivity(View view) {
        Intent intent = new Intent(this, RemindsActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToAllPlantsActvity(View view) {

    }
    public void click(View view){}
}
