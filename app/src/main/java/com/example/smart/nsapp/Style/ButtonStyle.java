package com.example.smart.nsapp.Style;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import androidx.core.content.ContextCompat;
import com.example.smart.nsapp.R;
import com.example.smart.nsapp.Fuction.Screen;

public class ButtonStyle {

    private String TAG = "ButtonStyle";

    public ButtonStyle(){
        super();
    }

    public void buttonstyle(Context context, Button btn){
        Screen screen = new Screen();
        DisplayMetrics dm = screen.size(context);
        int strokeWidth = 2;    //圓邊框寬度
        int roundRadius;    //圓半徑
        int strokeColor = ContextCompat.getColor(context, R.color.colormenu_btn);   //邊框顏色
        int fillColor = ContextCompat.getColor(context, R.color.colorBackground);   //內部填充顏色

        roundRadius = (dm.heightPixels / 4) / 4;

        /*if (dm.widthPixels > dm.heightPixels) {
            roundRadius = dm.heightPixels / 4;
        }
        else {
            roundRadius = dm.widthPixels / 4;
        }*/

        GradientDrawable gd = new GradientDrawable();   //創建drawable
        gd.setColor(fillColor);
        Log.e("buttonstyle","roundRadius = " + roundRadius);
        gd.setCornerRadius((float) roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setGradientCenter(0.5f,0.5f);
        //noinspection deprecation,ConstantConditions
        btn.setWidth(roundRadius);
        btn.setHeight(roundRadius);
        btn.setBackground(gd);
    }
}
