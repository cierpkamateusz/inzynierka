package com.example.mateusz.plant.activities.NewRemindActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.mateusz.plant.DBconnection.DBConnection;
import com.example.mateusz.plant.DBconnection.OnDownloadFinishedListener;
import com.example.mateusz.plant.Factory;
import com.example.mateusz.plant.R;
import com.example.mateusz.plant.activities.MyActivity;
import com.example.mateusz.plant.model.ActionType;
import com.example.mateusz.plant.model.Message;
import com.example.mateusz.plant.model.NewRemind;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewRemindActivity extends MyActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private DBConnection conn;
    private static List<ActionType> actions = null;
    private static int idUserPlant;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_remind);
        Intent intent = getIntent();
        idUserPlant = intent.getIntExtra("idUserPlant",1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);
        conn = Factory.getApiConnection();
        Log.d("onCreateView", "onCreate");
        getActions();





    }
    private void getActions() {
        Log.d("getactions", "SSSSSSSSSS");
        conn.getActions(new OnDownloadFinishedListener<List<ActionType>>() {
            @Override
            public void onSuccess(List<ActionType> arg) {
                Log.d("onSuccess", "SSSSSSSSSS");

                actions = arg;
                Log.d("actions[0]", actions.get(0).getName());
                // Create the adapter that will return a fragment for each of the three
                // primary sections of the activity.
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager.setAdapter(mSectionsPagerAdapter);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(mViewPager);
            }

            @Override
            public void onError() {
                Log.d("onSuccess", "EEEEEEEEEEE");
            }
        });

    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private DBConnection conn;
        private static final String ARG_SECTION_NUMBER = "section_number";
        List<NewRemind> newReminds = new ArrayList<NewRemind>();
        //Frequently remind
        private NumberPicker numberPicker;
        private Button addFreq;
        private Spinner spinner;
        //Daily
        private LinearLayout linearLayout;
        private CheckBox cb1;
        private CheckBox cb2;
        private CheckBox cb3;
        private CheckBox cb4;
        private CheckBox cb5;
        private CheckBox cb6;
        private CheckBox cb7;
        private Button addDaily;
        //Single
        private String date = "";
        private CalendarView calendarView;
        private Button addSingle;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                final boolean booleanArray[] = {false,false,false,false,false,false,false};
                View rootView = inflater.inflate(R.layout.fragment_daily_rem, container, false);
                spinner = (Spinner) rootView.findViewById(R.id.spinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
                for(int i=0; i<actions.size(); i++){
                    Log.d("action",actions.get(i).getName());
                    adapter.add(actions.get(i).getName());
                }

                spinner.setAdapter(adapter);
                cb1 = (CheckBox)rootView.findViewById(R.id.checkBox);
                cb2 = (CheckBox)rootView.findViewById(R.id.checkBox2);
                cb3 = (CheckBox)rootView.findViewById(R.id.checkBox3);
                cb4 = (CheckBox)rootView.findViewById(R.id.checkBox4);
                cb5 = (CheckBox)rootView.findViewById(R.id.checkBox5);
                cb6 = (CheckBox)rootView.findViewById(R.id.checkBox6);
                cb7 = (CheckBox)rootView.findViewById(R.id.checkBox7);

                addDaily = (Button)rootView.findViewById(R.id.button);
                addDaily.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cb1.isChecked())booleanArray[0]=true;
                        else booleanArray[0]=false;
                        if(cb2.isChecked())booleanArray[1]=true;
                        else booleanArray[1]=false;
                        if(cb3.isChecked())booleanArray[2]=true;
                        else booleanArray[2]=false;
                        if(cb4.isChecked())booleanArray[3]=true;
                        else booleanArray[3]=false;
                        if(cb5.isChecked())booleanArray[4]=true;
                        else booleanArray[4]=false;
                        if(cb6.isChecked())booleanArray[5]=true;
                        else booleanArray[5]=false;
                        if(cb7.isChecked())booleanArray[6]=true;
                        else booleanArray[6]=false;
                        if(booleanArray[6])Log.d("addDaily","asd");

                        {
                            for(int i=0; i<booleanArray.length; i++){
                                if(booleanArray[i]){
                                    Log.d("array[i]",String.valueOf(i));
                                    String date = "";
                                    Calendar current = Calendar.getInstance();
                                    int currentDayOfWeek = current.get(Calendar.DAY_OF_WEEK);
                                    switch(i){
                                        case 0: date = getDateOfDayOfWeek(current, currentDayOfWeek,Calendar.MONDAY); break;
                                        case 1: date = getDateOfDayOfWeek(current, currentDayOfWeek,Calendar.TUESDAY); break;
                                        case 2: date = getDateOfDayOfWeek(current, currentDayOfWeek,Calendar.WEDNESDAY); break;
                                        case 3: date = getDateOfDayOfWeek(current, currentDayOfWeek,Calendar.THURSDAY); break;
                                        case 4: date = getDateOfDayOfWeek(current, currentDayOfWeek,Calendar.FRIDAY); break;
                                        case 5: date = getDateOfDayOfWeek(current, currentDayOfWeek,Calendar.SATURDAY); break;
                                        case 6: date = getDateOfDayOfWeek(current, currentDayOfWeek,Calendar.SUNDAY); break;
                                        default: break;
                                    }


                                    NewRemind newRemind = new NewRemind();
                                    newRemind.setDate(date);
                                    newRemind.setIdAction(spinner.getSelectedItemPosition()+1);
                                    newRemind.setType("d");
                                    newReminds.add(newRemind);



                                }
                            }
                        }
                        addNewReminds();
                    }

                    private String getDateOfDayOfWeek(Calendar current, int calendar, int i) {
                        Calendar curr = Calendar.getInstance();
                        while(curr.get(Calendar.DAY_OF_WEEK)!=i)
                            curr.add(Calendar.DATE,1);
                        return makeDate(current.get(Calendar.YEAR),curr.get(Calendar.MONTH)+1,curr.get(Calendar.DAY_OF_MONTH));
                    }


                });


                return rootView;
            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
                View rootView = inflater.inflate(R.layout.fragment_freq_rem, container, false);
                spinner = (Spinner) rootView.findViewById(R.id.spinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
                for(int i=0; i<actions.size(); i++){
                    adapter.add(actions.get(i).getName());
                }
                spinner.setAdapter(adapter);
                numberPicker = (NumberPicker)rootView.findViewById(R.id.numberPicker);
                numberPicker.setMaxValue(31);
                numberPicker.setMinValue(1);
                addFreq = (Button)rootView.findViewById(R.id.button);
                addFreq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("addFreq", String.valueOf(numberPicker.getValue()));
                        Calendar calendar = Calendar.getInstance();

                        calendar.add(Calendar.DAY_OF_MONTH,numberPicker.getValue());
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH)+1;
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        date = makeDate(year, month,dayOfMonth);
                        Log.d("calendar", date);
                        NewRemind newRemind = new NewRemind();
                        newRemind.setDate(date);
                        newRemind.setIdAction(spinner.getSelectedItemPosition()+1);
                        newRemind.setType(String.valueOf(numberPicker.getValue()));
                        newReminds.add(newRemind);
                        addNewReminds();
                    }
                });
                return rootView;

            }
            else{
                View rootView = inflater.inflate(R.layout.fragment_single_rem, container, false);
                spinner = (Spinner) rootView.findViewById(R.id.spinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
                for(int i=0; i<actions.size(); i++){
                    adapter.add(actions.get(i).getName());
                }
                spinner.setAdapter(adapter);
                calendarView = (CalendarView)rootView.findViewById(R.id.calendarView);
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        month+=1;
                        date = makeDate(year, month, dayOfMonth);
                    }


                });
                addSingle = (Button)rootView.findViewById(R.id.button);
                addSingle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewRemind newRemind = new NewRemind();
                        newRemind.setDate(date);
                        newRemind.setIdAction(spinner.getSelectedItemPosition()+1);
                        newRemind.setType("s");
                        newReminds.add(newRemind);
                        Log.d("Single", date);
                        addNewReminds();
                    }
                });
                return rootView;
            }

        }

        private void addNewReminds() {
            conn = Factory.getApiConnection();
            for(NewRemind remind: newReminds){
                conn.addNewReminds(idUserPlant, remind, new OnDownloadFinishedListener<Message>() {
                    @Override
                    public void onSuccess(Message arg) {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }


        }

        private String makeDate(int year, int month, int dayOfMonth) {
            date = ""+year;
            if(month<10){
                date+="-"+"0"+month;
            }
            else
                date+="-"+month;
            if(dayOfMonth<10){
                date+="-"+"0"+dayOfMonth;
            }
            else
                date+="-"+dayOfMonth;
            return date;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DZIENNE";
                case 1:
                    return "OKRESOWE";
                case 2:
                    return "JEDNORAZOWE";
            }
            return null;
        }
    }
}
