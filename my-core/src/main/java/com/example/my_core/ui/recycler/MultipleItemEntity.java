package com.example.my_core.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.WeakHashMap;

public class MultipleItemEntity implements MultiItemEntity {

    //数据转换层
    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    //用于处理 每加入每个数据的键值对 即一个数据的key和一个数据的value
    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE =
            new SoftReference<>(MULTIPLE_FIELDS, ITEM_QUEUE);

    public MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        FIELDS_REFERENCE.get().putAll(fields);
    }

    public static MultipleEntityBuilder builder(){
        return new MultipleEntityBuilder();
    }

    //getItemType 控制recyclerView每一个Item的样式和表现特征 必须实现的方法
    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    //获取其他数据的方法
    public final <T> T getField(Object key){
        return (T) FIELDS_REFERENCE.get().get(key);
    }

    public final LinkedHashMap<?,?> getFields(){
        return FIELDS_REFERENCE.get();
    }

    public final MultipleItemEntity setField(Object key,Object value){
        FIELDS_REFERENCE.get().put(key,value);
        return this;
    }
}
