package com.example.my.ec.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.my_core.ui.recycler.DataConverter;
import com.example.my_core.ui.recycler.MultipleFields;
import com.example.my_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class ShopCartDataConverter extends DataConverter {


    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0;i < size;i ++){
            final JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String desc = data.getString("desc");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final int count = data.getInteger("count");
            final double price = data.getDouble("price");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,ShopCartItemType.SHOP_CART_ITEM)
                    .setField(ShopCartItemFields.ID,id)
                    .setField(ShopCartItemFields.TITLE,title)
                    .setField(ShopCartItemFields.DESC,desc)
                    .setField(ShopCartItemFields.COUNT,count)
                    .setField(ShopCartItemFields.IMAGE_URL,thumb)
                    .setField(ShopCartItemFields.PRICE,price)
                    .build();

            dataList.add(entity);
        }

        return dataList;
    }
}
