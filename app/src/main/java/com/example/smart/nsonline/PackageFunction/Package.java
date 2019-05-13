package com.example.smart.nsonline.PackageFunction;

import android.content.Context;

public class Package {

    public Package() {
        super();
    }

    public String getMessage(Context context, String str) {
        String message = "";

        if (str.matches("裝備盒福袋")) {
            GoldBox goldBox = new GoldBox();
            message = goldBox.getMessage(context);
        } else if (str.matches("符紋福袋")) {
            Tattoo tattoo = new Tattoo();
            message = tattoo.getMessage(context);
        } else if (str.matches("機會福袋")) {
            Chance chance = new Chance();
            message = chance.getMessage(context);
        } else if (str.matches("真˙武器福袋")) {
            NewWeapon newWeapon = new NewWeapon();
            message = newWeapon.getMessage(context);
        } else if (str.matches("新制服套裝福袋")) {
            NewWear newWear = new NewWear();
            message = newWear.getMessage(context);
        } else if (str.matches("能力臂環福袋")) {
            AbilityArm abilityArm = new AbilityArm();
            message = abilityArm.getMessage(context);
        } else if (str.matches("戀人福袋")) {
            Lover lover = new Lover();
            message = lover.getMessage(context);
        } else if (str.matches("天使福袋")) {
            AngelWing angelWing = new AngelWing();
            message = angelWing.getMessage(context);
        } else if (str.matches("獸寵胸針福袋")) {
            Monster monster = new Monster();
            message = monster.getMessage(context);
        } else if (str.matches("進階馬鞍福袋")) {
            Saddle saddle = new Saddle();
            message = saddle.getMessage(context);
        }

        return message;
    }
}
