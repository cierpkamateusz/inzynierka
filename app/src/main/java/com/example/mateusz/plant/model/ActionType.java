package com.example.mateusz.plant.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mateusz on 2016-12-06.
 */
public class ActionType{
    @SerializedName("idAction")
    private int idAction;
    @SerializedName("name")
    private String name;

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
