package com.example.smart.nsapp.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.smart.nsapp.Fuction.Loading;
import com.example.smart.nsapp.R;

public class WebviewActivity extends AppCompatActivity {

    private String TAG = "WebviewActivity";
    private TextView title, back;
    private LinearLayout backgroundlinear;
    private Vibrator vibrator;
    private String title_text, geturl;
    private WebView webview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean swipe = false;
    private Handler refreshHandler = new Handler();
    private Loading loading = new Loading(this);
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        Log.d(TAG, "onCreate");
        //隱藏標題欄
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.show("載入中");

        get_Intent();
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

    private void get_Intent() {
        Intent intent = getIntent();
        title_text = intent.getStringExtra("title");
        geturl = intent.getStringExtra("url");
        Log.e(TAG, "title = " + title_text);
        Log.e(TAG, "geturl = " + geturl);
        watchView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void watchView() {
        setContentView(R.layout.watchweb);

        backgroundlinear = findViewById(R.id.backgroundlinear);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        title = findViewById(R.id.textView);   //對帳通知
        back = findViewById(R.id.textView1);   //返回

        title.setText(title_text);
        back.setOnClickListener(view -> {
            vibrator.vibrate(100);
            homePage();
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            vibrator.vibrate(100);
            swipe = true;
            new Thread(refreshUrl).start();
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.progressColor);

        webview = findViewById(R.id.web_view);
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
                setTitle(view.getTitle());
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
                return BitmapFactory.decodeResource(getApplicationContext().getResources(), 0);
            }

            public void onHideCustomView() {
                ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
                this.mCustomView = null;
                getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
                setRequestedOrientation(this.mOriginalOrientation);
                this.mCustomViewCallback.onCustomViewHidden();
                this.mCustomViewCallback = null;
            }

            public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
                if (this.mCustomView != null) {
                    onHideCustomView();
                    return;
                }
                this.mCustomView = paramView;
                this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                this.mOriginalOrientation = getRequestedOrientation();
                this.mCustomViewCallback = paramCustomViewCallback;
                ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
                getWindow().getDecorView().setSystemUiVisibility(3846);
            }
        });
        webview.loadUrl(geturl);
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
        request.setTitle("下载");
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        if (dm != null) {
            dm.enqueue(request);
        }
        Toast.makeText(getApplicationContext(), "即將開始下載",
                //To notify the Client that the file is being downloaded
                Toast.LENGTH_LONG).show();

    }

    private void requeststorage(String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //未取得權限，向使用者要求允許權限
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_EXTERNAL_STORAGE);
            } else {
                downloadManager(url);
                //已有權限，可進行工作
            }
        } else {
            downloadManager(url);
        }
    }

    private void homePage() {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int key, KeyEvent event) {
        switch (key) {
            case KeyEvent.KEYCODE_SEARCH:
                break;
            case KeyEvent.KEYCODE_BACK: {
                vibrator.vibrate(100);
                if (webview.canGoBack()) {
                    webview.goBack();
                } else {
                    homePage();
                }
            }
            break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
            default:
                return false;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        webview.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration  newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // land do nothing is ok
            title.setVisibility(View.GONE);
            back.setVisibility(View.GONE);
            backgroundlinear.setVisibility(View.GONE);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // port do nothing is ok
            title.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            backgroundlinear.setVisibility(View.VISIBLE);
        }
    }
}
