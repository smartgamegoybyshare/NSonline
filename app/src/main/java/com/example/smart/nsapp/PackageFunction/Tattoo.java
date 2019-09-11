package com.example.smart.nsapp.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.smart.nsapp.Fuction.RandomNumber;
import com.example.smart.nsapp.R;

public class Tattoo {

    private String TAG = "Tattoo";
    private Context context;
    private Vibrator vibrator;

    public Tattoo() {
        super();
    }

    public String getMessage(Context context, Vibrator vibrator) {
        this.context = context;
        this.vibrator = vibrator;

        String message;
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 69) {    //1~68%
            String[] boxArr = {"巨紅魔瓶隨身包 X 1", "巨藍魔瓶隨身包 X 1", "熟練度記憶卡 X 2",
                    "經驗分享記憶卡 X 1", "記憶卡增幅器 X 2"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 95) {   //69%-94%
            String[] boxArr = {"70%經驗藥水 X 1", "80%經驗藥水 X 1", "100%經驗藥水 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 100) {    //96-
            String[] boxArr = {"愛心 X 1", "大復活丸 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            String[] boxArr = {"瞬之符紋 X 1", "法之符紋 X 1", "戰之符紋 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            new Thread(playMP).start();
            if (i < 61) { //1~60%
                message = boxArr[0];
            } else if (i < 81) {    //61~80%
                message = boxArr[1];
            }else { //81~100%
                message = boxArr[2];
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
