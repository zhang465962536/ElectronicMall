package com.example.my_core.delegates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public abstract class BaseDelegate extends SwipeBackFragment {

    private Unbinder mUnbinder = null;

    //传入布局
    public abstract Object setLayout();
    //强制子类绑定视图
    public abstract void onBindView(@Nullable Bundle savedInstanceState,View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = null;
        if(setLayout() instanceof Integer){ //如果setLayout()返回的是 layoutId 即 Integer值
            rootView = inflater.inflate((Integer) setLayout(),container,false);
        }else if (setLayout() instanceof View){  // //如果setLayout()返回的是 View
            rootView = (View) setLayout();
        }
        if(rootView != null){ //如果root 不为空 绑定资源
            mUnbinder = ButterKnife.bind(this,rootView);   //这里绑定fragment 和 RootView
            onBindView(savedInstanceState,rootView);
        }

        return rootView;
    }

    //销毁的时候 判断 mUnbinder的情况 解除绑定
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
