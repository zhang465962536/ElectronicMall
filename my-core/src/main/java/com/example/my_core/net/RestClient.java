package com.example.my_core.net;

import com.example.my_core.net.callback.IError;
import com.example.my_core.net.callback.IFailure;
import com.example.my_core.net.callback.IRequest;
import com.example.my_core.net.callback.ISuecess;
import com.example.my_core.net.callback.RequestCallbacks;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

//RESTful
//编写网络框架 传入什么参数就用什么 使用建造者模式
//请求网络具体实现类

public class RestClient {  // RestClient 在每次builder 去build 的时候 都会生成一个全新的实例
    //网络请求常用的参数 url 常用的值 文件 回调 loader(等待加载圈)
    //这里的参数需要一次构建完毕 不允许更改

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuecess SUECESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;

    public RestClient(String url, Map<String, Object> params, IRequest request, ISuecess suecess, IFailure failure, IError error, RequestBody body) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUECESS = suecess;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
    }

    //创建构造者
    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    //请求开始时候 做的事情
    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        switch (method) {
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.get(URL,PARAMS);
                break;
            case PUT:
                call = service.get(URL,PARAMS);
                break;
            case DELETE:
                call = service.get(URL,PARAMS);
                break;
            default:
                break;
        }

        //如果call 已经创建
        if(call != null){
            //call.execute();  execute 在主线程中执行
            call.enqueue(getRequestCallback()); //enqueue 在 后台运行 另在的线程执行 不影响UI线程
        }

    }

    //单独写一个方法 getCallBack
    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(
                REQUEST,
                SUECESS,
                FAILURE,
                ERROR
        );
    }

    //get post put delete 具体方法
    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        request(HttpMethod.POST);
    }
    public final void put(){
        request(HttpMethod.PUT);
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }


}
