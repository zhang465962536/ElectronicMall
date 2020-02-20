package com.example.my_core.delegates.web.route;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.example.my_core.delegates.web.WebDelegate;
import com.example.my_core.delegates.web.event.Event;
import com.example.my_core.delegates.web.event.EventManager;

//该接口是用于 WebView 和原生android 进行交互的
public final class LatteWebInterface {
    private final WebDelegate DELEGATE;

    private LatteWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    //使用简单工厂方法
    public static LatteWebInterface create(WebDelegate delegate){
        return new LatteWebInterface(delegate);
    }

    //在JS 注入方法名 EVENT
    @JavascriptInterface
    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        //将事件统一地加到事件组里边
        final Event event = EventManager.getInstance().createEvent(action);
        if(event != null){
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }

}
