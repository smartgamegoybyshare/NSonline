package com.example.smart.nsapp.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.IntentFilter;
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
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.smart.nsapp.Fuction.DownloadCompleteReceiver;
import com.example.smart.nsapp.Fuction.Loading;
import com.example.smart.nsapp.R;

import static android.content.Context.DOWNLOAD_SERVICE;

public class VideoView {

    private String TAG = "VideoView";
    private WebView webview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean swipe = false;
    private Handler refreshHandler = new Handler();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Loading loading;
    private Context context;

    public VideoView(Context context) {
        this.context = context;
        loading = new Loading(context);
    }

    public View setView(Vibrator vibrator) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);


        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.videoview, null);

        webview = view.findViewById(R.id.web_view);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            vibrator.vibrate(100);
            swipe = true;
            new Thread(refreshUrl).start();
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.progressColor);

        String userAgent = webview.getSettings().getUserAgentString();
        if (!TextUtils.isEmpty(userAgent)) {    //去除浮窗式廣告
            webview.getSettings().setUserAgentString(userAgent
                    .replace("Android", "")
                    .replace("android", "") + " cldc");
        }
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true); // 支持缩放
        // 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        //不显示webview缩放按钮
        webview.getSettings().setDisplayZoomControls(false);
        //自适应屏幕
        //noinspection deprecation
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setBlockNetworkImage(false);//不阻塞网络图片
        webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Log.e(TAG, "userAgent = " + userAgent);
                Log.e(TAG, "contentDisposition = " + contentDisposition);
                Log.e(TAG, "mimetype = " + mimetype);
                Log.e(TAG, "contentLength = " + contentLength);
                requeststorage(url);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重寫此方法表明點選網頁裡面的連結還是在當前的webview裡跳轉,不跳到瀏覽器那邊
                Log.e(TAG, "點選新連結");
                loading.show("載入中");
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e(TAG, "載入中");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                getActivity(context).setTitle(view.getTitle());
                super.onPageFinished(view, url);
                loading.dismiss();
                Log.e(TAG, "載入完畢");
            }
        });
        webview.setWebChromeClient(new WebChromeClient(){

            private View mCustomView;
            private WebChromeClient.CustomViewCallback mCustomViewCallback;
            private int mOriginalOrientation;
            private int mOriginalSystemUiVisibility;

            public Bitmap getDefaultVideoPoster() {
                if (mCustomView == null) {
                    return null;
                }
                return BitmapFactory.decodeResource(context.getApplicationContext().getResources(), 0);
            }

            public void onHideCustomView() {
                ((FrameLayout)getActivity(context).getWindow().getDecorView()).removeView(this.mCustomView);
                this.mCustomView = null;
                getActivity(context).getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
                getActivity(context).setRequestedOrientation(this.mOriginalOrientation);
                this.mCustomViewCallback.onCustomViewHidden();
                this.mCustomViewCallback = null;
            }

            public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
                if (this.mCustomView != null) {
                    onHideCustomView();
                    return;
                }
                this.mCustomView = paramView;
                this.mOriginalSystemUiVisibility = getActivity(context).getWindow().getDecorView().getSystemUiVisibility();
                this.mOriginalOrientation = getActivity(context).getRequestedOrientation();
                this.mCustomViewCallback = paramCustomViewCallback;
                ((FrameLayout)getActivity(context).getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
                getActivity(context).getWindow().getDecorView().setSystemUiVisibility(3846);
            }
        });
        webview.loadUrl("https://aniwantsmart.com/NSonline/OP/nsmv.php");

        return view;
    }

    private Runnable refreshUrl = new Runnable() {
        @Override
        public void run() {
            if (swipe) {
                swipe = false;
                refreshHandler.postDelayed(() -> {
                    webview.clearCache(true);
                    webview.reload();
                    swipeRefreshLayout.setRefreshing(false);
                }, 2000);
            }
        }
    };

    private static Activity getActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper) return getActivity(((ContextWrapper)context).getBaseContext());
        return null;
    }

    private void requeststorage(String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(context),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //未取得權限，向使用者要求允許權限
                ActivityCompat.requestPermissions(getActivity(context),
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_EXTERNAL_STORAGE);
            } else {
//                DownloaderTask task = new DownloaderTask(context);
//                task.execute(url);
                downloadManager(url);
                //已有權限，可進行工作
            }
        } else {
//            DownloaderTask task = new DownloaderTask(context);
//            task.execute(url);
            downloadManager(url);
        }
    }

    private void downloadManager(String url){
        Log.e(TAG, "url = " + url);
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        Log.e(TAG, "fileName = " + fileName);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(url));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        DownloadManager dm = (DownloadManager) getActivity(context).getSystemService(DOWNLOAD_SERVICE);
        if (dm != null) {
            dm.enqueue(request);
        }
        Toast.makeText(context, "開始下載",
                //To notify the Client that the file is being downloaded
                Toast.LENGTH_SHORT).show();
        // 使用
        DownloadCompleteReceiver receiver = new DownloadCompleteReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(receiver, intentFilter);
    }
}
