package com.example.smart.nsapp.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.smart.nsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetViewPager {

    private String TAG = "SetViewPager";

    public SetViewPager() {
        super();
    }

    public View setView(Context context, Vibrator vibrator) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.tablayoutview, null);

        ListView listView = view.findViewById(R.id.listview);

        List<String> tablist = new ArrayList<>();
        tablist.clear();
        tablist.add("");

        return view;
    }
}
