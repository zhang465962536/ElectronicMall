package com.example.my_core.delegates.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.example.my_core.delegates.web.chromeclient.WebChromeClientImpl;
import com.example.my_core.delegates.web.client.WebViewClientImpl;
import com.example.my_core.delegates.web.route.RouteKeys;
import com.example.my_core.delegates.web.route.Router;

//具体实现类e
public class WebDelegateImpl extends WebDelegate{

    //使用静态工厂方法 创建 WebDelegateImpl
    public static WebDelegateImpl create(String url){
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(),url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this; //自身加载
    }

    @Override
    public Object setLayout() {
        return getWebView() ;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if(getUrl() != null){
            //使用原生的方式模拟Web 跳转 并且进行页面加载
            Router.getInstance().loadPage(this,getUrl());

        }
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}
