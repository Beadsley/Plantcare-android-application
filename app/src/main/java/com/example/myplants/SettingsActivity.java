package com.example.myplants;
/**
 *
 * Class Creates a settings activity
 * allowing the user to set an alarm
 * that will notify the user every day at
 * a specified time. The user will also
 * be able to clear all favourites
 *
 * @author Anastasija Gurejeva
 * @author Daniel Beadleson
 * @author Mahlet Mulu
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    boolean reminderSet;
    boolean clearAll;
    /*
     * Method creates the initial state of the settings activity
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        Log.v(TAG,"onCreate initialised");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Settings");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /*
         * Method creates a pathway to the other
         * activities via a navigation bar
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                clearAllSwitch.setChecked(false);

                switch (id) {
                    case R.id.nav_home:
                        Intent intent0 = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.nav_plantinfo:
                        Intent intent1 = new Intent(SettingsActivity.this, PlantInfoActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_favourites:
                        Intent intent2 = new Intent(SettingsActivity.this, FavouritesActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_settings:

                        break;
                }
                return false;
            }
        });

        clearAllSwitch= findViewById(R.id.switch_clear_favourites);
        notificationsSwitch= findViewById(R.id.switch_notifications);
        listview=findViewById(R.id.listview_favourites);

        switchBarPreviousStates();

        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked){
                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                }
                else{
                    notificationsSwitch.setText("Set Daily Reminder");
                    cancelAlarm();
                }
                storeNotificationsSwitchState(isChecked);
            }
        });

        clearAllSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Log.v(TAG, "clear all checked");
                    clearFavHashSet();
                }
                else {
                    Log.v(TAG, "clear all not checked");
                }
                storeClearAllSwitchState(isChecked);
            }
        });
    }
    /*
     * Method sets the state of the switch bars to
     * their original state
     */
    public void switchBarPreviousStates(){
        SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
        reminderSet = settings.getBoolean("notification_switchkey", false);
        clearAll = settings.getBoolean("clearAll_switchkey", false);
        notificationsSwitch.setChecked(reminderSet);
        clearAllSwitch.setChecked(clearAll);
        Log.v(TAG, "reminder :"+reminderSet);

        if (reminderSet){
            SharedPreferences remindSettings=getSharedPreferences("reminder_id",0);
            String alarmText=remindSettings.getString("alarm", "Set Daily Reminder");
            notificationsSwitch.setText(alarmText);
        }
    }
    /*
     * Method stores the state of the notification switch bar
     */
    public void storeNotificationsSwitchState(Boolean isChecked){
        SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("notification_switchkey", isChecked);
        editor.commit();
    }
    /*
     * Method stores the state of the clear all switch bar
     */
    public void storeClearAllSwitchState(Boolean isChecked){
        SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("clearAll_switchkey", isChecked);
        editor.commit();
    }

    /*
     * Method clears the hashset of favourite plant
     * indices and stores that new state of the
     * hashset
     */
    public void clearFavHashSet(){
        SharedPreferences settings = getSharedPreferences("fav_id", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(SettingsActivity.this, "All Favourites removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.v(TAG, "onTimeSet initialised");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        Toast.makeText(this, timeText, Toast.LENGTH_SHORT).show();
        storeAlarmTime(timeText);
        notificationsSwitch.setText(timeText);

        startAlarm(calendar);
    }

    /*
     * Stores the time in which the
     * alarm is set by the user
     */
    public void storeAlarmTime(String timeText){
        SharedPreferences settings = getSharedPreferences("reminder_id", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("alarm", timeText);
        editor.commit();
    }

    /*
     * Method initialises the alarm
     */
    private void startAlarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    /*
     * Method cancels the alarm when the
     * switch is turned off
     */
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Reminder Cancelled", Toast.LENGTH_SHORT).show();
    }

    /*
     * Method sets all the switch bars
     * to not checked
     */
    public void onRefresh(View view) {
        notificationsSwitch.setChecked(false);
        clearAllSwitch.setChecked(false);
    }
}
