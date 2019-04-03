package com.example.myplants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PlantInfoActivity extends OptionsMenuActivity implements PlantNamesFragment.PlantListener {
    private static final String TAG= "PlantInfo";
    TextView lightRequirementDetails;
    TextView waterRequirementDetails;
    TextView funFactsDetails;
    TextView lightRequirement_txt;
    TextView waterRequirement_txt;
    TextView funFacts_txt;
    TextView hint_txt;
    ImageView plantImage;
    ImageView indoorPlants;
    private FloatingActionButton btnAddFavourite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Plant Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lightRequirementDetails = findViewById(R.id.light_requirements);
        waterRequirementDetails = findViewById(R.id.water_requirements);
        funFactsDetails = findViewById(R.id.fun_fact);

        lightRequirement_txt = findViewById(R.id.light_requirements_txt);
        waterRequirement_txt = findViewById(R.id.water_requirements_txt);
        funFacts_txt = findViewById(R.id.fun_facts_txt);

        plantImage = findViewById(R.id.plant_image);
        indoorPlants = findViewById(R.id.indoorPlants);

        btnAddFavourite = (FloatingActionButton) findViewById(R.id.btnAddFavourite);
        btnAddFavourite.hide();

        //retreive index of favourite plant set by user, default 0
        SharedPreferences favsettings = getSharedPreferences("favourite_plant", 0);
        int favNumber=favsettings.getInt("favourite_plant", 0);
        Log.v(TAG, "favourite number on create: "+favNumber);
        onPlantSelected(favNumber);

    }

    // method sets a description to the 'plantDetails Textview' when a plant is selected
    @Override
    public void onPlantSelected(int index) {
        indoorPlants.setVisibility(View.INVISIBLE);

        lightRequirement_txt.setText("Light Requirements");
        String [] lightRequirements= getResources().getStringArray(R.array.lightRequirements);
        lightRequirementDetails.setText(lightRequirements[index]);

        waterRequirement_txt.setText("Water Requirements");
        String [] waterRequirements= getResources().getStringArray(R.array.waterRequirements);
        waterRequirementDetails.setText(waterRequirements[index]);

        funFacts_txt.setText("Fun Fact");
        String [] funFacts =getResources().getStringArray(R.array.funFacts);
        funFactsDetails.setText(funFacts[index]);



        Drawable d=getResources().obtainTypedArray(R.array.plantimages).getDrawable(index);
        plantImage.setImageDrawable(d);

        btnAddFavourite.show();
        final int favIndex = index;

        btnAddFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: favourite button pressed");
                Toast.makeText(PlantInfoActivity.this, "Plant added to your favourites", Toast.LENGTH_LONG).show();
                int[] favourites = getResources().getIntArray(R.array.favourites);
                    favourites [favourites.length-1] = favIndex;
                    Log.v(TAG,"favourites"+favourites );
            }
        });

    }

}

