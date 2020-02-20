package com.example.my_core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_core.app.ConfigKeys;
import com.example.my_core.app.Latte;
import com.example.my_core.delegates.IPageLoadListener;
import com.example.my_core.delegates.web.WebDelegate;
import com.example.my_core.delegates.web.route.Router;
import com.example.my_core.ui.loader.LatteLoader;
import com.example.my_core.util.log.LatteLogger;
import com.example.my_core.util.storage.LattePreference;

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    //做路由截断 和处理
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        // return super.shouldOverrideUrlLoading(view, url);  //如果返回false 由WebView 处理事件 true 所有的页面链接的跳转 或者 JS的重定向全部由原生接管
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    //进行显示加载圈
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    //获取浏览器cookie  实现Cookie同步
    private void syncCookie() {
        final CookieManager manager = CookieManager.getInstance();
        //这里的Cookie和API请求的Cookie是不一样的，这里的Cookie在网页是不可见的
        final String webHost = Latte.getConfiguration(ConfigKeys.WEB_HOST);
        final String cookieStr = manager.getCookie(webHost);
        if (webHost != null) {
            if (manager.hasCookies()) {
                if (cookieStr != null && !cookieStr.equals("")) {
                    //存入SP中
                    LattePreference.addCustomAppProfile("cookie", cookieStr);
                }
            }
        }
    }

    //进行隐藏加载圈
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        }, 1000);

    }
}
