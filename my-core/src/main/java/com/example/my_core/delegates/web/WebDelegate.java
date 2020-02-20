package com.example.my_core.delegates.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.delegates.web.route.LatteWebInterface;
import com.example.my_core.delegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

//WebDelegate 是承载WEB页面的基础核心
public abstract class WebDelegate extends LatteDelegate implements IWebViewInitializer{

    private WebView mWebView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    private boolean mIsWebViewAbailable = false;

    public WebDelegate() {
    }

    //强制实现 做初始化
    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        //获取url 因为以fragment每个页面的载体
        mUrl = args.getString(RouteKeys.URL.name());
        initWebView();
    }

    //初始化WebView
    @SuppressLint("JavascriptInterface")
    private void initWebView(){
        //避免WebView 重复初始化 导致内存泄漏
        if(mWebView != null){
            mWebView.removeAllViews();
            mWebView.destroy();
        }else {
            final IWebViewInitializer initializer = setInitializer();
            if(initializer != null){
                //如果WebView如果写在 XML里面 国内外各大网站会有相应的描述 容易造成内存泄漏 。如果在代码中new 的WebView 可以很大程度上避免
                final WeakReference<WebView> webViewWeakReference = new WeakReference<WebView>(new WebView(getContext()),WEB_VIEW_QUEUE);
                mWebView = webViewWeakReference.get();
                mWebView= initializer.initWebView(mWebView);
                mWebView.setWebViewClient(initializer.initWebViewClient());
                mWebView.setWebChromeClient(initializer.initWebChromeClient());
                //添加  JS接口 用于WebView 和原生 进行交互的
                mWebView.addJavascriptInterface(LatteWebInterface.create(this),"latte");
                //可以使用WebView了
                mIsWebViewAbailable = true;
            }else {
                throw new NullPointerException("Initializer is NuLL");
            }
        }
    }

    public WebView getWebView(){
        if(mWebView == null){
            throw new NullPointerException("WebView IS NULL!");
        }
        return mIsWebViewAbailable ? mWebView: null;
    }

    public String getUrl(){
        if(mUrl == null){
            throw new NullPointerException("Url IS NULL!");
        }
        return mUrl;
    }



    @Override
    public void onPause() {
        super.onPause();
        if(mWebView != null){
            mWebView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mWebView != null){
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAbailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWebView != null){
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
