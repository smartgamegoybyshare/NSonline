package com.example.smart.nsapp.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smart.nsapp.R;
import pl.droidsonroids.gif.GifImageView;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";
    private Vibrator vibrator;
    private MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        //隱藏標題欄
        supportRequestWindowFeature( Window.FEATURE_NO_TITLE);
        //隱藏狀態欄
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隱藏底部HOME工具列
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.startview);

        GifImageView gifImageView = findViewById(R.id.activity_gif_giv);

        gifImageView.setBackgroundResource(R.drawable.loading);

        MediaPlayer mp = MediaPlayer.create(this, R.raw.loading);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        int SPLASH_DISPLAY_LENGHT = 4500;
        mp.start();

        new android.os.Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(StartActivity.this, FirstActivity.class);
            StartActivity.this.startActivity(mainIntent);
            StartActivity.this.finish();
        }, SPLASH_DISPLAY_LENGHT);
    }

    public boolean onKeyDown(int key, KeyEvent event) {
        switch (key) {
            case KeyEvent.KEYCODE_SEARCH:
                break;
            case KeyEvent.KEYCODE_BACK: {
                vibrator.vibrate(100);
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
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        mp.stop();
        mp.release();
    }
}