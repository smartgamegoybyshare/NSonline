package com.example.smart.nsapp.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.*;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.smart.nsapp.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirstActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "FirstActivity";
    private Menu menu;
    private boolean music = true;
    private NavigationView navigationView;
    private Vibrator vibrator;
    private List<View> list;
    private MediaPlayer mp = new MediaPlayer();
    private String[] tableList = {"影片欣賞"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        list = new ArrayList<>();
        list.clear();
        new Thread(mpplayer).start();   //程式開啟即撥放背景音樂，縮放回來後亦同
        startpage();
    }

    private void startpage() {
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        DrawerLayout(myToolbar);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);

        for(int i = 0; i < tableList.length; i++){
            tabLayout.addTab(tabLayout.newTab());
            Objects.requireNonNull(tabLayout.getTabAt(i)).setText(tableList[i]);
            //list.add(nameView.setView(this, nameList, vibrator));
        }
    }

    private Runnable mpplayer = () -> {
        mp = MediaPlayer.create(FirstActivity.this, R.raw.homepage);
        mp.setLooping(true);
        mp.start();
    };

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
            Uri uri = Uri.parse("https://www.mycard520.com/zh-tw/MicroPayment");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        } else if (id == R.id.tobaha) {
            vibrator.vibrate(100);
            Uri uri = Uri.parse("https://forum.gamer.com.tw/A.php?bsn=13013");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        } else if (id == R.id.bugpage) {
            vibrator.vibrate(100);
            Uri uri = Uri.parse("https://ns.chinesegamer.net/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        } else if (id == R.id.nswiki) {
            vibrator.vibrate(100);
            Uri uri = Uri.parse("https://nsura.wiki.fc2.com/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
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
    public void onConfigurationChanged(Configuration newConfig) {
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
}
