package com.example.my_core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.my_core.app.Latte;

//测量工具类
public class DimenUtil {

    //获取屏幕的宽
    public static int getScreenWidth(){
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    //获取屏幕的高
    public static int getScreenHeight(){
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}
