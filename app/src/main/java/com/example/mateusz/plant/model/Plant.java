package com.example.mateusz.plant.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mateusz on 2016-09-05.
 */
public class Plant implements Serializable {

    @SerializedName("idPlant")
    private int idPlant;
    @SerializedName("name")
    private String Plant_name;
    @SerializedName("latinName")
    private String Latin_name;
    @SerializedName("description")
    private String Description;
    @SerializedName("imageAdress")
    private String imageAdress;
    @SerializedName("location")
    private String location;
//    @SerializedName("Date")
//    private String Date;
//    @SerializedName("Photo_url")
//    private String photo_url;
//    @SerializedName("Health")
//    private Integer health;

//    public Integer getHealth() {
//        return health;
//    }
//
//    public void setHealth(Integer health) {
//        this.health = health;
//    }
//
//    public String getDate() {
//        return Date;
//    }
//
//    public void setDate(String date) {
//        this.Date = date;
//    }

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

    public int getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(int idPlant) {
        this.idPlant = idPlant;
    }

    public String getPlant_name() {
        return Plant_name;
    }

    public void setPlant_name(String plant_name) {
        this.Plant_name = plant_name;
    }

    public String getLatin_name() {
        return Latin_name;
    }

    public void setLatin_name(String latin_name) {
        this.Latin_name = latin_name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

//    public String getPhoto_url() {
//        return photo_url;
//    }
//
//    public void setPhoto_url(String photo_url) {
//        this.photo_url = photo_url;
//    }
}
