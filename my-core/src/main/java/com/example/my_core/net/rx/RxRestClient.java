package com.example.my_core.net.rx;

import android.content.Context;

import com.example.my_core.net.HttpMethod;
import com.example.my_core.net.RestCreator;
import com.example.my_core.ui.loader.LatteLoader;
import com.example.my_core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

//RESTful
//编写网络框架 传入什么参数就用什么 使用建造者模式
//请求网络具体实现类

public class RxRestClient {  // RestClient 在每次builder 去build 的时候 都会生成一个全新的实例
    //网络请求常用的参数 url 常用的值 文件 回调 loader(等待加载圈)
    //这里的参数需要一次构建完毕 不允许更改

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final RequestBody BODY;
    private LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    public RxRestClient(String url,
                        Map<String, Object> params,
                        RequestBody body,
                        File file,
                        Context context,
                        LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    //创建构造者
    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    //请求开始时候 做的事情
    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;

        //开始请求的时候 启动加载窗体
        if(LOADER_STYLE != null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable = service.get(URL,PARAMS);
                break;
            case POST:
                observable = service.get(URL,PARAMS);
                break;
            case PUT:
                observable = service.get(URL,PARAMS);
                break;
            case DELETE:
                observable = service.get(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
                break;
        }
        return observable;
    }


    //get post put delete 具体方法
    public final Observable<String> get(){
        return request(HttpMethod.GET);
    }
    public final Observable<String> post(){
       return request(HttpMethod.POST);
    }
    public final Observable<String> put(){
        return request(HttpMethod.PUT);
    }
    public final Observable<String> delete(){
       return request(HttpMethod.DELETE);
    }

    public final Observable<ResponseBody> download(){
        final Observable<ResponseBody> responseBodyObservable = RestCreator.getRxRestService().download(URL,PARAMS);
        return responseBodyObservable;
    }
}
