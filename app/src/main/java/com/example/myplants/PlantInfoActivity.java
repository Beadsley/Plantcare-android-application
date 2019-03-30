package com.example.myplants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PlantInfoActivity extends AppCompatActivity implements PlantsFragment.PlantListener {
    private static final String TAG= "PlantInfo";
    TextView plantDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        Log.v(TAG, "Plant Info Initialised");
        plantDetails=findViewById(R.id.plant_details);

    }
    // sends the user back to the main menu when the go back button is selected
    public void onGoBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
    // method sets a description to the 'plantDetails Textview' when a plant is selected
    @Override
    public void onPlantSelected(int index) {
        Log.v(TAG,"selcted index: "+index);
        String [] plantDescriptions= getResources().getStringArray(R.array.plantDescriptions);
        plantDetails.setText(plantDescriptions[index]);
    }
}
