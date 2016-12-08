package com.example.mateusz.plant.activities.MyPlantsActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.R;

import com.example.mateusz.plant.model.Plant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mateusz on 2016-09-09.
 */
public class MyPlantAdapter extends RecyclerView.Adapter<MyPlantAdapter.ViewHolder> {

    private List<Plant> myPlants;
    public MyPlantAdapter(List<Plant> plants) {
        this.myPlants = plants;
    }

    @Override
    public MyPlantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_my_plant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPlantAdapter.ViewHolder holder, int position) {
        Plant plant = myPlants.get(position);
        holder.plantName.setText(plant.getPlant_name());
        holder.latinName.setText(plant.getLatin_name());
//        BitmapDownloaderTask task = new BitmapDownloaderTask(holder.healthImg);
//        task.execute(plant.getImageAdress());
        Picasso
                .with(holder.view.getContext())
                .load(DBConnection.PHOTO_URL+ "resized/" + plant.getImageAdress())
                .into(holder.healthImg);
//        switch (plant.getHealth())
//        {
//            case 1:holder.healthImg.setImageResource(R.drawable.ic_sentiment_satisfied_green_500_24dp);break;
//            case 2:holder.healthImg.setImageResource(R.drawable.ic_sentiment_neutral_yellow_500_24dp);break;
//            case 3:holder.healthImg.setImageResource(R.drawable.ic_sentiment_dissatisfied_red_500_24dp);break;
//            default: holder.healthImg.setImageResource(R.drawable.ic_sentiment_satisfied_green_500_24dp);break;
//        }



    }


    @Override
    public int getItemCount() {
        return myPlants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View view;
        public TextView plantName;
        public TextView latinName;
        public ImageView healthImg;

        public ViewHolder(View itemView) {

            super(itemView);
            view = itemView;
            plantName = (TextView) itemView.findViewById(R.id.nazwaRosliny);
            latinName = (TextView)itemView.findViewById(R.id.nazwaLacinska);
            healthImg = (ImageView) itemView.findViewById(R.id.ImageView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
