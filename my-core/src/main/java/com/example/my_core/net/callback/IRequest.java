package com.example.my_core.net.callback;

//网络请求开始-结束的回调接口
//请求开始的时候 加载一个加载圈  请求结束 需要把加载圈隐藏掉
public interface IRequest {

    void onRequestStart();

    void onRequestEnd();

}
