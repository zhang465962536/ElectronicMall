package com.example.my_core.util.callback;

//全局回调  泛型接口可以以更安全的形式去转换不同的类型
public interface IGlobalCallBack<T> {

    void  executeCallBack(T args);
}
