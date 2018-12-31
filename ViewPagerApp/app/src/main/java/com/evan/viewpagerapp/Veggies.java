package com.evan.viewpagerapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Veggies extends Fragment {


    public Veggies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        RecyclerView veggiesRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_veggies,
                container,false);

        String[] veggieNames = new String[VeggieData.veggies.length];
        for(int i=0; i<veggieNames.length; i++){
            veggieNames[i] = VeggieData.veggies[i].getName();
        }

        int[] veggieImages = new int[VeggieData.veggies.length];
        for(int i=0; i<veggieImages.length; i++) {
            veggieImages[i] = VeggieData.veggies[i].getImageResId();
        }

            ImagesAndNamesAdapter adapter = new ImagesAndNamesAdapter(veggieNames, veggieImages);
        veggiesRecycler.setAdapter(adapter);

        //set a grid layout of the recyclerview. 2 column grid
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        veggiesRecycler.setLayoutManager(layoutManager);

        return veggiesRecycler;
    }

}
