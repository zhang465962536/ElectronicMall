package com.example.my.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.my.ec.detail.GoodsDetailDelegate;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.ui.recycler.MultipleFields;
import com.example.my_core.ui.recycler.MultipleItemEntity;

public class IndexItemClickListener extends SimpleClickListener {

    // 通过 LatteDelegate 实例控制 它打开或者关闭
    private final LatteDelegate DELEGATE;

    public IndexItemClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    //简单工厂方法
    public static SimpleClickListener create(LatteDelegate delegate){
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodsId = entity.getField(MultipleFields.ID);
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodsId);
        DELEGATE.start(delegate);
    }

    //长按事件
    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) { }
    //点击子视图
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) { }
    //子视图长按
    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) { }
}
