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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class SettingsActivity extends OptionsMenuActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG= "PlantSettings";
    Switch notificationsSwitch;
    Switch clearAllSwitch;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.v(TAG,"onCreate initialised");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        clearAllSwitch= findViewById(R.id.switch_clear_favourites);
        notificationsSwitch= findViewById(R.id.switch_notifications);
        // finding the state of the switch bars
        SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
        boolean notification = settings.getBoolean("notification_switchkey", false);
        boolean clearAll = settings.getBoolean("clearAll_switchkey", false);
        Log.v(TAG, "state :"+notification);
        notificationsSwitch.setChecked(notification);
        clearAllSwitch.setChecked(clearAll);

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



}
