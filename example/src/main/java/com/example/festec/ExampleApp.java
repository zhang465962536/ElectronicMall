package com.example.festec;

import android.app.Application;

import com.example.my.ec.icon.FontEcModule;
import com.example.my_core.app.Latte;
import com.example.festec.event.TestEvent;
import com.example.my_core.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
//可变的配置 全部放在这里一次性配置完毕
public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1/")
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .withJavascriptInterface("latte")
                .withWebEvent("test",new TestEvent())
                .configure();
    }


}
