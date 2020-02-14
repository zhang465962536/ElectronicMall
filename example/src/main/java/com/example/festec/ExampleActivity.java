package com.example.festec;


import com.example.my.ec.launcher.LauncherDelegate;
import com.example.my_core.activities.ProxyActivity;
import com.example.my_core.delegates.LatteDelegate;


public  class ExampleActivity extends ProxyActivity {


    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }
}
