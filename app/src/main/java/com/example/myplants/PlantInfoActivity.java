package com.example.myplants;

import android.content.Intent;
import android.database.Cursor;
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

import static com.example.myplants.R.id.plant_info_hint_txt;

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
    private FloatingActionButton btnAddFavourite;
    private DataBaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        db = new DataBaseHelper(this);
        //db.onCreate(db.getWritableDatabase());
        db.getWritableDatabase();
        //db.getReadableDatabase();
        System.out.println("hereeeeeeeeeeee");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("PLANT INFO");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lightRequirementDetails = findViewById(R.id.light_requirements);
        waterRequirementDetails = findViewById(R.id.water_requirements);
        funFactsDetails = findViewById(R.id.fun_fact);

        lightRequirement_txt = findViewById(R.id.light_requirements_txt);
        waterRequirement_txt = findViewById(R.id.water_requirements_txt);
        funFacts_txt = findViewById(R.id.fun_facts_txt);
        hint_txt = findViewById(plant_info_hint_txt);

        plantImage = findViewById(R.id.plant_image);

        btnAddFavourite = (FloatingActionButton) findViewById(R.id.btnAddFavourite);
        btnAddFavourite.hide();

    }

    // method sets a description to the 'plantDetails Textview' when a plant is selected
    @Override
    public void onPlantSelected(int index) {
        Cursor select = db.getFilterData();
        boolean m = select.moveToPosition(index);
        //System.out.println(index);
        //select.move(index);
        int light_index = select.getColumnIndex("Light_requirements");
        int water_index = select.getColumnIndex("Water_requirements");
        int fun_index = select.getColumnIndex("Fun_fact");
        int image_index = select.getColumnIndex("Image_Resources_ID");
        //int light_index = select.getColumnIndex("Light_requirements");
        lightRequirement_txt.setText("Light Requirements");

        //String [] lightRequirements= getResources().getStringArray(R.array.lightRequirements);
        lightRequirementDetails.setText(select.getString(light_index));

        waterRequirement_txt.setText("Water Requirements");
        //String [] waterRequirements= getResources().getStringArray(R.array.waterRequirements);
        waterRequirementDetails.setText(select.getString(water_index));

        funFacts_txt.setText("Fun Fact");
        //String [] funFacts =getResources().getStringArray(R.array.funFacts);
        funFactsDetails.setText(select.getString(fun_index));

        hint_txt.setText("");

        int imageID = select.getInt(image_index);

        Drawable d = getResources().getDrawable(imageID);
                //obtainTypedArray(R.array.plantimages).getDrawable(index);
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

