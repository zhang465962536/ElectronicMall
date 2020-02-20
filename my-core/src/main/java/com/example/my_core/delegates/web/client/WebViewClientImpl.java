package com.example.my_core.delegates.web.client;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_core.delegates.web.WebDelegate;
import com.example.my_core.delegates.web.route.Router;
import com.example.my_core.util.log.LatteLogger;

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    //做路由截断 和处理
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading",url);
       // return super.shouldOverrideUrlLoading(view, url);  //如果返回false 由WebView 处理事件 true 所有的页面链接的跳转 或者 JS的重定向全部由原生接管
        return Router.getInstance().handleWebUrl(DELEGATE,url);
    }

}
