package com.example.mateusz.plant.activities.PlantActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mateusz.plant.R;
import com.example.mateusz.plant.model.Remind;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mateusz on 2016-12-04.
 */
public class RemindsAdapter extends RecyclerView.Adapter<RemindsAdapter.ViewHolder> {
    List<String> remindNames;
    HashMap<String, List<Remind>> remindMap;


    public RemindsAdapter(List<String> remindNames, HashMap<String, List<Remind>> remindMap) {
        this.remindMap = remindMap;
        this.remindNames = remindNames;
    }

    @Override
    public RemindsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_remind, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String remindName = remindNames.get(position);
        holder.plantName.setText(remindName);
        String dates = "";
        for(Remind remind : remindMap.get(remindName)){
            dates = dates + " " + remind.getDate();
        }
        holder.latinName.setText(dates);

    }

    @Override
    public int getItemCount() {
        return remindMap.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView plantName;
        public TextView latinName;
        public ImageView healthImg;

        public ViewHolder(View itemView) {
            super(itemView);
            plantName = (TextView) itemView.findViewById(R.id.nazwaRosliny);
            latinName = (TextView)itemView.findViewById(R.id.nazwaLacinska);
            healthImg = (ImageView) itemView.findViewById(R.id.healthImageView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
