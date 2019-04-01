package com.example.myplants;

import android.app.Notification;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private static  final String TAG= "MyPlantAppSettings";
    private Switch notificationsSwitch;
    private Button submit;
    private NotificationManagerCompat notificationManager;
    Boolean notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Notify: "+notify);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationManager = NotificationManagerCompat.from(this);

        notificationsSwitch= findViewById(R.id.switch_notifications);
        submit=findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationsSwitch.isChecked()){
                    Toast.makeText(SettingsActivity.this, "Checked baby", Toast.LENGTH_SHORT).show();
                    notify=true;
                    Log.v(TAG, "Notify: "+notify);


                }
                else {

                    Toast.makeText(SettingsActivity.this, "Not checked", Toast.LENGTH_SHORT).show();
                    notify=false;
                    Log.v(TAG, "Notify: "+notify);
                }
            }
        });

    }

    public void onGoBack(View view) {


        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onclickTEst(View view) {
        if(notify==null){
            Log.v(TAG, "Not Initialised");
            //notify user with review

        }
        else if(notify==true){
            Log.v(TAG, "Initialised");
            Notification notification = new NotificationCompat.Builder(this, AppNotificationManager.CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.icon_flower)
                    .setContentTitle("Water Warning")
                    .setContentText("Remember to water your plants today")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();

            notificationManager.notify(1, notification);

        }
    }
}
