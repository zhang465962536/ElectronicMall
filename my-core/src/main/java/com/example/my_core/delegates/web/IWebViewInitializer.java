package com.example.my_core.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//初始化 WebDelegate 的接口
public interface IWebViewInitializer {

    //初始化WebView,WebViewClient ,WebChromeClient
//传入WebView 作为目标
    WebView initWebView(WebView webView);

    //WebViewClient 针对于浏览器本身行为的控制
    WebViewClient initWebViewClient();

    //针对于内部页面的控制
    WebChromeClient initWebChromeClient();
}
