package com.example.mateusz.plant;

import android.view.View;

/**
 * Created by win7 on 26/03/2016.
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
