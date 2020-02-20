package com.example.my_core.delegates.web.route;

import com.alibaba.fastjson.JSON;
import com.example.my_core.delegates.web.WebDelegate;

//该接口是用于 WebView 和原生android 进行交互的
public class LatteWebInterface {
    private final WebDelegate DELEGATE;

    private LatteWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    //使用简单工厂方法
    public static LatteWebInterface create(WebDelegate delegate){
        return new LatteWebInterface(delegate);
    }

    //在JS 注入方法名 EVENT
    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        return null;
    }

}
