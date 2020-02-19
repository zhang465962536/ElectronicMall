package com.example.my.ec.main.sort.list;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.my.ec.R;
import com.example.my_core.delegates.LatteDelegate;

//垂直列表
public class VerticalListDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
