package com.example.festec;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.IError;
import com.example.my_core.net.callback.IFailure;
import com.example.my_core.net.callback.ISuccess;

public class ExampleDelegate extends LatteDelegate {
    //设置布局
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    //对控件做的操作
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
            testRestClient();
    }

    //测试创建的 RestClient 网络请求框架
    private void testRestClient(){
        RestClient.builder()
                .url("http://news.baidu.com/")
                //.params("","")
                .loader(getContext()) //使用加载窗体
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                        Log.e("onSuccessResponse",response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
