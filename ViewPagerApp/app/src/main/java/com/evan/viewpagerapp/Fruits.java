package com.evan.viewpagerapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fruits extends Fragment {


    public Fruits() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView =(RecyclerView) inflater.inflate(R.layout.fragment_fruits,
                container, false);


        String[] hamNames = new String[FruitData.fruits.length];
        for(int i=0;i<hamNames.length;i++){
            hamNames[i] = FruitData.fruits[i].getName();
        }

        int[] hamImages = new int[FruitData.fruits.length];
        for(int i=0;i<hamImages.length;i++){
            hamImages[i] = FruitData.fruits[i].getImageResId();
        }


        ImagesAndNamesAdapter imagesAndNamesAdapter = new ImagesAndNamesAdapter(hamNames,hamImages);
        recyclerView.setAdapter(imagesAndNamesAdapter);


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);


        return recyclerView;
    }

}
