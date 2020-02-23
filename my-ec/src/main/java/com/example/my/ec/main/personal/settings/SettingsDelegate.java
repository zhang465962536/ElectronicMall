package com.example.my.ec.main.personal.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my.ec.main.personal.address.AddressDelegate;
import com.example.my.ec.main.personal.list.ListAdapter;
import com.example.my.ec.main.personal.list.ListBean;
import com.example.my.ec.main.personal.list.ListItemType;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.util.callback.CallBackManager;
import com.example.my_core.util.callback.CallBackType;
import com.example.my_core.util.log.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingsDelegate extends LatteDelegate {

    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            //CallBackManager.getInstance().getCallback(CallBackType.TAG_OPEN_PUSH).executeCallBack(null);
                            ToastUtil.QuickToast("打开推送");
                        } else {
                           // CallBackManager.getInstance().getCallback(CallBackType.TAG_STOP_PUSH).executeCallBack(null);
                         ToastUtil.QuickToast("关闭推送");
                        }
                    }
                })
                .setText("消息推送")
                .build();

        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                //.setDelegate(new AboutDelegate())
                .setText("关于")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        //mRecyclerView.addOnItemTouchListener(new SettingsClickListener(this));
    }
}
