package com.example.smart.nsapp.Fuction;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Screen {

    public Screen(){
        super();
    }

    public DisplayMetrics size(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
