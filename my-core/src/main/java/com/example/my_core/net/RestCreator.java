package com.example.my_core.net;

import com.example.my_core.app.ConfigKeys;
import com.example.my_core.app.Latte;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//创建 Retrofit
public class RestCreator {

    /**
     * 参数容器
     */
    //声明一个全局变量 方便后面使用 使用单例懒汉模式创建 比较严谨
    //public static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    private static final class ParamsHolder{
        public static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    /**
     * 构建全局Retrofit客户端
     */
    //使用单例模式创建方式 使用内部类Holder
    private static final class RetrofitHolder{
        //Retrofit 全局实例只需要一个即可 所以这里统一创建
        //BASE_URL 在 ExampleApp withApiHost传入的
        private static final String BASE_URL =  Latte.getConfiguration(ConfigKeys.API_HOST.toString());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()  //简化建造者模式
                .baseUrl(BASE_URL)
                .client(OkhttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())    //String转换器
                .build();
    }


    /**
     * 构建OkHttp
     */
    //如果还想对Okhttp Client 处理的话 可以对Okhttp 进行惰性初始化 懒汉单例模式
    private static final class OkhttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigKeys.INTERCEPTOR);
        //通过for循环 将拦截器 传入OkhttpClient中
        private static final OkHttpClient.Builder addInterceptor(){
            //判断是否存在拦截器
            if(INTERCEPTORS != null &&!INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor: INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
        //  .addInterceptor() 拦截器 在response 时候调用一次
        //.addNetworkInterceptor() 拦截器 分别在 response  request 时候调用一次1

    }

    /**
     * Service接口
     */
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

}
