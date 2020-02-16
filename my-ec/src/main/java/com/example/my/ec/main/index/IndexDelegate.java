package com.example.my.ec.main.index;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my_core.delegates.bottom.BottomItemDelegate;
import com.example.my_core.ui.refresh.RefreshHandler;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;

//首页
public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    EditText mSearchView = null;

    private RefreshHandler mRefreshHandler =  null;

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = new RefreshHandler(mRefreshLayout);
    }

    //初始化下拉刷新控件
    private void initRefreshLayout(){
        //刷新时候 加载圈颜色变化
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        //设置位置和表现
        //setProgressViewOffset() 移动过程中的表现
        //scale 为 true 下拉过程中 加载球 就会由小变大 回弹过程中 由大变小 flase 反之
        //120是起始高度  300是下降终止高度
        mRefreshLayout.setProgressViewOffset(true,120,300);
    }


}
