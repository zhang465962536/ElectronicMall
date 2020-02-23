package com.example.my.ec.main.personal.order;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my.ec.main.personal.PersonalDelegate;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.ui.recycler.MultipleItemEntity;
import com.example.my_core.util.file.FileUtil;

import java.util.List;

import butterknife.BindView;

public class OrderListDelegate extends LatteDelegate {

    //通过Type 判断不同的 orderList
    private String mType = null;

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    //通过 onCreate 方法获取TYPE
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mType = args.getString(PersonalDelegate.ORDER_TYPE);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        String json = FileUtil.getRawFile(R.raw.order_list_data);
        noNet(json);
    }

    private void noNet(String json){
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data = new OrderListDataConverter().setJsonData(json).convert();
        final  OrderListAdapter adapter = new OrderListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new OrderListClickListener(OrderListDelegate.this));
    }

    private  void haveNet(){
        RestClient.builder()
                .loader(getContext())
                .url("order_list_data.json")
                .params("type",mType)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data = new OrderListDataConverter().setJsonData(response).convert();
                        final  OrderListAdapter adapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnItemTouchListener(new OrderListClickListener(OrderListDelegate.this));
                    }
                })
                .build()
                .get();
    }
}
