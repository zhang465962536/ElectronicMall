package com.example.my_core.delegates.bottom;

import java.util.LinkedHashMap;

//将 BottomItemDelegate 和 BottomTabBean 连接起来
public final class ItemBuilder {

    //使用 LinkedHashMap 使得双方结合起来
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    //使用简单工厂模式创建  可以让构造参数一目了然
    static ItemBuilder builder(){
        return new ItemBuilder();
    }

    //创建Item 使用链式  单个Item加入
    public final ItemBuilder addItem(BottomTabBean bean,BottomItemDelegate delegate){
        ITEMS.put(bean,delegate);
        return this;
    }

    //多个Item加入
    public final ItemBuilder addItems( LinkedHashMap<BottomTabBean,BottomItemDelegate> items){
        ITEMS.putAll(items);
        return this;
    }

    public final  LinkedHashMap<BottomTabBean,BottomItemDelegate> build(){
        return ITEMS;
    }

}
