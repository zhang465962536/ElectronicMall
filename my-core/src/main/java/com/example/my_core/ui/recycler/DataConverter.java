package com.example.my_core.ui.recycler;


import java.util.ArrayList;

//数据转换处理约束
public abstract class DataConverter {
    //存储数据 会有一个entity 对象

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json){
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData(){
        if(mJsonData == null|| mJsonData.isEmpty()){
            throw new NullPointerException("DATA IS NULL");
        }
        return mJsonData;
    }
}
