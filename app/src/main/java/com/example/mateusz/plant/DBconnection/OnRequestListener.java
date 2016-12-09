package com.example.mateusz.plant.DBconnection;

/**
 * Created by win7 on 26/05/2016.
 */
public interface OnRequestListener<E>  {

    void onSuccess(E arg);
    void onError(Throwable t);
}
