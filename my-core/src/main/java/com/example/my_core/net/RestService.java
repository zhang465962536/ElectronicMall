package com.example.my_core.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

//Rest使用需要有一个接口 该Service定义一系列方法 用于请求
public interface RestService {
    //请求返回值 因为是通用的封装所以@GET 不需要加入任何路由信息
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    //传输
    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    //放置
    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body );

    //删除
    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    //下载
    //download 返回的是请求体 Retrofit 默认的download方式是把文件一次性下载到内存里，下载完毕之后统一写入文件中 当文件过大会出现内存溢出
    //可以用 @Streaming 解决问题 边下载 一边在系统文件写入它 把文件写入放入单独线程中 需要用异步形式去处理 否则在主线程中进行I/O操作 也会报错
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);
}
