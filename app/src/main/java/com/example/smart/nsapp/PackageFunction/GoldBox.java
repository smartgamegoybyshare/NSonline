package com.example.smart.nsapp.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.smart.nsapp.Fuction.RandomNumber;
import com.example.smart.nsapp.R;

public class GoldBox {

    private String TAG = "GoldBox";
    private Context context;
    private Vibrator vibrator;

    public GoldBox() {
        super();
    }

    public String getMessage(Context context, Vibrator vibrator) {
        this.context = context;
        this.vibrator = vibrator;

        String message;
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 79) {    //1~78%
            String[] boxArr = {"特級鮮乳 X 2", "黃金鑰匙 X 2", "熟練度記憶卡 X 1", "巨紅魔瓶 X 1", "巨藍魔瓶 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 99) {
            String[] boxArr = {"經驗分享記憶卡 X 1", "提神飲料 X 1", "愛心 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            String[] boxArr = {"戰術裝備盒 X 1", "黃金裝備盒 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            if (i < 91) { //1~90%
                message = boxArr[0];
            } else{
                new Thread(playMP).start();
                message = boxArr[1];
            }
        }

        return message;
    }

    private Runnable playMP = new Runnable() {
        @Override
        public void run() {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.winner);
            mp.start();
            vibrator.vibrate(100);
        }
    };
}
