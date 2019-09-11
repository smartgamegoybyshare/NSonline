package com.example.smart.nsapp.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.smart.nsapp.Fuction.RandomNumber;
import com.example.smart.nsapp.R;

public class Monster {

    private String TAG = "Monster";
    private Context context;
    private Vibrator vibrator;

    public Monster() {
        super();
    }

    public String getMessage(Context context, Vibrator vibrator) {
        this.context = context;
        this.vibrator = vibrator;

        String message;
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 56) {    //1~55%
            String[] boxArr = {"巨紅魔瓶隨身包 X 1", "巨藍魔瓶隨身包 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 81) {   //56~80%
            String[] boxArr = {"50%經驗藥水 X 1", "60%經驗藥水 X 1", "70%經驗藥水 X 1", "80%經驗藥水 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 100) {   //81~99%
            String[] boxArr = {"大復活丸 X 1", "愛心 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            String[] boxArr = {"獸寵胸針 X 1", "黃金獸寵胸針 X 1", "合金獸寵胸針 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            new Thread(playMP).start();
            if (i < 61) { //1~60%
                message = boxArr[0];
            } else if (i < 91) {  //61~90%
                message = boxArr[1];
            } else {
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
