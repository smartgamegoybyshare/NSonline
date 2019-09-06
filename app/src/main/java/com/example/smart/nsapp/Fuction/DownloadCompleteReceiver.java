package com.example.smart.nsapp.Fuction;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadCompleteReceiver extends BroadcastReceiver {

    private String TAG = "DownloadCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                Toast.makeText(context, "下載完成",
                        //To notify the Client that the file is being downloaded
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}