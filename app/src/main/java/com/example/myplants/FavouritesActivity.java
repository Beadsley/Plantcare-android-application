package com.example.myplants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavouritesActivity extends OptionsMenuActivity implements PlantNamesFragment.PlantListener{

    private static  final String TAG= "MyPlantAppFavourites";
    ListView listview;
    TextView lightRequirementDetails;
    TextView waterRequirementDetails;
    TextView funFactsDetails;
    TextView lightRequirement_txt;
    TextView waterRequirement_txt;
    TextView funFacts_txt;
    TextView hint_txt;
    ImageView plantImage;
    ImageView indoorPlants;
    int[] favourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Favourites");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent intent = new Intent(FavouritesActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_plantinfo:
                        intent = new Intent(FavouritesActivity.this, PlantInfoActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_favourites:
                        intent = new Intent(FavouritesActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_settings:
                        intent = new Intent(FavouritesActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        listview=findViewById(R.id.listview_favourites);

        lightRequirementDetails=findViewById(R.id.light_requirements);
        waterRequirementDetails=findViewById(R.id.water_requirements);
        funFactsDetails=findViewById(R.id.fun_fact);

        lightRequirement_txt=findViewById(R.id.light_requirements_txt);
        waterRequirement_txt=findViewById(R.id.water_requirements_txt);
        funFacts_txt=findViewById(R.id.fun_facts_txt);

        plantImage=findViewById(R.id.plant_image);
        indoorPlants=findViewById(R.id.indoorPlants);

        //open hashset of names
        Set<String> favouriteSet = new HashSet<String>();
        SharedPreferences settingsopen = getSharedPreferences("fav_id", 0);
        favouriteSet= settingsopen.getStringSet("favourites",new HashSet<String>());
        Log.v(TAG, "favourites from set: "+favouriteSet);

        //check if list is empty
        ArrayList<String> favouritesNames2= new ArrayList<>();
        final ArrayList<Integer> favouriteIndices= new ArrayList<>();
        for (String s: favouriteSet){
            int index= Integer.parseInt(s);
            Log.v(TAG, "indices from set: "+index);
            favouritesNames2.add((getResources().getStringArray(R.array.plants))[index]);
            favouriteIndices.add(index);
        }
        Log.v(TAG, "names from set: " +favouritesNames2);
        Log.v(TAG, "indices from list"+ favouriteIndices);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1,
                favouritesNames2);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = favouriteIndices.get(position);
                Log.v(TAG, "Position: "+position);
                Log.v(TAG, "index: "+ index);
                onPlantSelected(index);
            }
        });
    }

    // method sets a description to the 'plantDetails Textview' when a plant is selected
    @Override
    public void onPlantSelected(int index) {
        indoorPlants.setVisibility(View.INVISIBLE);
        Log.v(TAG, "index passed:"+index);
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

    }
    public void onGoBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
