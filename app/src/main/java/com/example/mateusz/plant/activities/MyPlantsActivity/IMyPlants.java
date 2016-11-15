package com.example.mateusz.plant.activities.MyPlantsActivity;

import com.example.mateusz.plant.model.Plant;
import com.example.mateusz.plant.model.Plants;

/**
 * Created by Mateusz on 2016-09-09.
 */
public interface IMyPlants {

    void showToast(String message);

    void loadPlants(Plants list);

    void showProgress();

    void hideProgress();
    void setEmptyView();

    void goToPlantActivity(Plant plant);
}
