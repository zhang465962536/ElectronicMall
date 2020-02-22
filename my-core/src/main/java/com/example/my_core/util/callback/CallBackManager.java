package com.example.my_core.util.callback;

import java.util.WeakHashMap;

public class CallBackManager {

    private static final WeakHashMap<Object,IGlobalCallBack> CALLBACKS = new WeakHashMap<>();

    //懒汉单例模式
    private static class Holder{
        private static final CallBackManager INSTANCE = new CallBackManager();
    }

    public static CallBackManager getInstance(){
        return  Holder.INSTANCE;
    }

    public CallBackManager addCallback(Object tag,IGlobalCallBack callBack){
        CALLBACKS.put(tag,callBack);
        return this;
    }

    public IGlobalCallBack getCallback(Object tag){
       return CALLBACKS.get(tag);
    }
}
