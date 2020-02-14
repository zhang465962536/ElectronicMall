package com.example.my.ec.launcher;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.my.ec.R;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.ui.launcher.LauncherHolderCreator;
import com.example.my_core.ui.launcher.ScrollLauncherTag;
import com.example.my_core.util.storage.LattePreference;

import java.util.ArrayList;

//启动页面的 滚动页面
public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {

    //初始化ConvenientBanner
    private ConvenientBanner<Integer> mConvenientBanner = null;
    //初始化 轮播图片集合
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private void initBanner(){
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);

        mConvenientBanner
                .setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus}) //设置未选择 和 已经选择的选择器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置焦点位置 水平居中
                .setOnItemClickListener(this)
                .setCanLoop(false);  //设置不可以循环

    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        //如果点击的是最后一个轮播图
        if(position == INTEGERS.size() - 1){
            //设置flag 表面已经是第一次进入该app 以后轮播图都不会再出现
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            //检查用户是否已经登录
        }
    }
}
