package com.example.smart.nsapp.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.smart.nsapp.Fuction.Loading;
import com.example.smart.nsapp.R;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

public class NewsList extends BaseAdapter {

    private String TAG = "NewsList";
    private List<String> nameList;
    private List<View> viewlist;


    @SuppressLint("InflateParams")
    public NewsList(Context context, List<String> nameList, Vibrator vibrator) {
        this.nameList = nameList;
        viewlist = new ArrayList<>();
        viewlist.clear();
        for (int i = 0; i < nameList.size(); i++) {
            if (i == 0) {
                View view;
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(R.layout.videoview, null);
                viewlist.add(view);
            } else {

            }
        }
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return nameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean areAllItemsEnabled() {   //禁用item被點擊
        return false;
    }

    @Override
    public boolean isEnabled(int position) {    //禁用item被點擊
        return false;
    }

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view;
        view = viewlist.get(position);

        if(position == 0){

        }
        else {

        }

        return view;
    }


}
