package com.example.my_core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);
    }

    //简单工厂模式
    public static MultipleViewHolder create(View view){
        return new MultipleViewHolder(view);
    }
}
