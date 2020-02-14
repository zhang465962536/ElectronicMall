package com.example.my_core.net.interceptors;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseInterceptor implements Interceptor {


    //获取url里面的参数  get请求里面特有的东西  LinkedHashMap 是有序的   HashMap是无序的
    protected LinkedHashMap<String,String> getUrlParameters(Chain chain){
        final HttpUrl url = chain.request().url();
        //获取 请求参数的个数
        int size = url.querySize();
        //从第一个到最后一个有序排列的url参数对
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        for(int i = 0;i <size; i ++){
            /*  url.query() 获取完整的url参数 会以 ? 和 & 形式拼接返回
            *   url.queryParameter() 会插入一个key 即 name 得到value
            * url.queryParameterName() 通过第几个参数来获取Parameter的名字
            * queryParameterValue() 获取值
            * */
            params.put(url.queryParameterName(i),url.queryParameterValue(i));
        }
        return params;
    }

    //通过key值 获取value
    protected String getUrlParameters(Chain chain,String key){
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    //获取参数的方法 从post请求体获取
    protected LinkedHashMap<String,String> getBodyparameters(Chain chain){
        final FormBody formBody = (FormBody) chain.request().body();
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        int size = formBody.size();
        for(int i = 0; i < size; i ++){
            params.put(formBody.name(i),formBody.value(i));
        }
        return params;
    }

    protected String getBodyparameters(Chain chain,String key){
        return getBodyparameters(chain).get(key);
    }


}
