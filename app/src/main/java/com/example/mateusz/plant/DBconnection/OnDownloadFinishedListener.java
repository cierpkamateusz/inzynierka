package com.example.mateusz.plant.DBconnection;

/**
 * Created by win7 on 03/04/2016.
 */
public interface OnDownloadFinishedListener<E> {

    void onSuccess(E arg);
    void onError(Throwable t);

}
