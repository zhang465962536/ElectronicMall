package com.example.my_core.net.callback;

import android.os.Handler;

import com.example.my_core.ui.LatteLoader;
import com.example.my_core.ui.LoaderStyle;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUECESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    //Handler尽量声明为 static 避免内存泄漏
    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(IRequest request, ISuccess suecess, IFailure failure, IError error, LoaderStyle loaderStyle) {
        this.REQUEST = request;
        this.SUECESS = suecess;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = loaderStyle;
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
        //请求结束 关闭 加载窗体
        stopLoading();

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }

        //请求结束的回调也可以执行
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        //请求失败 关闭 加载窗体
        stopLoading();

    }

    //关闭窗体
    private void stopLoading(){
        if(LOADER_STYLE != null){
            //加个延迟效果 加载完成之后 一秒后消失 而不是瞬间
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            },1000);
        }
    }
}
