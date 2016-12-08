package com.example.mateusz.plant.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mateusz on 2016-12-06.
 */
public class NewRemind {

    @SerializedName("idUserPlant")
    private int idUserPlant;
    @SerializedName("idAction")
    private int idAction;
    @SerializedName("date")
    private String date;
    @SerializedName("type")
    private String type;

    public int getIdUserPlant() {
        return idUserPlant;
    }

    public void setIdUserPlant(int idUserPlant) {
        this.idUserPlant = idUserPlant;
    }

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
