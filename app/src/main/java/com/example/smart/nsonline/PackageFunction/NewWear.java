package com.example.smart.nsonline.PackageFunction;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.smart.nsonline.Fuction.RandomNumber;
import com.example.smart.nsonline.R;

public class NewWear {

    private String TAG = "NewWear";
    private MediaPlayer mp = new MediaPlayer();

    public NewWear() {
        super();
    }

    public String getMessage(Context context) {
        mp = MediaPlayer.create(context, R.raw.winner);
        String message = "";
        int random;
        RandomNumber randomNumber = new RandomNumber();
        random = randomNumber.getNumber();

        if (random < 81) {    //1~80%
            String[] boxArr = {"巨紅魔瓶隨身包 X 1", "巨藍魔瓶隨身包 X 1", "熟練度記憶卡 X 3", "經驗分享記憶卡 X 2",
                    "黃金鑰匙 X 6", "寵伴訓練券 X 3"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else if (random < 96) {   //81~95%
            String[] boxArr = {"100%經驗藥水 X 1", "120%經驗藥水 X 1"};
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            int sum = i % boxArr.length;
            message = boxArr[sum];
        } else {
            RandomNumber randomNumber2 = new RandomNumber();
            int i = randomNumber2.getNumber();
            mp.start();
            if (i < 51) { //1~30%
                String[] boxArr = {"真˙炙焰軍鞋 X 1", "真˙輝紫軍鞋 X 1", "真˙澤金軍鞋 X 1", "真˙粉紅軍鞋 X 1",
                        "真˙闇月軍鞋 X 1", "真˙凜白軍鞋 X 1"};
                RandomNumber randomNumber3 = new RandomNumber();
                int j = randomNumber3.getNumber();
                if(j < 31){
                    message = boxArr[3];
                }else if(j < 61){
                    message = boxArr[1];
                }else if(j < 76){
                    message = boxArr[2];
                }else if(j < 91){
                    message = boxArr[4];
                }else if(j < 96){
                    message = boxArr[0];
                }else {
                    message = boxArr[5];
                }
            } else if (i < 81) {    //31~60%
                String[] boxArr = {"真˙炙焰手套 X 1", "真˙輝紫手套 X 1", "真˙澤金手套 X 1", "真˙粉紅手套 X 1",
                        "真˙闇月手套 X 1", "真˙凜白手套 X 1"};
                RandomNumber randomNumber3 = new RandomNumber();
                int j = randomNumber3.getNumber();
                if(j < 31){
                    message = boxArr[3];
                }else if(j < 61){
                    message = boxArr[1];
                }else if(j < 76){
                    message = boxArr[2];
                }else if(j < 91){
                    message = boxArr[4];
                }else if(j < 96){
                    message = boxArr[0];
                }else {
                    message = boxArr[5];
                }
            } else {
                String[] boxArr = {"真˙炙焰軍服 X 1", "真˙輝紫軍服 X 1", "真˙澤金軍服 X 1", "真˙粉紅軍服 X 1",
                        "真˙闇月軍服 X 1", "真˙凜白軍服 X 1"};
                RandomNumber randomNumber3 = new RandomNumber();
                int j = randomNumber3.getNumber();
                if(j < 31){
                    message = boxArr[3];
                }else if(j < 61){
                    message = boxArr[1];
                }else if(j < 76){
                    message = boxArr[2];
                }else if(j < 91){
                    message = boxArr[4];
                }else if(j < 96){
                    message = boxArr[0];
                }else {
                    message = boxArr[5];
                }
            }
        }

        return message;
    }
}
