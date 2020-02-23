package com.example.my.ec.main.personal.address;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.net.RestClient;
import com.example.my_core.net.callback.ISuccess;
import com.example.my_core.ui.recycler.MultipleItemEntity;
import com.example.my_core.util.file.FileUtil;

import java.util.List;

import butterknife.BindView;

public class AddressDelegate extends LatteDelegate implements ISuccess {

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        String json = FileUtil.getRawFile(R.raw.address);
        noNet(json);
    }

    @Override
    public void onSuccess(String response) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data = new AddressDataConverter().setJsonData(response).convert();
        final AddressAdapter adapter = new AddressAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }

    //有服务器
    private void haveNet(){
        RestClient.builder()
                .url("address.json")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    private void noNet(String json){
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data = new AddressDataConverter().setJsonData(json).convert();
        final AddressAdapter adapter = new AddressAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }
}
