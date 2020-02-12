package com.example.my_core.app;

import android.content.Context;

import java.util.HashMap;
import java.util.WeakHashMap;

//对外的工具类  里面的方法都是 静态的
public  final class Latte {

    //获取Application里面的Context
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
