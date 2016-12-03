package com.example.mateusz.plant.activities.AllPlantsActivity;

import com.example.mateusz.plant.model.Plant;
import com.example.mateusz.plant.model.Plants;

/**
 * Created by Mateusz on 2016-11-19.
 */
public interface IAllPlantsActivity {
    void showToast(String message);

    void loadPlants(Plants list);

    void showProgress();

    void hideProgress();
    void setEmptyView();

    void goToPlantActivity(Plant plant);

}
