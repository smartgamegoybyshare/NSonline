package com.example.smart.nsapp.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import com.example.smart.nsapp.R;

public class CombineView {

    private String TAG = "CombineView";
    private Context context;

    public CombineView(Context context) {
        this.context = context;
    }

    public View setView(Vibrator vibrator){
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.combineview, null);

        return view;
    }
}
