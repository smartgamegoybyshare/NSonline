package com.example.smart.nsapp.Activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.smart.nsapp.Fuction.DownloadCompleteReceiver;
import com.example.smart.nsapp.InstallAPK.DownloadStatus;
import com.example.smart.nsapp.InstallAPK.FinishListener;
import com.example.smart.nsapp.R;
import com.example.smart.nsapp.ViewPager.CombineView;
import com.example.smart.nsapp.ViewPager.SetPagerAdapter;
import com.example.smart.nsapp.ViewPager.SetViewPager;
import com.example.smart.nsapp.ViewPager.VideoView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirstActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FinishListener {

    private String TAG = "FirstActivity";
    private DownloadStatus downloadStatus = new DownloadStatus();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Menu menu;
    private boolean music = true;
    private NavigationView navigationView;
    private Vibrator vibrator;
    private List<View> list;
    private MediaPlayer mp = new MediaPlayer();
    private String[] tableList = {"官方網站", "合成製作"};
    private Handler checkHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隱藏狀態欄
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        list = new ArrayList<>();
        list.clear();
        startpage();
    }

    private void startpage() {
        setContentView(R.layout.activity_main);

        downloadStatus.setListener(this);
        new Thread(mpplayer).start();   //程式開啟即撥放背景音樂，縮放回來後亦同
        new Thread(versioncontrol).start();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        DrawerLayout(myToolbar);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);

        VideoView videoView = new VideoView(this);
        CombineView combineView = new CombineView(this);
        SetPagerAdapter setPagerAdapter = new SetPagerAdapter();
        List<View> viewList = new ArrayList<>();
        viewList.clear();

        tabLayout.addTab(tabLayout.newTab());
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText(tableList[0]);
        viewList.add(videoView.setView(vibrator));

        tabLayout.addTab(tabLayout.newTab());
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText(tableList[1]);
        viewList.add(combineView.setView(vibrator));

        /*for (int i = 1; i < tableList.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
            Objects.requireNonNull(tabLayout.getTabAt(i)).setText(tableList[i]);
            //viewList.add(setViewPager.setView(this, vibrator));
        }*/

        setPagerAdapter.setView(viewList);
        viewPager.setAdapter(setPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        /*final String CHANNEL_ID = "channel_id_1";
        final String CHANNEL_NAME = "channel_name_1";
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.notification_download);
        notificationManager.notify(1000, builder.build());
        Log.e(TAG,"已出現通知");*/
    }

    private Runnable versioncontrol = () -> {
        try {
            URL url = new URL("https://aniwantsmart.com/NSonline/version.json");
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setConnectTimeout(2000);
            InputStream uin = urlCon.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(uin));
            boolean more = true;
            StringBuilder line = new StringBuilder();
            for (; more; ) {
                String getline = in.readLine();
                Log.e(TAG, "getline = " + getline);
                if (getline != null) {
                    line.append(getline);
                } else {
                    more = false;
                }
            }
            Log.e(TAG, "line = " + line);
            JSONObject jsonObject = new JSONObject(line.toString());
            Log.e(TAG, "jsonObject = " + jsonObject);
            String thisversion = getVersionName(this);
            String version = jsonObject.getString("version");
            if (!version.matches(thisversion)) {
                checkHandler.post(() -> {
                    new AlertDialog.Builder(this)
                            .setTitle("女神App" + thisversion)
                            .setIcon(R.drawable.ns)
                            .setMessage("偵測到有新版本" + version + "\n現在要更新嗎?")
                            .setPositiveButton("確定", (dialog, which) -> {
                                requeststorage();
                            })
                            .setNegativeButton("取消", (dialog, which) -> {
                                // TODO Auto-generated method stub
                            }).show();
                });
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    };

    private void getNewVersion() {
        String url = "https://aniwantsmart.com/NSonline/NSonline.apk";
        downloadManager(url);
        /*DownloadCompleteReceiver receiver = new DownloadCompleteReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(receiver, intentFilter);*/
    }

    private void downloadManager(String url) {
        Log.e(TAG, "url = " + url);
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        Log.e(TAG, "fileName = " + fileName);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        DownloadManager dm = (DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);
        if (dm != null) {
            dm.enqueue(request);
        }
        Toast.makeText(this, "開始下載",
                //To notify the Client that the file is being downloaded
                Toast.LENGTH_SHORT).show();

        // 使用
        DownloadCompleteReceiver receiver = new DownloadCompleteReceiver();
        receiver.setListener(downloadStatus);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        this.registerReceiver(receiver, intentFilter);
    }


    private Runnable mpplayer = () -> {
        mp = MediaPlayer.create(FirstActivity.this, R.raw.homepage);
        mp.setLooping(true);
        mp.start();
    };

    public String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo.versionName;
    }

    private void requeststorage() {
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
                getNewVersion();
                //已有權限，可進行工作
            }
        } else {
            getNewVersion();
        }
    }

    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    private void DrawerLayout(Toolbar myToolbar) {
        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.homepage).setEnabled(false);
        SpannableString spanString1 = new SpannableString(navigationView.getMenu().
                findItem(R.id.homepage).getTitle().toString());
        spanString1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, spanString1.length(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requeststorage();
                } else {
                    finish();
                }
            }
            break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //toolbar menu item
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_music) {
            vibrator.vibrate(100);
            if (mp.isPlaying()) { //停止或撥放
                mp.pause();
                music = false;
                menu.findItem(R.id.action_music).setTitle(R.string.start);
            } else {
                mp.start();
                music = true;
                menu.findItem(R.id.action_music).setTitle(R.string.stop);
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.homepage) {
            vibrator.vibrate(100);
            return true;
        } else if (id == R.id.testpackages) {
            vibrator.vibrate(100);
            Intent intent = new Intent(this, TestPackage.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.buymycard) {
            vibrator.vibrate(100);
            String url = "https://www.mycard520.com/zh-tw/MicroPayment";
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra("title", menuItem.getTitle());
            intent.putExtra("url", url);
            startActivity(intent);
            return true;
        } else if (id == R.id.tobaha) {
            vibrator.vibrate(100);
            String url = "https://forum.gamer.com.tw/A.php?bsn=13013";
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra("title", menuItem.getTitle());
            intent.putExtra("url", url);
            startActivity(intent);
            return true;
        } else if (id == R.id.newNS) {
            vibrator.vibrate(100);
            String url = "https://nsc.chinesegamer.net";
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra("title", menuItem.getTitle());
            intent.putExtra("url", url);
            startActivity(intent);
            return true;
        } else if (id == R.id.viewpage) {
            vibrator.vibrate(100);
            String url = "https://aniwantsmart.com/NSonline/OP/nsmv.php";
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra("title", menuItem.getTitle());
            intent.putExtra("url", url);
            startActivity(intent);
            return true;
        } else if (id == R.id.nswiki) {
            vibrator.vibrate(100);
            String url = "https://nsura.wiki.fc2.com/";
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra("title", menuItem.getTitle());
            intent.putExtra("url", url);
            startActivity(intent);
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            // land do nothing is ok
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // port do nothing is ok
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        if (music)
            stopPlaying();  //離開此生命週期即停止撥放
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        if (music)
            if (!mp.isPlaying()) {   //如果音樂已暫停即繼續撥放
                mp.start();
            }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        if (music)
            if (mp.isPlaying()) { //縮小視窗即停止背景撥放
                mp.pause();
            }
    }

    public boolean onKeyDown(int key, KeyEvent event) {
        switch (key) {
            case KeyEvent.KEYCODE_SEARCH:
                break;
            case KeyEvent.KEYCODE_BACK: {
                vibrator.vibrate(100);
                new AlertDialog.Builder(this)
                        .setTitle("結束app")
                        .setMessage("確定離開?")
                        .setPositiveButton(R.string.butoon_yes, (dialog, which) -> {
                            vibrator.vibrate(100);
                            finish();
                        })
                        .setNeutralButton(R.string.butoon_no, (dialog, which) -> {
                        })
                        .show();
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
    public void isFinsish(String uri, String type) {
        Log.e(TAG, "回來了");
        try {
            Intent handlerIntent = new Intent();
            Log.e(TAG, "uri = " + uri);
            Log.e(TAG, "type = " + type);
            if (type.contains("application")) {
                File mFile = new File(Objects.requireNonNull(Uri.parse(uri).getPath()));
                String downloadFilePath = mFile.getAbsolutePath();
                Uri fileuri = Uri.fromFile(new File(downloadFilePath));
                handlerIntent.setAction(Intent.ACTION_VIEW);
                handlerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                handlerIntent.setDataAndType(fileuri, type);
            } else {
                Log.e(TAG, "自動開啟影音");
                /*File mFile = new File(Objects.requireNonNull(Uri.parse(uri).getPath()));
                String downloadFilePath = mFile.getAbsolutePath();
                Uri fileuri = Uri.parse(downloadFilePath);
                handlerIntent.setAction(Intent.ACTION_VIEW);
                handlerIntent.setDataAndType(fileuri, type);*/
            }
            startActivity(handlerIntent);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "e = " + e);
        }
    }
}
