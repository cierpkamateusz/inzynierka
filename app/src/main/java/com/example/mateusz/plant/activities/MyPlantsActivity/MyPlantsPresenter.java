package com.example.mateusz.plant.activities.MyPlantsActivity;

import com.example.mateusz.plant.DBconnection.DBConnectionInt;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.model.Plant;
import com.example.mateusz.plant.model.Plants;

/**
 * Created by Mateusz on 2016-09-09.
 */
public class MyPlantsPresenter implements MyPlantsPresenterInt{

    private IMyPlants view;
    private DBConnectionInt dbConnectionInt;
    private Plants plants = null;


    public MyPlantsPresenter(IMyPlants view) {
        this.view = view;
        this.dbConnectionInt = Factory.getApiConnection();
    }

    @Override
    public void getUserPlants() {
        dbConnectionInt.getUserPlants(new OnDownloadFinishedListener<Plants>() {
            @Override
            public void onSuccess(Plants list) {
                plants = list;
                if(plants.getPlants().isEmpty())
                    view.setEmptyView();
                else
                    view.loadPlants(list);
            }

            @Override
            public void onError(Throwable t) {
                view.showToast("Blad - nie pobrało roslin");
            }
        });
    }



    @Override
    public void onClickPlant(int position) {
        Plant plant = plants.getPlants().get(position);
        view.goToPlantActivity(plant);
    }

}
