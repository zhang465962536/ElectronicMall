package com.example.my.ec.main.cart;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.my.ec.R;
import com.example.my_core.delegates.bottom.BottomItemDelegate;

//购物车
public class CartDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
