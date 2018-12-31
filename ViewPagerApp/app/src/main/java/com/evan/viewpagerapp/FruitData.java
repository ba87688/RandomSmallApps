package com.evan.viewpagerapp;

public class FruitData {
    private String name;
    private int ImageResId;

    private FruitData(String name, int imageResId) {
        this.name = name;
        this.ImageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return ImageResId;
    }

    public static final FruitData[] fruits = {
            new FruitData("Apples", R.drawable.apples),
            new FruitData("Watermelon", R.drawable.watermelon),
            new FruitData("watermelon", R.drawable.watermelon),
            new FruitData("apples", R.drawable.apples)
    };
}
