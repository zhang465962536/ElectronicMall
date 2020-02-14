package com.example.my_core.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

//官方的 AVLoadingIndicatorView  加载方式是通过反射加载的 通过取Load名字来加载Load的
//每次请求都需要去反射 那么性能会下降很多  对AVLoadingIndicatorView 使用做一步改进
public final class LoaderCreator {

    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    //以 一种缓存的方式 创建Load 就不需要每次需要使用Load的时候 都去反射一次 性能有大幅度提高
    static AVLoadingIndicatorView create(String type, Context context){

        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if(LOADING_MAP.get(type) == null){  //为空就创建  不为空就使用 相当于做个缓存
            final Indicator indicator = getIndicator(type);
            //将实例化好的类型放入  LOADING_MAP
            LOADING_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    //把名字拼接到完整的包名下
    private static Indicator getIndicator(String name){
        if(name == null || name.isEmpty()){
            return null;
        }
        //import com.wang.avi.indicators.BallPulseIndicator;
        final StringBuffer drawableClassName = new StringBuffer();
        if(!name.contains(".")){ //传入的是一个类名
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName(); //获取包名
            //每个一个AVLoadingIndicatorView 都会在这个包下 indicators 所以需要拼接
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);

        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
