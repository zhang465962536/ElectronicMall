package com.example.my_core.net.interceptors;

import androidx.annotation.RawRes;

import com.example.my_core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    //获取文件
    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()  //返回Response实例
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json)) //请求体
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    //@RawRes 注解 确保int 数据一定是R生成有唯一id的资源
    private Response debugResponse(Chain chain,@RawRes int rawId){
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain,json);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString(); //获取将要拦截的url
        if(url.contains(DEBUG_URL)) { //如果url 内包含 所要拦截的关键字
            //返回一个想要返回的json数据
            return debugResponse(chain,DEBUG_RAW_ID);
        }
        //如果不需要拦截 就原样返回
        return chain.proceed(chain.request());
    }
}
