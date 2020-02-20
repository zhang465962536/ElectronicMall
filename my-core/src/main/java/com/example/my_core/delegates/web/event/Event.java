package com.example.my_core.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.delegates.web.WebDelegate;

//抽象每个具体得事件 将每个事件作为类的某一个实例来处理
public abstract class Event implements IEvent{

    private Context mContext = null;
    private String mAction = null;
    private WebDelegate mDelegate = null;
    private String mUrl = null;
    private WebView mWebView = null;

    public Event() {
    }

    public Event(Context mContext, String mAction, WebDelegate mDelegate, String mUrl) {
        this.mContext = mContext;
        this.mAction = mAction;
        this.mDelegate = mDelegate;
        this.mUrl = mUrl;
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void setWebView(WebView webView) {
        this.mWebView = webView;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        this.mAction = action;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(WebDelegate delegate) {
        this.mDelegate = delegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
