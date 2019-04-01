package com.example.myplants;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity implements PlantNamesFragment.PlantListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        listview=findViewById(R.id.listview_favourites);

        lightRequirementDetails=findViewById(R.id.light_requirements);
        waterRequirementDetails=findViewById(R.id.water_requirements);
        funFactsDetails=findViewById(R.id.fun_fact);

        lightRequirement_txt=findViewById(R.id.light_requirements_txt);
        waterRequirement_txt=findViewById(R.id.water_requirements_txt);
        funFacts_txt=findViewById(R.id.fun_facts_txt);
        hint_txt=findViewById(R.id.plant_info_hint_txt);

        plantImage=findViewById(R.id.plant_image);
        //create an arraylist of favourite plant indices
        final ArrayList<Integer> favouritesIndices= new ArrayList<>();
        favouritesIndices.add(0);
        favouritesIndices.add(1);
        favouritesIndices.add(3);
        Log.v(TAG, "indices: "+favouritesIndices);

        //create an arraylist of favourite plant names
        ArrayList<String> favouritesNames= new ArrayList<>();
        for (int i=0; i<favouritesIndices.size();i++){
            int index=favouritesIndices.get(i);
            Log.v(TAG, "index: "+index);
            favouritesNames.add((getResources().getStringArray(R.array.plants))[index]);
            Log.v(TAG, "names: " +favouritesNames.get(i));
        }

        final ArrayAdapter<String> adapter =new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1,
                favouritesNames);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, "Position: "+position);
                Log.v(TAG, "index: "+favouritesIndices.get(position));
                onPlantSelected(favouritesIndices.get(position));
            }
        });
    }

    // method sets a description to the 'plantDetails Textview' when a plant is selected
    @Override
    public void onPlantSelected(int index) {
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

        hint_txt.setText("");

        Drawable d=getResources().obtainTypedArray(R.array.plantimages).getDrawable(index);
        plantImage.setImageDrawable(d);

    }
    public void onGoBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
