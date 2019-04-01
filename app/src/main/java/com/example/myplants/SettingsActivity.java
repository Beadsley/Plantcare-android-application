package com.example.myplants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    Switch notificationsSwitch;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        notificationsSwitch= findViewById(R.id.switch_notifications);
        submit=findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationsSwitch.isChecked()){
                    Toast.makeText(SettingsActivity.this, "Checked baby", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SettingsActivity.this, "Not checked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onGoBack(View view) {
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
