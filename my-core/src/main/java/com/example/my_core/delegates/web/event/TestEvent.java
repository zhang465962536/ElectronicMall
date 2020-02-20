package com.example.my_core.delegates.web.event;

import android.webkit.WebView;

import com.example.my_core.util.log.ToastUtil;

public class TestEvent extends Event{

    @Override
    public String execute(String params) {
        ToastUtil.QuickToast(params);
        if(getAction().equals("test")){
            final WebView webView = getWebView();
            getWebView().post(new Runnable() {  //.post保证这代码块 运行在主线程
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall();",null);
                }
            });
        }
        return null;
    }

}
