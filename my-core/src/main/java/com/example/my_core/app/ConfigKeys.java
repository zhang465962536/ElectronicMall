package com.example.my_core.app;

//枚举类在整个应用程序中是唯一的单例 并且只能初始化一次
//如果需要进行多线程操作 可以使用枚举类安全地进行惰性单例的初始化 懒汉模式
public enum ConfigKeys {
    API_HOST,           //配置网络请求域名
    APPLICATION_CONTEXT,//全局上下文
    CONFIG_READY,       //控制初始化或者配置是否完成
    ICON,                //存储自己的初始化项目
    LOADER_DELAYED,        //延迟加载
    INTERCEPTOR      //拦截器
}
