package com.example.ecommerceapp.Utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Utility {
    public static List<ColorDrawable> vibrantLightColorList = new ArrayList<>();


    public static ColorDrawable getRandomColor() {
        vibrantLightColorList.add(new ColorDrawable(Color.parseColor("#ffeead")));
        vibrantLightColorList.add(new ColorDrawable(Color.parseColor("#93cfb3")));
        vibrantLightColorList.add(new ColorDrawable(Color.parseColor("#fd7a7a")));
        vibrantLightColorList.add(new ColorDrawable(Color.parseColor("#faca5f")));
        vibrantLightColorList.add(new ColorDrawable(Color.parseColor("#1ba798")));
        vibrantLightColorList.add(new ColorDrawable(Color.parseColor("#6aa9ae")));
        vibrantLightColorList.add(new ColorDrawable(Color.parseColor("#ffbf27")));
        vibrantLightColorList.add(new ColorDrawable(Color.parseColor("#d93947")));
        int pos = new Random().nextInt(vibrantLightColorList.size());
        return vibrantLightColorList.get(pos);
    }
}


