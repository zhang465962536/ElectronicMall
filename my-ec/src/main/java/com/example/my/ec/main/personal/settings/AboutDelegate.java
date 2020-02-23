package com.example.my.ec.main.personal.settings;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.my.ec.R;
import com.example.my_core.delegates.LatteDelegate;

public class AboutDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
