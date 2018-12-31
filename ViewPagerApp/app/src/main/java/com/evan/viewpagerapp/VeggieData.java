package com.evan.viewpagerapp;

public class VeggieData {
    private String name;
    private int ImageResId;

    private VeggieData(String name, int imageResId) {
        this.name = name;
        this.ImageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return ImageResId;
    }

    public static final VeggieData[] veggies = {
            new VeggieData("Broccoli", R.drawable.broccoli),
            new VeggieData("Brussel Sprouts", R.drawable.brussels_sprouts)
    };

}
