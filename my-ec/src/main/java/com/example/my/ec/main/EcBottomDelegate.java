package com.example.my.ec.main;

import android.graphics.Color;

import com.example.my.ec.main.cart.CartDelegate;
import com.example.my.ec.main.discover.DiscoverDelegate;
import com.example.my.ec.main.index.IndexDelegate;
import com.example.my.ec.main.personal.PersonalDelegate;
import com.example.my.ec.main.sort.SortDelegate;
import com.example.my_core.delegates.bottom.BaseBottomDelegate;
import com.example.my_core.delegates.bottom.BottomItemDelegate;
import com.example.my_core.delegates.bottom.BottomTabBean;
import com.example.my_core.delegates.bottom.ItemBuilder;

import java.util.LinkedHashMap;

public class EcBottomDelegate extends BaseBottomDelegate {

    //通过builder  传入构建好的映射键值对
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new CartDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new PersonalDelegate());
        return builder.addItems(items).build();
    }

    //默认显示主页
    @Override
    public int setmIndexDelegate() {
        return 0;
    }

    //设置点击后的颜色
    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
