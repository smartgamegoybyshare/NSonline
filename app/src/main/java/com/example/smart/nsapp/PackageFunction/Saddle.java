package com.example.smart.nsapp.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.smart.nsapp.Fuction.RandomNumber;
import com.example.smart.nsapp.R;

public class Saddle {

    private String TAG = "Saddle";
    private Context context;
    private Vibrator vibrator;

    public Saddle() {
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
            String[] boxArr = {"巨紅魔瓶隨身包 X 2", "巨藍魔瓶隨身包 X 2", "60%經驗藥水 X 1", "70%經驗藥水 X 1", "80%經驗藥水 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 76) {   //56~75%
            String[] boxArr = {"裝備強化丸 X 2", "愛心 X 2", "大復活丸 X 2"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            if (i < 51) { //1~60%
                String[] boxArr = {"進階術防馬鞍 X 1", "進階防禦馬鞍 X 1", "進階生命馬鞍 X 1"};
                RandomNumber randomNumber3 = new RandomNumber();
                int j = randomNumber3.getNumber();
                int sum = j % boxArr.length;
                message = boxArr[sum];
            } else if (i < 81) {  //61~90%
                new Thread(playMP).start();
                String[] boxArr = {"進階敏捷馬鞍 X 1"};
                message = boxArr[0];
            } else {
                new Thread(playMP).start();
                String[] boxArr = {"進階睿智馬鞍 X 1", "進階猛擊馬鞍 X 1"};
                RandomNumber randomNumber3 = new RandomNumber();
                int j = randomNumber3.getNumber();
                if (j < 71) { //1~70%
                    message = boxArr[0];
                } else {
                    message = boxArr[1];
                }
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
