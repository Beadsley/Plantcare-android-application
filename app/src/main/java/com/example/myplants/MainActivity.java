package com.example.myplants;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static  final String TAG= "MyPlantAPP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HOME");
    }
    //method sends the user to the plant info activity
    public void onPlantInfo(View view) {
        Intent intent= new Intent(this, PlantInfoActivity.class);
        startActivity(intent);
    }




}
