package com.example.festec;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.example.my.ec.launcher.LauncherDelegate;
import com.example.my.ec.launcher.LauncherScrollDelegate;
import com.example.my.ec.main.EcBottomDelegate;
import com.example.my.ec.sign.SignInDelegate;
import com.example.my.ec.sign.SignUpDelegate;
import com.example.my_core.activities.ProxyActivity;
import com.example.my_core.app.Latte;
import com.example.my_core.delegates.LatteDelegate;

import cn.jpush.android.api.JPushInterface;
import qiu.niorgai.StatusBarCompat;


public  class ExampleActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide(); //隐藏标题栏
        }
        //将当前Activity 传入 Configurator 配置文件保存
        Latte.getConfigurator().withActivity(this);
        //沉浸式状态栏
        StatusBarCompat.translucentStatusBar(this,true);
    }

    //极光推送 绑定消息
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new EcBottomDelegate();
    }
}
