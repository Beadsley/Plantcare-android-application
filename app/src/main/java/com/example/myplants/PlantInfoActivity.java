package com.example.myplants;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlantInfoActivity extends AppCompatActivity implements PlantsFragment.PlantListener {
    private static final String TAG= "PlantInfo";
    TextView lightRequirementDetails;
    TextView waterRequirementDetails;
    ImageView plantImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        //Log.v(TAG, "Plant Info Initialised");
        lightRequirementDetails=findViewById(R.id.light_requirements);
        waterRequirementDetails=findViewById(R.id.water_requirements);
        plantImage=findViewById(R.id.plant_image);

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
        //Log.v(TAG,"selcted index: "+index);
        String [] lightRequirements= getResources().getStringArray(R.array.lightRequirements);
        lightRequirementDetails.setText(lightRequirements[index]);
        String [] waterRequirements= getResources().getStringArray(R.array.waterRequirements);
        waterRequirementDetails.setText(waterRequirements[index]);


         Drawable d=getResources().obtainTypedArray(R.array.plantimages).getDrawable(index);

        //int[] plantimages2=getResources().getIntArray(R.array.plantimages);
        //String [] plantimages=getResources().getStringArray(R.array.plantimages);
        Log.v(TAG,d+"    yhyhyh");
        plantImage.setImageDrawable(d);
        //plantImage.setImageDrawable(getResources().getDrawable(R.drawable.plant1));
        //plantImage.setImageDrawable(getResources().getDrawable(R.drawable.plantimages2[index]));
    }
}
