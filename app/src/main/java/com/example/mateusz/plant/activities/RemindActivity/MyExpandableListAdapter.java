package com.example.mateusz.plant.activities.RemindActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.R;
import com.example.mateusz.plant.model.Remind;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mateusz on 2016-12-03.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<Remind>> expandableListDetail;
    private Typeface typeface;
    private Typeface typefaceBold;

    public MyExpandableListAdapter(Typeface typeface, Typeface typeBold, Context context, List<String> expandableListTitle, HashMap<String, List<Remind>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.typeface = typeface;
        this.typefaceBold = typeBold;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String date = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_group, null);
        }
        TextView listTitleText = (TextView) convertView.findViewById(R.id.dateText);
        listTitleText.setText(date);
        listTitleText.setTypeface(typefaceBold);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Remind expandedListRemind = (Remind) getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_my_plant, null);
        }
        TextView expandedListPlantName = (TextView) convertView
                .findViewById(R.id.nazwaRosliny);
        expandedListPlantName.setText(expandedListRemind.getName());
        expandedListPlantName.setTypeface(typefaceBold);

        TextView expandedListLatinName = (TextView) convertView
                .findViewById(R.id.nazwaLacinska);
        expandedListLatinName.setText(expandedListRemind.getPlantName());
        expandedListLatinName.setTypeface(typeface);
        TextView expandedListLocation = (TextView) convertView
                .findViewById(R.id.location);
        expandedListLocation.setText(expandedListRemind.getLocation());
        expandedListLocation.setTypeface(typeface);
        ImageView expandedPlantImage = (ImageView) convertView
                .findViewById(R.id.ImageView);
        Picasso
                .with(convertView.getContext())
                .load(DBConnection.PHOTO_URL+ "resized/" + expandedListRemind.getImageAdress())
                .into(expandedPlantImage);
        ImageView expandedDoneImage = (ImageView) convertView.findViewById(R.id.imageDone);
        expandedDoneImage.setVisibility(View.VISIBLE);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
