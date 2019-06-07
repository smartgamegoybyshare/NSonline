package com.example.smart.nsapp.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.smart.nsapp.Fuction.RandomNumber;
import com.example.smart.nsapp.R;

public class CowBoy {

    private String TAG = "Rock";
    private MediaPlayer mp = new MediaPlayer();

    public CowBoy() {
        super();
    }

    public String getMessage(Context context) {
        mp = MediaPlayer.create(context, R.raw.winner);
        String message = "";
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 71) {    //1~70%
            String[] boxArr = {"巨紅魔瓶隨身包 X 1", "巨藍魔瓶隨身包 X 1", "50%經驗藥水 X 1", "復活丸 X 1", "60%經驗藥水 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 99) {   //71~98%
            String[] boxArr = {"70%經驗藥水 X 1", "回復卷軸 X 1", "大復活丸 X 1", "防壞裝木偶 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            mp.start();
            if (i < 47) { //1~46%
                String[] boxArr = {"獸寵馬鐙 X 1", "獸寵馬刺 X 1"};
                int sum = i % boxArr.length;
                message = boxArr[sum];
            } else if (i < 81) {  //47~80%
                String[] boxArr = {"獸寵胸飾 X 1", "獸寵韁繩 X 1"};
                RandomNumber randomNumber3 = new RandomNumber();
                int j = randomNumber3.getNumber();
                int sum = j % boxArr.length;
                message = boxArr[sum];
            } else if (i < 91) {    //81~90%
                String[] boxArr = {"獸寵面罩 X 1"};
                message = boxArr[0];
            } else {    //91~100%
                String[] boxArr = {"獸寵戰輪 X 1"};
                message = boxArr[0];
            }
        }

        return message;
    }
}
