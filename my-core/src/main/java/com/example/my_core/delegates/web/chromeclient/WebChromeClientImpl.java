package com.example.my_core.delegates.web.chromeclient;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

//主要用于  WebView内部的处理和控制
public class WebChromeClientImpl extends WebChromeClient {

    //可以拦截Alert 进行自己的处理
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);

    }
}
