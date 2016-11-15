package com.example.mateusz.plant.model;

import java.io.Serializable;

/**
 * Created by Mateusz on 2016-03-20.
 */
public class DataBody implements Serializable{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return age;
    }

    public void setId(int id) {
        this.age = id;
    }
}
