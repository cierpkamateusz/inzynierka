package com.example.mateusz.plant.activities.RemindActivity;

import android.util.Log;

import com.example.mateusz.plant.DBconnection.DBConnectionInt;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.model.Remind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mateusz on 2016-12-03.
 */
public class RemindsPresenter {
    RemindsActivity view;
    private DBConnectionInt dbConnectionInt;
    public RemindsPresenter(RemindsActivity remindsActivity) {
        this.view = remindsActivity;
        dbConnectionInt = Factory.getApiConnection();
    }

    public void getReminds() {
        Log.d("getReminds","success");
        dbConnectionInt.getReminds(new OnDownloadFinishedListener<List<Remind>>() {
            @Override
            public void onSuccess(List<Remind> arg) {
                Log.d("onSuccess","success");
                if(arg.size()>0) {
                    view.loadReminds(arg);
                }
            }

            @Override
            public void onError() {
                Log.d("onSuccess","error");

            }
        });
    }

    public HashMap<String,List<Remind>> fillExpandableList(List<Remind> arg) {
        HashMap<String,List<Remind>> map = new HashMap<String,List<Remind>>();
        for(Remind remind : arg){
            String date = String.valueOf(remind.getDate());
            if(!map.containsKey(date)){
                map.put(date,new ArrayList<Remind>());
                map.get(date).add(remind);
            }
            else{
                map.get(date).add(remind);
            }
        }
        return map;
    }
}
