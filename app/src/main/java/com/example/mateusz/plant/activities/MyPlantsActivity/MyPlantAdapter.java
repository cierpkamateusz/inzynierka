package com.example.mateusz.plant.activities.MyPlantsActivity;

import android.graphics.Typeface;
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
        holder.location.setText(plant.getLocation());
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
        public TextView location;
        public ImageView healthImg;


        public ViewHolder(View itemView) {

            super(itemView);
            Typeface type = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/Comfortaa Thin.ttf");
            Typeface typeBold = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/Comfortaa Thin.ttf");
            view = itemView;
            plantName = (TextView) itemView.findViewById(R.id.nazwaRosliny);
            plantName.setTypeface(typeBold);
            latinName = (TextView)itemView.findViewById(R.id.nazwaLacinska);
            latinName.setTypeface(type);
            healthImg = (ImageView) itemView.findViewById(R.id.ImageView);
            location = (TextView) itemView.findViewById(R.id.location);
            location.setTypeface(type);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
