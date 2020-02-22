package com.example.my.ec.main.personal.list;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.my.ec.R;

import java.util.List;

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    public ListAdapter(List<ListBean> data) {
        super(data);
        //添加Item布局  以后将在RecyclerView进行显示
        addItemType(30, R.layout.arrow_item_avatar);
        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout);
        addItemType(40, R.layout.arrow_switch_layout);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, ListBean item) {
        switch (holder.getItemViewType()) {
            case ListItemType.ITEM_NORMAL:
                holder.setText(R.id.tv_arrow_text,item.getText());
                holder.setText(R.id.tv_arrow_value,item.getValue());
                break;
            default:
                break;
        }
    }
}
