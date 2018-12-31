package com.evan.viewpagerapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Ham extends Fragment {


    public Ham() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView= (RecyclerView) inflater.inflate(R.layout.fragment_ham, container, false);

        String[] hamNames = new String[HamData.hams.length];
        for(int i=0;i<hamNames.length;i++){
            hamNames[i] = HamData.hams[i].getName();
        }

        int[] hamImages = new int[HamData.hams.length];
        for(int i=0;i<hamImages.length;i++){
            hamImages[i] = HamData.hams[i].getImageResId();
        }


        ImagesAndNamesAdapter imagesAndNamesAdapter = new ImagesAndNamesAdapter(hamNames,hamImages);
        recyclerView.setAdapter(imagesAndNamesAdapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);


        return recyclerView;

    }

}
