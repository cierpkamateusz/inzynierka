package com.example.mateusz.plant.activities.AllPlantsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mateusz.plant.ClickListener;
import com.example.mateusz.plant.R;
import com.example.mateusz.plant.RecyclerTouchListener;
import com.example.mateusz.plant.activities.MyPlantsActivity.MyPlantAdapter;
import com.example.mateusz.plant.model.Plant;
import com.example.mateusz.plant.model.Plants;

public class AllPlantsActivity extends AppCompatActivity implements IAllPlantsActivity{
    private RecyclerView plantsRecycler;
    private MyPlantAdapter plantAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private TextView textEmptyView;
    private SearchView searchView;
    AllPlantsPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_plants);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                plantAdapter.filter(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                plantAdapter.filter(newText);
                return true;
            }
        });
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        plantsRecycler = (RecyclerView) findViewById(R.id.all_plants_recycler);
        plantsRecycler.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        plantsRecycler.setLayoutManager(mLayoutManager);
        plantsRecycler.setItemAnimator(new DefaultItemAnimator());
        plantsRecycler.setAdapter(plantAdapter);
        plantsRecycler.addOnItemTouchListener(new RecyclerTouchListener(this, plantsRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                presenter.onClickPlant(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        progressBar = (ProgressBar)findViewById(R.id.all_plants_progressBar);
        textEmptyView = (TextView) findViewById(R.id.textEmptyView);
        presenter = new AllPlantsPresenter(this);
        showProgress();
        presenter.getAllPlants();
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void loadPlants(Plants list) {
        plantAdapter = new MyPlantAdapter(list.getPlants());
        plantsRecycler.setAdapter(plantAdapter);
        hideProgress();

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        textEmptyView.setVisibility(View.INVISIBLE);
        plantsRecycler.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        textEmptyView.setVisibility(View.INVISIBLE);
        plantsRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmptyView() {
        progressBar.setVisibility(View.INVISIBLE);
        textEmptyView.setVisibility(View.VISIBLE);
        plantsRecycler.setVisibility(View.INVISIBLE);

    }

    @Override
    public void goToPlantActivity(Plant plant) {

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
