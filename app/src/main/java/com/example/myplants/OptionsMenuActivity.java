package com.example.myplants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class OptionsMenuActivity extends AppCompatActivity {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.mnuHome:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                // finish();
                break;
            case R.id.mnuPlantInfo:
                intent = new Intent(this, PlantInfoActivity.class);
                startActivity(intent);
                // finish();
                break;
            case R.id.mnuFavourites:
                intent = new Intent(this, FavouritesActivity.class);
                startActivity(intent);
                // finish();
                break;
            case R.id.mnuReminder:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                // finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
