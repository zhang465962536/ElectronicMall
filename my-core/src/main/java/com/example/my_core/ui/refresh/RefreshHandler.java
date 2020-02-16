package com.example.my_core.ui.refresh;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.my_core.app.Latte;

//上下拉刷新 助手 OnRefreshListener 用于监听Refresh操作
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    //传入Layout
    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
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



    @Override
    public void onRefresh() {
        refresh();
    }
}
