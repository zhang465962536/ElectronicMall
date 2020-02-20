package com.example.my_core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.delegates.web.WebDelegate;
import com.example.my_core.delegates.web.WebDelegateImpl;

//路由者
public class Router {
//懒汉单例模式
    private Router() {
    }

    private static class Holder{
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance(){
        return Holder.INSTANCE;
    }

    //处理url方法
    public final boolean handleWebUrl(WebDelegate delegate,String url){

        //如果是电话协议的链接
        if(url.contains("tel:")){
            callPhone(delegate.getContext(),url);
            return true;
        }

        final LatteDelegate parentDelegate = delegate.getParentDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);

        if(parentDelegate == null){  //如果没有父fragment 直接当前fragment 进行跳转
            delegate.start(webDelegate);
        }else {
            parentDelegate.start(webDelegate);
        }

        return true;  //接管WebView 事件
    }


    //拨打电话
    private void callPhone(Context context,String phone){
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(phone);
        intent.setData(data);
        context.startActivity(intent);
    }

    //进行Web页面的加载
    private void loadWebPage(WebView webView,String url){
        if(webView != null){
            webView.loadUrl(url);
        }else {
            throw new NullPointerException("Web View is Null !");
        }
    }

    //在assets文件夹 写的HTML页面和JS 包括样式都会以本地页面进行渲染
    private void loadLocalPage(WebView webView,String url){
        loadWebPage(webView,"file:///android_asset/"+url);
    }

    private void loadPage(WebView webView,String url){
        //如果是网络的 或者是本地的 网页
        if(URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url) ){
            loadWebPage(webView,url);
        }else {
            loadLocalPage(webView,url);
        }
    }
    public final void loadPage(WebDelegate delegate,String url){
        loadPage(delegate.getWebView(),url);
    }

}
