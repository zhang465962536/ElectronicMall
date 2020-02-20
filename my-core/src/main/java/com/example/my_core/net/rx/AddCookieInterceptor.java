package com.example.my_core.net.rx;

import com.example.my_core.util.storage.LattePreference;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

//完成Cookie 的注入
public final class AddCookieInterceptor implements Interceptor {

    //实现Cookie拦截器接口
    @Override
    public Response intercept(Chain chain) throws IOException {

        //拦截原始请求
        final Request.Builder builder = chain.request().newBuilder();

        Observable
                .just(LattePreference.getCustomAppProfile("cookie")) //实现持久化
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String cookie) throws Exception {
                //给原生API附带上WebView 所拦截的cookie
                builder.addHeader("Cookie",cookie);
            }
        });
        return chain.proceed(builder.build());
    }
}
