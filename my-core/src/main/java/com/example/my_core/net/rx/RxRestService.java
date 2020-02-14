package com.example.my_core.net.rx;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
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

public interface RxRestService {
    //Observable 是可观察对象 基于 Observable 进行相应的链式，响应式的，观察式的操作
    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> params);

    //传输
    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> params);

    //放置
    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String, Object> params);

    //删除
    @DELETE
    Observable<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    //下载
    //download 返回的是请求体 Retrofit 默认的download方式是把文件一次性下载到内存里，下载完毕之后统一写入文件中 当文件过大会出现内存溢出
    //可以用 @Streaming 解决问题 边下载 一边在系统文件写入它 把文件写入放入单独线程中 需要用异步形式去处理 否则在主线程中进行I/O操作 也会报错
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    @Multipart
    @POST
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);
}
