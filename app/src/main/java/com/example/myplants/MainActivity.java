package com.example.myplants;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends OptionsMenuActivity {
    private static  final String TAG= "MyPlantAPP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView logo = findViewById(R.id.logo);
        logo.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        setTitle("");



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(0);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:

                        break;
                    case R.id.nav_plantinfo:
                        Intent intent1 = new Intent(MainActivity.this, PlantInfoActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_favourites:
                        Intent intent2 = new Intent(MainActivity.this, FavouritesActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_settings:
                        Intent intent3 = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });
    }



    //method sends the user to the plant info activity


    public void onPlantInfo(View view) {
        Intent intent= new Intent(this, PlantInfoActivity.class);
        startActivity(intent);
       // finish();
    }

    public void onClickFavourites(View view) {
        Intent intent = new Intent(this, FavouritesActivity.class);
        startActivity(intent);
        //finish();
    }

    public void onClickSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        //finish();
    }
}
