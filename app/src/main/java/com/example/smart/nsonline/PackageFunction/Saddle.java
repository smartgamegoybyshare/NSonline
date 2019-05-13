package com.example.smart.nsonline.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.smart.nsonline.Fuction.RandomNumber;
import com.example.smart.nsonline.R;

public class Saddle {

    private String TAG = "Saddle";
    private MediaPlayer mp = new MediaPlayer();

    public Saddle() {
        super();
    }

    public String getMessage(Context context) {
        mp = MediaPlayer.create(context, R.raw.winner);
        String message = "";
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
        } else if (random < 100) {   //81~99%
            String[] boxArr = {"大復活丸 X 1", "愛心 X 1"};
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
                mp.start();
                String[] boxArr = {"進階敏捷馬鞍 X 1"};
                message = boxArr[0];
            } else {
                mp.start();
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
}
