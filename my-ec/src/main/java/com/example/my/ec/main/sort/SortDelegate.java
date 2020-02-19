package com.example.my.ec.main.sort;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.my.ec.R;
import com.example.my.ec.main.sort.content.ContentDelegate;
import com.example.my.ec.main.sort.list.VerticalListDelegate;
import com.example.my_core.delegates.bottom.BottomItemDelegate;

//分类
public class SortDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    // onLazyInitView 只有点击分类 才会加载布局  如果放在onBindView()里面 是进入主页就已经加载了
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        //加载根fragment
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container,listDelegate);
        // 设置右侧第一个分类显示， 默认显示分类1
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));
    }
}
