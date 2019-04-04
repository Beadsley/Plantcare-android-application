package com.example.myplants;
/**
 *
 * Fragment of the plant image, which is visible
 * within the favourite and plan info activities
 *
 * @author Anastasija Gurejeva
 * @author Daniel Beadleson
 * @author Mahlet Mulu
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

}
