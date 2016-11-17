package com.example.mateusz.plant.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 2016-09-09.
 */
public class Plants implements Serializable {
    @SerializedName("plants")
    private List<Plant> Plants = new ArrayList<Plant>();

    public List<Plant> getPlants() {

        return Plants;
    }

    public void setPlants(List<Plant> plants) {
        Plants = plants;
    }
}
