package com.example.myplants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;


public class PlantInfoActivity extends OptionsMenuActivity implements PlantNamesFragment.PlantListener {
    private static final String TAG= "PlantInfo";
    TextView lightRequirementDetails;
    TextView waterRequirementDetails;
    TextView funFactsDetails;
    TextView lightRequirement_txt;
    TextView waterRequirement_txt;
    TextView funFacts_txt;
    TextView plantTitle;
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent intent = new Intent(PlantInfoActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_plantinfo:
                        intent = new Intent(PlantInfoActivity.this, PlantInfoActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_favourites:
                        intent = new Intent(PlantInfoActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_settings:
                        intent = new Intent(PlantInfoActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });


        plantTitle = findViewById(R.id.plant_title);
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

    }

    // method sets a description to the 'plantDetails Textview' when a plant is selected
    @Override
    public void onPlantSelected(int index) {
        indoorPlants.setVisibility(View.INVISIBLE);

        String [] plantName = getResources().getStringArray(R.array.plants);
        plantTitle.setText(plantName[index]);

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

                //initialise set
                Set<String> favouriteSet = new HashSet<String>();

                // open Hashset
                SharedPreferences settingsopen = getSharedPreferences("fav_id", 0);
                favouriteSet= settingsopen.getStringSet("favourites",new HashSet<String>());

                //boolean notification = settings.getBoolean("notification_switchkey", false);

                // save HashSet
                SharedPreferences settings = getSharedPreferences("fav_id", 0);
                SharedPreferences.Editor editor = settings.edit();

                favouriteSet.add(""+favIndex);

                Log.v(TAG, ""+favouriteSet);

                editor.clear();
                editor.putStringSet("favourites",favouriteSet);
                editor.commit();
            }
        });

    }

}

