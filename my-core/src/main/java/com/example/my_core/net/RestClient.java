package com.example.my_core.net;

import android.content.Context;

import com.example.my_core.net.callback.IError;
import com.example.my_core.net.callback.IFailure;
import com.example.my_core.net.callback.IRequest;
import com.example.my_core.net.callback.ISuecess;
import com.example.my_core.net.callback.RequestCallbacks;
import com.example.my_core.net.download.DownloadHandler;
import com.example.my_core.ui.loader.LatteLoader;
import com.example.my_core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;

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
    private final File FILE;
    private LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    public RestClient(String url, Map<String, Object> params, IRequest request, ISuecess suecess, IFailure failure, IError error, RequestBody body,File file,Context context,LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUECESS = suecess;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
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

        //开始请求的时候 启动加载窗体
        if(LOADER_STYLE != null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.get(URL,PARAMS);
                break;
            case POST_RAW:
                 call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.get(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case DELETE:
                call = service.get(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);//以For形式提交文件
                call = RestCreator.getRestService().upload(URL,body);
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
                ERROR,
                LOADER_STYLE
        );
    }

    //get post put delete 具体方法
    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        if(BODY == null){  //如果 BODY 为空 使用默认的post
            request(HttpMethod.POST);
        }else {
            if(!PARAMS.isEmpty()){ //如果参数不是空的  如果post一个原始数据的话 这个参数一定为空
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }

    }
    public final void put(){
        if(BODY == null){  //如果 BODY 为空 使用默认的put
            request(HttpMethod.PUT);
        }else {
            if(!PARAMS.isEmpty()){ //如果参数不是空的  如果put一个原始数据的话 这个参数一定为空
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }

    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }


}
