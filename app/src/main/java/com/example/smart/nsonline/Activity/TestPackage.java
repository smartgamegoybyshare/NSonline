package com.example.smart.nsonline.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.smart.nsonline.PackageFunction.Package;
import com.example.smart.nsonline.R;
import com.example.smart.nsonline.Style.ButtonStyle;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TestPackage extends AppCompatActivity {

    private String TAG = "TestPackage";
    private MediaPlayer mp = new MediaPlayer();
    private ButtonStyle buttonStyle = new ButtonStyle();
    private Package aPackage = new Package();
    private Vibrator vibrator;
    private ListView listView1, listView2;
    private ArrayAdapter<String> listAdapter, listAdapter2;
    private String[] spinArr = {"裝備盒福袋", "符紋福袋", "機會福袋", "真˙武器福袋", "新制服套裝福袋",
            "能力臂環福袋", "戀人福袋", "天使福袋", "獸寵胸針福袋", "進階馬鞍福袋"};
    private Integer[] costArr = {6, 10, 14, 16, 16, 7, 9, 18, 9, 18};
    private String getmessage;
    private int count, chose;
    private List<String> alllist;
    private List<Integer> countList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        /*MediaPlayer mp = MediaPlayer.create(this, R.raw.playing);
        mp.setLooping(true);
        mp.start();*/
        alllist = new ArrayList<>();
        countList = new ArrayList<>();
        alllist.clear();
        countList.clear();
        startpage();
    }

    private void startpage() {
        setContentView(R.layout.showpackage);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        listView1 = findViewById(R.id.listview1);
        listView2 = findViewById(R.id.listMessage);
        Spinner spinner = findViewById(R.id.spinner);
        Button b1 = findViewById(R.id.sendbutton);
        Button b2 = findViewById(R.id.clearbutton);

        count = 1;

        buttonStyle.buttonstyle(this, b1);
        buttonStyle.buttonstyle(this, b2);

        listAdapter = new ArrayAdapter<>(this, R.layout.message_detail);
        listView1.setAdapter(listAdapter);
        listView1.setDivider(null);

        listAdapter2 = new ArrayAdapter<>(this, R.layout.message_detail2);
        listView2.setAdapter(listAdapter2);
        listView2.setDivider(null);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_style, spinArr) {    //android.R.layout.simple_spinner_item
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_style);    //R.layout.spinner_style
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chose = position;
                getmessage = spinArr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        b1.setOnClickListener(v -> {
            vibrator.vibrate(100);
            listAdapter2.clear();
            listAdapter2.notifyDataSetChanged();
            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
            String message = aPackage.getMessage(this, getmessage);
            listAdapter.add("[" + currentDateTimeString + "] : " + "恭喜獲得 : " + message);
            listView1.smoothScrollToPosition(listAdapter.getCount() - 1);
            listAdapter2.add("");
            listAdapter2.add("總共抽了" + count + "包");
            listAdapter2.add("總共花了" + costArr[chose] * count + "點");
            String[] arr = message.split("\\s");
            if (alllist.size() == 0) {
                int i = 1;
                alllist.add(arr[0]);
                countList.add(i);
            } else {
                if (alllist.indexOf(arr[0]) == -1) {
                    int i = 1;
                    alllist.add(arr[0]);
                    countList.add(i);
                } else {
                    int i = alllist.indexOf(arr[0]);
                    int getcount = countList.get(i);
                    countList.set(i, (getcount + 1));
                }
            }
            for (int i = 0; i < alllist.size(); i++) {
                listAdapter2.add("抽中 : " + alllist.get(i) + countList.get(i) + "次");
            }
            listView2.smoothScrollToPosition(listAdapter2.getCount() - 1);
            count++;
        });

        b2.setOnClickListener(v -> {
            vibrator.vibrate(100);
            count = 1;
            countList.clear();
            alllist.clear();
            listAdapter.clear();
            listAdapter.notifyDataSetChanged();
            listAdapter2.clear();
            listAdapter2.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //toolbar menu item
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.backpage) {
            vibrator.vibrate(100);
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return true;
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public boolean onKeyDown(int key, KeyEvent event) {
        switch (key) {
            case KeyEvent.KEYCODE_SEARCH:
                break;
            case KeyEvent.KEYCODE_BACK: {
                vibrator.vibrate(100);
                /*try {
                    if (mp != null && mp.isPlaying()) {
                        mp.pause();
                        mp.stop();
                        mp.reset();
                        mp.release();
                        mp = null;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "問題在哪?" + e);
                    e.printStackTrace();
                }*/
                Intent intent = new Intent(this, FirstActivity.class);
                startActivity(intent);
                finish();
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
