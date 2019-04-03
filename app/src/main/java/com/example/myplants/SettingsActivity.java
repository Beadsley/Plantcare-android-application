package com.example.myplants;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class SettingsActivity extends OptionsMenuActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG= "PlantSettings";
    Switch notificationsSwitch;
    Switch clearAllSwitch;
    Switch favouritePlantSwitch;
    ListView listview;
    Spinner favouriteSpinner;
    String [] plantNames;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        Log.v(TAG,"onCreate initialised");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        clearAllSwitch= findViewById(R.id.switch_clear_favourites);
        notificationsSwitch= findViewById(R.id.switch_notifications);
        favouritePlantSwitch=findViewById(R.id.switch_favourite_plant);
        listview=findViewById(R.id.listview_favourites);
        favouriteSpinner= findViewById(R.id.spinner_favourite);

        // create a listview of plant names
        plantNames =getResources().getStringArray(R.array.plants);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,plantNames);
        listview.setAdapter(adapter);


        //retreive index of favourite plant set by user, default 0
        SharedPreferences favsettings = getSharedPreferences("favourite_plant", 0);
        int favNumber=favsettings.getInt("favourite_plant", 0);
        Log.v(TAG, "favourite number on create: "+favNumber);

        // finding the state of the switch bars
        SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
        boolean notification = settings.getBoolean("notification_switchkey", false);
        boolean clearAll = settings.getBoolean("clearAll_switchkey", false);
        final boolean favourite = settings.getBoolean("favourite_switchkey", false);

        notificationsSwitch.setChecked(notification);
        clearAllSwitch.setChecked(clearAll);
        favouritePlantSwitch.setChecked(favourite);
        if(favouritePlantSwitch.isChecked()){
            Log.v(TAG, "favourite switch was initialised");
            setFavSpinner(favNumber);
        }
        else
        {
            Log.v(TAG, "favourite switch was NOT initialised");
        }


        // when the reminder switch is intilialised
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked){
                    //set reminder
                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");

                }
                else{
                    cancelAlarm();
                }

            }
        });

        //when the clear all is initialised
        clearAllSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Log.v(TAG, "clear all checked");
                    getResources().getIntArray(R.array.favourites);
                }
                else {
                    Log.v(TAG, "clear all not checked");
                }

                // Set the state of the switch bar
                SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("clearAll_switchkey", isChecked);
                editor.commit();
            }
        });

        // when the favourite plant switch  in initialised
        favouritePlantSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Log.v(TAG, "favourite checked");
                    listview.setVisibility(View.VISIBLE);
                    Toast.makeText(SettingsActivity.this, "Select a plant from the list", Toast.LENGTH_SHORT).show();
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.v(TAG, "position: "+position);
                            //save favourite in preferences
                            SharedPreferences favsettings = getSharedPreferences("favourite_plant", 0);
                            SharedPreferences.Editor editor = favsettings.edit();
                            editor.putInt("favourite_plant", position);
                            editor.commit();
                            listview.setVisibility(View.INVISIBLE);
                            Toast.makeText(SettingsActivity.this, "Favourite plant set", Toast.LENGTH_SHORT).show();
                            setFavSpinner(position);             //add favourite to spinner

                        }
                    });
                }
                else {
                    Log.v(TAG, "favourite not checked");
                    //favourite value set to zero
                    SharedPreferences favsettings = getSharedPreferences("favourite_plant", 0);
                    SharedPreferences.Editor editor = favsettings.edit();
                    editor.putInt("favourite_plant", 0);
                    editor.commit();
                    listview.setVisibility(View.INVISIBLE);
                    Toast.makeText(SettingsActivity.this, "Favourite plant disabled", Toast.LENGTH_SHORT).show();
                    //set spinner to invisible
                    favouriteSpinner.setVisibility(View.INVISIBLE);
                }
                // Set the state of the switch bar
                SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("favourite_switchkey", isChecked);
                editor.commit();
            }
        });

    }
    public void setFavSpinner(int index){
        // add favourite to spinner
        String [] favouriteplant =new String[] {plantNames[index]};
        Log.v(TAG, "favourite plant"+favouriteplant[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                favouriteplant);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favouriteSpinner.setAdapter(adapter);
        favouriteSpinner.setVisibility(View.VISIBLE);
        //favouriteSpinner.setDropDownVerticalOffset();


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.v(TAG, "onTimeSet initialised");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        //Seperate Method ?
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        Toast.makeText(this, timeText, Toast.LENGTH_SHORT).show();

        startAlarm(calendar);

    }

    private void startAlarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Reminder Cancelled", Toast.LENGTH_SHORT).show();
    }

    public void onGoBack(View view) {

        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
        onPause();
    }

    public void onRefresh(View view) {
        notificationsSwitch.setChecked(false);
        clearAllSwitch.setChecked(false);
        favouritePlantSwitch.setChecked(false);
    }
}
