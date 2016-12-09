package com.example.mateusz.plant.activities.PlantActivity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.R;
import com.example.mateusz.plant.model.Message;
import com.example.mateusz.plant.model.Remind;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mateusz on 2016-12-04.
 */
public class RemindsAdapter extends RecyclerView.Adapter<RemindsAdapter.ViewHolder> {
    List<String> remindNames;
    HashMap<String, List<Remind>> remindMap;
    DBConnection conn = Factory.getApiConnection();


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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String remindName = remindNames.get(position);
        holder.plantName.setText(remindName);
        String dates = "";
        for(Remind remind : remindMap.get(remindName)){
            dates = dates + " " + remind.getDate();
        }
        holder.latinName.setText(dates);
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List <Remind> reminds = remindMap.get(remindNames.get(position));
                for(Remind remind : reminds){

                    conn.deleteRemind(remind.getIdRemind(), new OnDownloadFinishedListener<Message>() {
                        @Override
                        public void onSuccess(Message arg) {
                            Log.d("deleteRemind",arg.getMessage());

                        }

                        @Override
                        public void onError(Throwable t) {

                        }
                    });
                }
                remindMap.remove(remindNames.get(position));
                notifyItemRemoved(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return remindMap.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView plantName;
        public TextView latinName;
        public ImageView deleteIcon;
        public ImageView alarmIcon;

        public ViewHolder(final View itemView) {
            super(itemView);
            plantName = (TextView) itemView.findViewById(R.id.nazwaRosliny);
            latinName = (TextView)itemView.findViewById(R.id.nazwaLacinska);
            deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon);
            alarmIcon = (ImageView) itemView.findViewById(R.id.imageAlarm);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
