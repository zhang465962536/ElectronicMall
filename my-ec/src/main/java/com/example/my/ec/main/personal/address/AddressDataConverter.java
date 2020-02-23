package com.example.my.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.my_core.ui.recycler.DataConverter;
import com.example.my_core.ui.recycler.MultipleFields;
import com.example.my_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

//收货地址数据转换器
public class AddressDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();

        for(int i = 0;i < size ; i ++){
            final JSONObject data = array.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final String phone = data.getString("phone");
            final String address = data.getString("address");
            final boolean isDefault = data.getBoolean("default");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(AddressItemType.ITEM_ADDRESS)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.NAME,name)
                    .setField(MultipleFields.TAG,isDefault)
                    .setField(AddressItemFields.ADDRESS,address)
                    .setField(AddressItemFields.PHONE,phone)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
