package com.example.smart.nsapp.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.smart.nsapp.Fuction.RandomNumber;
import com.example.smart.nsapp.R;

public class Rock {

    private String TAG = "Rock";
    private MediaPlayer mp = new MediaPlayer();

    public Rock() {
        super();
    }

    public String getMessage(Context context) {
        mp = MediaPlayer.create(context, R.raw.winner);
        String message = "";
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 71) {    //1~70%
            String[] boxArr = {"輕便魔瓶組 X 1", "熟練值記憶卡 X 2", "經驗分享記憶卡 X 1", "記憶卡增幅器 X 2"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 87) {   //71~86%
            String[] boxArr = {"戰果錦囊 X 1", "女神祝福丸 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            String[] boxArr = {"火琉璃 X 1", "水琉璃 X 1"};
            mp.start();
            if (i < 51) { //1~50%
                message = boxArr[0];
            } else {  //51~100%
                message = boxArr[1];
            }
        }

        return message;
    }
}
