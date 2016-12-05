package com.example.mateusz.plant.activities.PlantActivity;

import com.example.mateusz.plant.model.Remind;

import java.util.List;

/**
 * Created by Mateusz on 2016-09-13.
 */
public interface PlantInterface {
    void pictureDialogInit();
    void recyclerInit();
    void loadReminds(List<Remind> reminds);
}
