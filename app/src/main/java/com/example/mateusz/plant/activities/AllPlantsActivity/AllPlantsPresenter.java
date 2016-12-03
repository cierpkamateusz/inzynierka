package com.example.mateusz.plant.activities.AllPlantsActivity;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.model.Message;
import com.example.mateusz.plant.model.Plant;
import com.example.mateusz.plant.model.Plants;

/**
 * Created by Mateusz on 2016-11-19.
 */
public class AllPlantsPresenter implements IAllPlantsPresenter {

    private DBConnection conn;
    private AllPlantsActivity view;
    private Plants plants;

    public AllPlantsPresenter(AllPlantsActivity view) {
        this.view = view;
        this.conn = Factory.getApiConnection();
    }

    @Override
    public void getAllPlants() {
        conn.getAllPlants(new OnDownloadFinishedListener<Plants>() {
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
    public void onClickPlant(int position) {
        Plant plant = plants.getPlants().get(position);
        addUserPlant(plant.getIdPlant());

    }

    private void addUserPlant(int idPlant) {
        conn.addUserPlant(idPlant, new OnDownloadFinishedListener<Message>() {
            @Override
            public void onSuccess(Message arg) {

            }

            @Override
            public void onError() {

            }
        });
    }
}
