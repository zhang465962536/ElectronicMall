package com.example.my_core.net;

import com.example.my_core.net.callback.IError;
import com.example.my_core.net.callback.IFailure;
import com.example.my_core.net.callback.IRequest;
import com.example.my_core.net.callback.ISuecess;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

//把建造者和宿主类分隔开 不用静态内部类方法
public class RestClientBuilder {  //主要做传值操作

    //建造者模式 值不能是final ，如果是final 就无法依次赋值
    private  String mUrl;
    //private  Map<String,Object> mParams;  //每次都需要去重新构建 在RestCreator声明一个全局变量即可
    private static final Map<String,Object> PARAMS = RestCreator.getParams();
    private  IRequest mIRequest;
    private  ISuecess mISuecess;
    private  IFailure mIFailure;
    private  IError mIError;
    private  RequestBody mBody;

    //不允许外部类直接 new RestClientBuilder 只允许同包使用
    RestClientBuilder(){
    }

    //编写具体方法
    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        /*this.mParams = mParams;*/
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key,Object value){
        /*if(mParams == null){
            //使用 WeakHashMap 可以使得内存管理更加精确 请求的时候 内部的值在不用的时候 最好让系统自动回收掉
            mParams = new WeakHashMap<>();
        }
        this.mParams.put(key,value);*/
        PARAMS.put(key,value);
        return this;
    }

    //如果传入的是一个原始数据
    public final RestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    //成功回调的处理
    public final RestClientBuilder success(ISuecess iSuecess){
        this.mISuecess = iSuecess;
        return this;
    }
    //失败回调的处理
    public final RestClientBuilder failure(IFailure iFailure){
        this.mIFailure = iFailure;
        return this;
    }

    //异常回调的处理
    public final RestClientBuilder error(IError iError){
        this.mIError = iError;
        return this;
    }

    //异常回调的处理
    public final RestClientBuilder onRequest(IRequest iRequest){
        this.mIRequest = iRequest;
        return this;
    }

 /*   //检查Map是否为空  因为Retrofit 不允许有空的map
    private Map<String,Object> checkParams(){
        if(mParams == null){
            return new WeakHashMap<>();
        }
        return mParams;
    }使用WeakHashMap 之后 就不需要判断是否为空了*/

    //Build RestClient
    public final RestClient build(){
        return new RestClient(mUrl,PARAMS,mIRequest,mISuecess,mIFailure,mIError,mBody);
    }

}
