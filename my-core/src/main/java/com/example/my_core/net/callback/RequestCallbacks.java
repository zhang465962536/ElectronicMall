package com.example.my_core.net.callback;

import androidx.core.app.NavUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuecess SUECESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public RequestCallbacks(IRequest request, ISuecess suecess, IFailure failure, IError error) {
        this.REQUEST = request;
        this.SUECESS = suecess;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) { //请求成功
            if (call.isExecuted()) {  //call 已经执行
                if (SUECESS != null) {  //进行 SUECESS 回调
                    SUECESS.onSuccess(response.body()); //传入值
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(FAILURE != null){
            FAILURE.onFailure();
        }

        //请求结束的回调也可以执行
        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }
    }
}
