package com.example.festec;

import android.app.Application;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.example.my.ec.icon.FontEcModule;
import com.example.my_core.app.Latte;
import com.example.festec.event.TestEvent;
import com.example.my_core.net.interceptors.DebugInterceptor;
import com.example.my_core.net.rx.AddCookieInterceptor;
import com.example.my_core.util.callback.CallBackManager;
import com.example.my_core.util.callback.CallBackType;
import com.example.my_core.util.callback.IGlobalCallBack;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import cn.jpush.android.api.JPushInterface;

//可变的配置 全部放在这里一次性配置完毕
public class ExampleApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1/")
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .withJavascriptInterface("latte")
                .withWebEvent("test", new TestEvent())
                //浏览器配置的Host
                .withWebHost("https://www.baidu.com/")
                //添加cookie同步拦截器
                .withInterceptor(new AddCookieInterceptor())
                .configure();
        MultiDex.install(this);
        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //使用接口实现依赖倒转原则   ec库 依赖 example Module
        CallBackManager.getInstance()
                .addCallback(CallBackType.TAG_OPEN_PUSH, new IGlobalCallBack() {
                    @Override
                    public void executeCallBack(Object args) {
                            //如果极光推送停止了
                        if(JPushInterface.isPushStopped(Latte.getApplicationContext())){
                            //重新初始化 开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallBackType.TAG_STOP_PUSH, new IGlobalCallBack() {
                    @Override
                    public void executeCallBack(Object args) {
                        if(!JPushInterface.isPushStopped(Latte.getApplicationContext())){
                            //如果极光推送没有停止
                            //停止极光推送
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });
    }


}
