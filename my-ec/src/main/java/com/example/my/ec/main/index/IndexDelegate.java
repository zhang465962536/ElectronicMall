package com.example.my.ec.main.index;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.my.ec.R;
import com.example.my_core.delegates.bottom.BottomItemDelegate;

//首页
public class IndexDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
