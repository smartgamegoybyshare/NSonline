package com.example.smart.nsonline.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.smart.nsonline.Fuction.RandomNumber;
import com.example.smart.nsonline.R;

public class AngelWing {

    private String TAG = "AngelWing";
    private MediaPlayer mp = new MediaPlayer();

    public AngelWing(){
        super();
    }

    public String getMessage(Context context) {
        mp = MediaPlayer.create(context, R.raw.winner);
        String message = "";
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 85) {    //1~84%
            String[] boxArr = {"巨紅魔瓶隨身包 X 1", "巨藍魔瓶隨身包 X 1", "復活丸 X 2", "回復卷軸 X 3",
                    "愛心 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 100) {   //85~99%
            String[] boxArr = {"天界繩鞋 X 1", "仙境霓裳 X 1", "天使之光 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            String[] boxArr = {"天怒羽翼 X 1", "聖潔羽翼 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            mp.start();
            if (i < 51) { //1~50%
                message = boxArr[0];
            } else {
                message = boxArr[1];
            }
        }

        return message;
    }
}
