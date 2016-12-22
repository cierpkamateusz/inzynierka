package com.example.mateusz.plant.activities.RemindActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.MyActivity;
import com.example.mateusz.plant.model.Remind;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RemindsActivity extends MyActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<Remind>> expandableListDetail;
    RemindsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminds);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(myToolbar);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView list, View v, int groupPosition, long id) {
                return  list.isGroupExpanded(groupPosition) ? list.collapseGroup(groupPosition) : list.expandGroup(groupPosition);
            }
        });
        presenter = new RemindsPresenter(this);
        Log.d("onCreate","success");
        presenter.getReminds();



    }

    public void loadReminds(List<Remind> arg) {

        Log.d("Remind", String.valueOf(arg.get(0).getName()));
        expandableListDetail = presenter.fillExpandableList(arg);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa Thin.ttf");
        Typeface typeBold = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa Bold.ttf");
        expandableListAdapter = new MyExpandableListAdapter(type,typeBold,this, expandableListTitle, expandableListDetail,arg);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0);

    }

}
