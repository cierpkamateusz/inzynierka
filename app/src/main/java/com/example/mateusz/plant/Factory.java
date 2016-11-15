package com.example.mateusz.plant;

import com.example.mateusz.plant.DBconnection.DBConnection;

/**
 * Created by win7 on 01/04/2016.
 */
public class Factory {

    private static DBConnection apiConnection;

    public static DBConnection getApiConnection(){
        if (apiConnection == null){
            apiConnection = new DBConnection();
        }
        return apiConnection;

    }
}
