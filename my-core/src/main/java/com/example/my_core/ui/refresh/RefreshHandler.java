package com.example.my_core.ui.refresh;

import android.util.Log;

import androidx.annotation.RawRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.my_core.app.Latte;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.ui.recycler.DataConverter;
import com.example.my_core.ui.recycler.MultipleRecyclerAdapter;
import com.example.my_core.util.file.FileUtil;
import com.example.my_core.util.log.ToastUtil;

import java.util.logging.Logger;

//上下拉刷新 助手 OnRefreshListener 用于监听Refresh操作
public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener
{

    //传入Layout
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    public RefreshHandler(SwipeRefreshLayout swiperefreshlayout,RecyclerView recyclerView,DataConverter converter,PagingBean bean) {
        this.REFRESH_LAYOUT = swiperefreshlayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        //监听滑动事件
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    //静态简单工厂
    public static RefreshHandler create(SwipeRefreshLayout swiperefreshlayout,RecyclerView recyclerView,DataConverter converter){
        return new RefreshHandler(swiperefreshlayout,recyclerView,converter,new PagingBean());
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
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url("http://127.0.0.1:8080/EC/"+url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        ToastUtil.QuickToast(response);
                        Log.e("firstPage", "onSuccess: " + response );

                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    //没有服务器情况下
    public void firstPage(@RawRes int rawId){
        BEAN.setDelayed(1000);
        final String json = FileUtil.getRawFile(rawId);
        ToastUtil.QuickToast(json);
        Log.e("firstPage", "onSuccess: " + json);

        final JSONObject object = JSON.parseObject(json);
        BEAN.setTotal(object.getInteger("total"))
                .setPageSize(object.getInteger("page_size"));
        //设置Adapter
        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(json));
        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLERVIEW);
        RECYCLERVIEW.setAdapter(mAdapter);
        BEAN.addIndex();


    }



    @Override
    public void onRefresh() {
        refresh();
    }

    //上拉刷新
    @Override
    public void onLoadMoreRequested() {

    }
}
