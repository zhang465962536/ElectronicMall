package com.example.my.ec.main.index;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my.ec.main.EcBottomDelegate;
import com.example.my.ec.main.index.search.SearchDelegate;
import com.example.my_core.delegates.bottom.BottomItemDelegate;
import com.example.my_core.ui.loader.LatteLoader;
import com.example.my_core.ui.recycler.BaseDecoration;
import com.example.my_core.ui.refresh.RefreshHandler;
import com.example.my_core.util.callback.CallBackManager;
import com.example.my_core.util.callback.CallBackType;
import com.example.my_core.util.callback.IGlobalCallBack;
import com.example.my_core.util.log.ToastUtil;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

//首页
public class IndexDelegate extends BottomItemDelegate implements View.OnFocusChangeListener {

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

    private RefreshHandler mRefreshHandler = null;

    @OnClick(R2.id.icon_index_scan)
    void onClickScanQrCode(){
            startScanWithCheck(this.getParentDelegate());
    }



    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage(R.raw.index_data);
    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler  = RefreshHandler.create(mRefreshLayout,mRecyclerView,new IndexDataConverter());
        //添加扫描二维码事件回调
        CallBackManager.getInstance().addCallback(CallBackType.ON_SCAN, new IGlobalCallBack() {
            @Override
            public void executeCallBack(Object args) {
                ToastUtil.QuickToast("二维码内容是  " + args.toString());
            }
        });
        mSearchView.setOnFocusChangeListener(this);
    }

    //初始化下拉刷新控件
    private void initRefreshLayout() {
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
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    //初始化recyclerView的布局
    private void initRecyclerView(){
        //网格布局 一行4个 格子
        final GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        mRecyclerView.setLayoutManager(manager);
        //添加分割线
        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(),R.color.app_background),5));
        //点击商品之后 切换整个界面  BottomItem 栏也需要消失 所以获取最大的fragment
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
   }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        getParentDelegate().getSupportDelegate().start(new SearchDelegate());
    }
}
