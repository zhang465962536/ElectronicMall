package com.example.my_core.util.log;

import android.widget.Toast;

import com.example.my_core.app.Configurator;
import com.example.my_core.app.Latte;

public class ToastUtil {

    public static void QuickToast(String content){
        Toast.makeText(Latte.getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }
}
