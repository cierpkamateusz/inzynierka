package com.example.mateusz.plant.activities.MyPlantsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateusz.plant.ClickListener;
import com.example.mateusz.plant.R;
import com.example.mateusz.plant.RecyclerTouchListener;
import com.example.mateusz.plant.activities.AllPlantsActivity.AllPlantsActivity;
import com.example.mateusz.plant.activities.MyActivity;
import com.example.mateusz.plant.activities.PlantActivity.PlantActivity;
import com.example.mateusz.plant.model.Plant;
import com.example.mateusz.plant.model.Plants;


public class MyPlantsActivity extends MyActivity implements IMyPlants {

    private RecyclerView myPlantsRecycler;
    private RecyclerView.Adapter myPlantAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyPlantsPresenter presenter;
    private ProgressBar progressBar;
    private TextView textEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(myToolbar);

        myPlantsRecycler = (RecyclerView) findViewById(R.id.my_plants_recycler);
        myPlantsRecycler.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        myPlantsRecycler.setLayoutManager(mLayoutManager);
        myPlantsRecycler.setItemAnimator(new DefaultItemAnimator());
        myPlantsRecycler.setAdapter(myPlantAdapter);
        myPlantsRecycler.addOnItemTouchListener(new RecyclerTouchListener(this, myPlantsRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                presenter.onClickPlant(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        progressBar = (ProgressBar)findViewById(R.id.my_plants_progressBar);
        textEmptyView = (TextView) findViewById(R.id.textEmptyView);
        presenter = new MyPlantsPresenter(this);
        showProgress();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getUserPlants();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadPlants(Plants list) {
        myPlantAdapter = new MyPlantAdapter(list.getPlants());
        myPlantsRecycler.setAdapter(myPlantAdapter);
        hideProgress();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        textEmptyView.setVisibility(View.INVISIBLE);
        myPlantsRecycler.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        textEmptyView.setVisibility(View.INVISIBLE);
        myPlantsRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmptyView() {
        progressBar.setVisibility(View.INVISIBLE);
        textEmptyView.setVisibility(View.VISIBLE);
        myPlantsRecycler.setVisibility(View.INVISIBLE);

    }

    @Override
    public void goToPlantActivity(Plant plant) {
        Intent intent = new Intent(this, PlantActivity.class);
        intent.putExtra("Plant",plant);
        startActivity(intent);
    }

    @Override
    public void onClickAddPlant(View view) {
        Intent intent = new Intent(this, AllPlantsActivity.class);
        startActivity(intent);
    }


}
