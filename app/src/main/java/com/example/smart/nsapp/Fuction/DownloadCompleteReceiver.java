package com.example.smart.nsapp.Fuction;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.example.smart.nsapp.InstallAPK.DownloadStatus;
import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadCompleteReceiver extends BroadcastReceiver {

    private String TAG = "DownloadCompleteReceiver";
    private DownloadStatus downloadStatus;

    public void setListener(DownloadStatus downloadStatus){
        this.downloadStatus = downloadStatus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                Log.e(TAG, "context = " + context);
                Toast.makeText(context, "下載完成",
                        //To notify the Client that the file is being downloaded
                        Toast.LENGTH_SHORT).show();

                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                assert downloadManager != null;
                String type = downloadManager.getMimeTypeForDownloadedFile(downloadId);
                if (TextUtils.isEmpty(type)) {
                    type = "*/*";
                }
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                Cursor c = downloadManager.query(query);
                if (c.moveToFirst()) {
                    int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    // 下载失败也会返回这个广播，所以要判断下是否真的下载成功
                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                        // 获取下载好的 apk 路径
                        String downloadFilePath = null;
                        String downloadFileLocalUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        if (downloadFileLocalUri != null) {
                            downloadStatus.openAPK(downloadFileLocalUri, type);
                        }
                        // 提示用户安装
                    }
                }
            }
        }
    }
}