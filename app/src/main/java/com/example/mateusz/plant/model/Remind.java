package com.example.mateusz.plant.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mateusz on 2016-12-03.
 */
public class Remind {
    @SerializedName("idRemind")
    private int idRemind;
    @SerializedName("idUserPlant")
    private int idUserPlant;
    @SerializedName("imageAdress")
    private String imageAdress;
    @SerializedName("location")
    private String location;
    @SerializedName("plantName")
    private String plantName;
    @SerializedName("latinName")
    private String latinName;
    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private String date;
    @SerializedName("type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdRemind() {
        return idRemind;
    }

    public void setIdRemind(int idRemind) {
        this.idRemind = idRemind;
    }

    public int getIdUserPlant() {
        return idUserPlant;
    }

    public void setIdUserPlant(int idUserPlant) {
        this.idUserPlant = idUserPlant;
    }

    public String getImageAdress() {
        return imageAdress;
    }

    public void setImageAdress(String imageAdress) {
        this.imageAdress = imageAdress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
