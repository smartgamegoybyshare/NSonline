package com.example.smart.nsonline.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.smart.nsonline.Fuction.RandomNumber;
import com.example.smart.nsonline.R;

public class NewWeapon {

    private String TAG = "NewWeapon";
    private MediaPlayer mp = new MediaPlayer();

    public NewWeapon() {
        super();
    }

    public String getMessage(Context context) {
        mp = MediaPlayer.create(context, R.raw.winner);
        String message = "";
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 71) {    //1~70%
            String[] boxArr = {"巨紅魔瓶隨身包 X 1", "巨藍魔瓶隨身包 X 1", "熟練度記憶卡 X 3", "經驗分享記憶卡 X 2"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 99) {   //71~98%
            String[] boxArr = {"黃金鑰匙 X 6", "寵伴訓練券 X 3", "100%經驗藥水 X 1", "120%經驗藥水 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            String[] boxArr = {"真˙炙焰軍刀 X 1", "真˙輝紫狙擊槍 X 1", "真˙澤金之杖 X 1", "真˙粉紅愛心杖 X 1",
                    "真˙闇月忍刀 X 1", "真˙凜白軍刀 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            mp.start();
            if (i < 31) { //1~30%
                message = boxArr[1];
            } else if (i < 61) {    //31~60%
                message = boxArr[3];
            } else if (i < 76) {   //61~75%
                message = boxArr[2];
            } else if (i < 91) {   //76~90%
                message = boxArr[4];
            } else if (i < 96) {   //91~95%
                message = boxArr[0];
            } else {
                message = boxArr[5];
            }
        }

        return message;
    }
}
