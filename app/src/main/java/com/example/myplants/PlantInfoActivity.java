package com.example.myplants;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlantInfoActivity extends AppCompatActivity implements PlantNamesFragment.PlantListener {
    private static final String TAG= "PlantInfo";
    private TextView lightRequirementDetails;
    private TextView waterRequirementDetails;
    private TextView funFactsDetails;
    private TextView lightRequirement_txt;
    private TextView waterRequirement_txt;
    private TextView funFacts_txt;
    private TextView hint_txt;
    private ImageView plantImage;
    private FloatingActionButton btnAddFavourite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);

        lightRequirementDetails=findViewById(R.id.light_requirements);
        waterRequirementDetails=findViewById(R.id.water_requirements);
        funFactsDetails=findViewById(R.id.fun_fact);

        lightRequirement_txt=findViewById(R.id.light_requirements_txt);
        waterRequirement_txt=findViewById(R.id.water_requirements_txt);
        funFacts_txt=findViewById(R.id.fun_facts_txt);
        hint_txt=findViewById(R.id.plant_info_hint_txt);

        plantImage=findViewById(R.id.plant_image);
        btnAddFavourite = (FloatingActionButton) findViewById(R.id.btnAddFavourite);
        btnAddFavourite.hide();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dropdown_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.mnuHome:
                intent= new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuFavourites:
                intent= new Intent(this, FavouriteActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuPlantInfo:
                intent= new Intent(this, PlantInfoActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }



    // method sets a description to the 'plantDetails Textview' when a plant is selected
    @Override
    public void onPlantSelected(int index) {

        lightRequirement_txt.setText("Light Requirements");
        String [] lightRequirements= getResources().getStringArray(R.array.lightRequirements);
        lightRequirementDetails.setText(lightRequirements[index]);

        waterRequirement_txt.setText("Water Requirements");
        String [] waterRequirements= getResources().getStringArray(R.array.waterRequirements);
        waterRequirementDetails.setText(waterRequirements[index]);

        funFacts_txt.setText("Fun Fact");
        String [] funFacts =getResources().getStringArray(R.array.funFacts);
        funFactsDetails.setText(funFacts[index]);

        hint_txt.setText("");

        Drawable d=getResources().obtainTypedArray(R.array.plantimages).getDrawable(index);
        plantImage.setImageDrawable(d);

        btnAddFavourite.show();
        final int favIndex = index;

        btnAddFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] favourites = getResources().getIntArray(R.array.favourites);
                favourites [favourites.length-1] = favIndex;
            }
        });
    }
}
