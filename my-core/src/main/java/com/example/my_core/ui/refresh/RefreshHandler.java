package com.example.my_core.ui.refresh;

import android.util.Log;

import androidx.annotation.RawRes;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.my_core.app.Latte;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.util.file.FileUtil;
import com.example.my_core.util.log.ToastUtil;

import java.util.logging.Logger;

//上下拉刷新 助手 OnRefreshListener 用于监听Refresh操作
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    //传入Layout
    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout swiperefreshlayout) {
        this.REFRESH_LAYOUT = swiperefreshlayout;
        //监听滑动事件
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }


    private void refresh(){
        //开始加载
        REFRESH_LAYOUT.setRefreshing(true);
        //模拟网络请求
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //todo 可以进行一些网络请求
                //加载圈停止滚动 即消失
                REFRESH_LAYOUT.setRefreshing(false);

            }
        },2000);
    }

    //有服务器的情况下
    public void firstPage(String url){
        RestClient.builder()
                .url("http://127.0.0.1:8080/EC/"+url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        ToastUtil.QuickToast(response);
                        Log.e("firstPage", "onSuccess: " + response );
                    }
                })
                .build()
                .get();
    }

    //没有服务器情况下
    public void firstPage(@RawRes int rawId){
        final String json = FileUtil.getRawFile(rawId);
        ToastUtil.QuickToast(json);
        Log.e("firstPage", "onSuccess: " + json);
    }



    @Override
    public void onRefresh() {
        refresh();
    }
}
