package com.example.my_core.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;

import com.example.my_core.R;
import com.example.my_core.delegates.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

//单Activity架构
//该Activity 只是一个存放 Fragment 的容器 为了以后主Activity使用它 设置为抽象类
//传入 Delegate
public abstract class ProxyActivity extends SupportActivity {

    public abstract LatteDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    //初始化容器
    private void initContainer(@Nullable Bundle savedInstanceState){
        //一般使用frameLayout 容纳fragment
        @SuppressLint("RestrictedApi")
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if(savedInstanceState == null){ //第一次加载的时候
            loadRootFragment(R.id.delegate_container,setRootDelegate());

        }
    }

    //因为是单Activity 架构 当该Activity推出的时候整个app 就会退出
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //做垃圾回收操作 以下方法不一定执行
        System.gc();
        System.runFinalization();
    }
}
