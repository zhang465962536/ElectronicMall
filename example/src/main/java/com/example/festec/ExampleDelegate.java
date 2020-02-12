package com.example.festec;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.my_core.delegates.LatteDelegate;

public class ExampleDelegate extends LatteDelegate {
    //设置布局
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    //对控件做的操作
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
