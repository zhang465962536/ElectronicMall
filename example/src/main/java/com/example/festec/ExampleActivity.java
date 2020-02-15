package com.example.festec;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.example.my.ec.launcher.LauncherDelegate;
import com.example.my.ec.launcher.LauncherScrollDelegate;
import com.example.my.ec.sign.SignInDelegate;
import com.example.my.ec.sign.SignUpDelegate;
import com.example.my_core.activities.ProxyActivity;
import com.example.my_core.delegates.LatteDelegate;


public  class ExampleActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide(); //隐藏标题栏
        }
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }
}
