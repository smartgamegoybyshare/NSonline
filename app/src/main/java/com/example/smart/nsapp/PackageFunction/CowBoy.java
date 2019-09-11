package com.example.smart.nsapp.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.smart.nsapp.Fuction.RandomNumber;
import com.example.smart.nsapp.R;

public class CowBoy {

    private String TAG = "Rock";
    private Context context;
    private Vibrator vibrator;

    public CowBoy() {
        super();
    }

    public String getMessage(Context context, Vibrator vibrator) {
        this.context = context;
        this.vibrator = vibrator;

        String message;
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 71) {    //1~70%
            String[] boxArr = {"巨紅魔瓶隨身包 X 1", "巨藍魔瓶隨身包 X 1", "50%經驗藥水 X 1", "復活丸 X 1", "60%經驗藥水 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 96) {   //71~95%
            String[] boxArr = {"70%經驗藥水 X 1", "回復卷軸 X 1", "大復活丸 X 1", "防壞裝木偶 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            new Thread(playMP).start();
            if (i > 30) { //30~100%
                String[] boxArr = {"獸寵馬鐙 X 1", "獸寵馬刺 X 1", "獸寵胸飾 X 1", "獸寵韁繩 X 1"};
                int sum = i % boxArr.length;
                message = boxArr[sum];
            } else if (i < 16) {    //1~15%
                String[] boxArr = {"獸寵面罩 X 1"};
                message = boxArr[0];
            } else {    //16~30%
                String[] boxArr = {"獸寵戰輪 X 1"};
                message = boxArr[0];
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
