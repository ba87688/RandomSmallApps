package com.evan.viewpagerapp;

public class HamData {

    private String name;
    private int ImageResId;

    private HamData(String name, int imageResId) {
        this.name = name;
        this.ImageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return ImageResId;
    }

    public static final HamData[] hams = {
            new HamData("Salami", R.drawable.salami),
            new HamData("Best Ham", R.drawable.ham1),
            new HamData("Salami", R.drawable.salami),
            new HamData("Best Ham", R.drawable.ham1),
            new HamData("Salami", R.drawable.salami)
    };
}
