package com.example.smart.nsapp.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.smart.nsapp.Fuction.RandomNumber;
import com.example.smart.nsapp.R;

public class AbilityArm {

    private String TAG = "AbilityArm";
    private MediaPlayer mp = new MediaPlayer();

    public AbilityArm(){
        super();
    }

    public String getMessage(Context context) {
        mp = MediaPlayer.create(context, R.raw.winner);
        String message = "";
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 60) {    //1~59%
            String[] boxArr = {"巨紅魔瓶 X 1", "巨藍魔瓶 X 1", "熟練值記憶卡 X 1", "經驗分享記憶卡 X 1",
                    "記憶卡增幅器 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 100) {   //60~99%
            String[] boxArr = {"50%經驗藥水 X 1", "提神飲料 X 2", "愛心 X 1", "大復活丸 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            String[] boxArr = {"藍霞之環 X 1", "強襲之環 X 1", "炙晶之環 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            mp.start();
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
}
