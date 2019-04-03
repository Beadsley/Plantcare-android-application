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
import java.util.HashSet;
import java.util.Set;

public class SettingsActivity extends OptionsMenuActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG= "PlantSettings";
    Switch notificationsSwitch;
    Switch clearAllSwitch;
    ListView listview;
    String [] plantNames;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        Log.v(TAG,"onCreate initialised");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        clearAllSwitch= findViewById(R.id.switch_clear_favourites);
        notificationsSwitch= findViewById(R.id.switch_notifications);
        listview=findViewById(R.id.listview_favourites);

        // create a listview of plant names
        plantNames =getResources().getStringArray(R.array.plants);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,plantNames);
        listview.setAdapter(adapter);        

        // finding the state of the switch bars
        SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
        boolean notification = settings.getBoolean("notification_switchkey", false);
        boolean clearAll = settings.getBoolean("clearAll_switchkey", false);

        notificationsSwitch.setChecked(notification);
        clearAllSwitch.setChecked(clearAll);

        //set state alarm txt
        Log.v(TAG, "reminder :"+notification);
        if (notification){
            SharedPreferences remindSettings=getSharedPreferences("reminder_id",0);
            String alarmText=remindSettings.getString("alarm", "Set Daily Reminder");
            notificationsSwitch.setText(alarmText);
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
                    notificationsSwitch.setText("Set Daily Reminder");
                    cancelAlarm();
                }
                // Set the state of the switch bar
                SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("notification_switchkey", isChecked);
                editor.commit();

            }
        });

        //when the clear all is initialised
        clearAllSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Log.v(TAG, "clear all checked");

                    // save HashSet
                    SharedPreferences settings = getSharedPreferences("fav_id", 0);
                    SharedPreferences.Editor editor = settings.edit();

                    editor.clear();

                    editor.commit();
                    Toast.makeText(SettingsActivity.this, "All Favourites removed", Toast.LENGTH_SHORT).show();

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
        //save timeText
        SharedPreferences settings = getSharedPreferences("reminder_id", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("alarm", timeText);
        editor.commit();

        //set txt
        notificationsSwitch.setText(timeText);

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
        clearAllSwitch.setChecked(false);
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
        onPause();
    }

    public void onRefresh(View view) {
        notificationsSwitch.setChecked(false);
        clearAllSwitch.setChecked(false);
    }
}
