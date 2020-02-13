package com.example.festec;

import android.app.Application;

import com.example.my.ec.icon.FontEcModule;
import com.example.my_core.app.Latte;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.IError;
import com.example.my_core.net.callback.IFailure;
import com.example.my_core.net.callback.ISuecess;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1/")
                .configure();
    }


}
