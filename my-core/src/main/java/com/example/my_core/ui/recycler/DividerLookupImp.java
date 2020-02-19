package com.example.my_core.ui.recycler;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

//处理 分割线 横向 和竖向的属性
public class DividerLookupImp implements DividerItemDecoration.DividerLookup {

    private final int COLOR;
    private final int SIZE;

    public DividerLookupImp(int color, int size) {
        this.COLOR = color;
        this.SIZE = size;
    }

    //竖向 分割线
    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }

    //横向 分割线
    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }
}
