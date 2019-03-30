package com.example.myplants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PlantInfoActivity extends AppCompatActivity implements PlantsFragment.PlantListener {
    private static final String TAG= "PlantInfo";
    TextView lightRequirementDetails;
    TextView waterRequirementDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        Log.v(TAG, "Plant Info Initialised");
        lightRequirementDetails=findViewById(R.id.light_requirements);
        waterRequirementDetails=findViewById(R.id.water_requirements);

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
        String [] lightRequirements= getResources().getStringArray(R.array.lightRequirements);
        lightRequirementDetails.setText(lightRequirements[index]);
        String [] waterRequirements= getResources().getStringArray(R.array.waterRequirements);
        waterRequirementDetails.setText(waterRequirements[index]);
    }
}
