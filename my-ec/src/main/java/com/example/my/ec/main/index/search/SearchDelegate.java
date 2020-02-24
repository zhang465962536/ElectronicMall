package com.example.my.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.ui.recycler.MultipleItemEntity;
import com.example.my_core.util.storage.LattePreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchDelegate extends LatteDelegate {

    @BindView(R2.id.rv_search)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.et_search_view)
    EditText mSearchEdit = null;

    @OnClick(R2.id.tv_top_search)
    void onClickSearch(){

        //向服务器 搜索
        final String searchItemText = mSearchEdit.getText().toString();
        saveItem(searchItemText);
    }

    @OnClick(R2.id.icon_top_search_back)
    void onClickBack(){
        getSupportDelegate().pop();
    }

    private void saveItem(String item){
        if(!StringUtils.isEmpty(item) && !StringUtils.isSpace(item)){  //如果搜索内容 不为空 并且 不是全都是空格
            List<String> history;
            final String historyStr = LattePreference.getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY);
            if(StringUtils.isEmpty(historyStr)){
                history = new ArrayList<>();
            }else {
                //将JSON数据转换成  Arraylist 特有的结构
                history = JSON.parseObject(historyStr,ArrayList.class);
            }
            history.add(item);
            //将 Arraylist 转换成JSON
            final String json = JSON.toJSONString(history);

            LattePreference.addCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY,json);

        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        final List<MultipleItemEntity> data = new SearchDataConverter().convert();
        final SearchAdapter adapter = new SearchAdapter(data);
        mRecyclerView.setAdapter(adapter);

        //添加分割线
        final DividerItemDecoration itemDecoration = new DividerItemDecoration();
        itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            //纵向分割线
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            //横向分割线
            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(20,20)
                        .color(Color.GRAY)
                        .build();
            }
        });

        //添加分割线
        mRecyclerView.addItemDecoration(itemDecoration);
    }
}
