package com.example.myplants;
/**
 *
 * Fragment of the plant names, which is visible
 * within the favourite and plan info activities
 * @author Anastasija Gurejeva
 * @author Daniel Beadleson
 * @author Mahlet Mulu
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PlantNamesFragment extends ListFragment {
    private PlantListener plantListener;

    public PlantNamesFragment() {
        // Required empty public constructor
    }

    // Method Lists the plants from the arrays.xml resource file
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.plants)));
    }
    // Interface allows the fragment to talk to the activity
    // fragments can't directly talk to one another, i.e. activity acts as a middle man
    public interface PlantListener{
        public void onPlantSelected(int index);
    }
    // Method makes sure an activity implements the interface, when a fragrment is attached to that activity
    @Override
    public void onAttach(Context context) {
            super.onAttach(context);
            try {
                plantListener= (PlantListener) context;
            }
            catch(ClassCastException e){
                    throw new ClassCastException(context.toString()+" must implement the interace"+" PlantListener!");

            }
    }
    // Method passes the index on the plant selected
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        plantListener.onPlantSelected(position);
    }
}
