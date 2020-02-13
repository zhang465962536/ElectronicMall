package com.example.my_core.net.callback;

//网络请求错误(异常)的回调接口
public interface IError {

    void onError(int code,String msg);
}
