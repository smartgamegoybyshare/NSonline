package com.example.smart.nsonline.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.smart.nsonline.Fuction.RandomNumber;
import com.example.smart.nsonline.R;

public class Tattoo {

    private String TAG = "Tattoo";
    private MediaPlayer mp = new MediaPlayer();

    public Tattoo() {
        super();
    }

    public String getMessage(Context context) {
        mp = MediaPlayer.create(context, R.raw.winner);
        String message = "";
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
            mp.start();
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
}
