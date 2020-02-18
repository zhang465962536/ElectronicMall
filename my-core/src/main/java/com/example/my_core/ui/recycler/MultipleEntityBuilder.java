package com.example.my_core.ui.recycler;

import java.util.LinkedHashMap;

//MultipleEntity的建造者 用于链式调用
public class MultipleEntityBuilder {

    private static final LinkedHashMap<Object,Object> FIELDS = new LinkedHashMap<>();

    public MultipleEntityBuilder(){
        //先清除之前的数据
        //使用 MultipleEntity的时候 每次都会插入新的数据 不能够把上一次添加的数据追加进来 不然第一次就展示第一条 第二次就展示1+1条 第三次就会展示 1+1+1条
        FIELDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType){
        FIELDS.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }

    public final MultipleEntityBuilder setField(Object key,Object value){
        FIELDS.put(key,value);
        return this;
    }

    public final MultipleEntityBuilder setFields(LinkedHashMap<?,?> map){
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build(){
        return new MultipleItemEntity(FIELDS);
    }
}
