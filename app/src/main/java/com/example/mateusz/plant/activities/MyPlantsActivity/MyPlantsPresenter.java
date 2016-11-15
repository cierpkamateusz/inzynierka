package com.example.mateusz.plant.activities.MyPlantsActivity;

import com.example.mateusz.plant.DBconnection.DBConnectionInt;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.model.DataBody;
import com.example.mateusz.plant.model.Plant;
import com.example.mateusz.plant.model.Plants;

import java.util.List;

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
    public void getAllPlants() {
        dbConnectionInt.getAllPlants(new OnDownloadFinishedListener<Plants>() {
            @Override
            public void onSuccess(Plants list) {
                plants = list;
                if(plants.getPlants().isEmpty())
                    view.setEmptyView();
                else
                    view.loadPlants(list);
            }

            @Override
            public void onError() {
                view.showToast("Blad - nie pobrało roslin");
            }
        });
    }

    @Override
    public void getAllPersons() {
        dbConnectionInt.getAllPersons(new OnDownloadFinishedListener<List<DataBody>>() {
            @Override
            public void onSuccess(List<DataBody> list) {
                List<DataBody> personsResult = list;
                view.showToast(personsResult.get(1).getName());
            }

            @Override
            public void onError() {
                view.showToast("Blad - nie pobrało persons");
            }
        });
    }

    @Override
    public void onClickPlant(int position) {
        Plant plant = plants.getPlants().get(position);
        view.goToPlantActivity(plant);
    }

}
