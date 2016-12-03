package com.example.mateusz.plant.DBconnection;

/**
 * Created by win7 on 22/04/2016.
 */
public interface OnLoginListener<E>  extends OnRequestListener<E>{

    void onWrongCredentials();

}
