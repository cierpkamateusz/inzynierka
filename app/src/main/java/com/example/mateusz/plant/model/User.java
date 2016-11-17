package com.example.mateusz.plant.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mateusz on 2016-11-16.
 */
public class User implements Serializable{
    @SerializedName("idUser")
    Integer idUser;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("apiKey")
    String apiKey;
    @SerializedName("createdAt")
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
