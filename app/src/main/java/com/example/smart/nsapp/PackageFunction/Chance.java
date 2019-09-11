package com.example.smart.nsapp.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.smart.nsapp.Fuction.RandomNumber;
import com.example.smart.nsapp.R;

public class Chance {

    private String TAG = "Chance";
    private Context context;
    private Vibrator vibrator;

    public Chance() {
        super();
    }

    public String getMessage(Context context, Vibrator vibrator) {
        this.context = context;
        this.vibrator = vibrator;

        String message;
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 86) {    //1~85%
            String[] boxArr = {"輕便魔瓶組 X 1", "熟練值記憶卡 X 2", "經驗分享記憶卡 X 2",
                    "記憶卡增幅器 X 3", "提神飲料 X 3", "寵伴訓練券 X 2"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 96) {   //86~95%
            String[] boxArr = {"裝備數值重置器 X 1", "倍增安定丸 X 1", "夥伴遺忘卷1張 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            String[] boxArr = {"夥伴遺忘卷2張 X 1", "夥伴遺忘卷3張 X 1", "夥伴遺忘卷8張 X 1", "夥伴遺忘卷15張 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            if (i < 41) { //1~40%
                message = boxArr[0];
            } else if (i < 71) {  //41~70%
                message = boxArr[1];
            } else if (i < 91) {  //71~90%
                new Thread(playMP).start();
                message = boxArr[2];
            } else {
                new Thread(playMP).start();
                message = boxArr[3];
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
