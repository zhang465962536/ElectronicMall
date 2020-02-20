package com.example.my.ec.main.sort.list;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.my.ec.R;
import com.example.my.ec.main.sort.SortDelegate;
import com.example.my.ec.main.sort.content.ContentDelegate;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.ui.recycler.ItemType;
import com.example.my_core.ui.recycler.MultipleFields;
import com.example.my_core.ui.recycler.MultipleItemEntity;
import com.example.my_core.ui.recycler.MultipleRecyclerAdapter;
import com.example.my_core.ui.recycler.MultipleViewHolder;
import com.example.my_core.util.log.ToastUtil;

import java.util.List;

public class SortRecyclerAdapter extends MultipleRecyclerAdapter {

    private final SortDelegate DELEGATE;
    //记录上一个点击位置
    private int mPrePosition = 0;

    //需要将 fragment 页面传入适配器 这样才能在Adapter控制左右的关联关系
    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        //添加垂直菜单布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                //获取Item信息
                final String text = entity.getField(MultipleFields.TEXT);
                System.out.println("text " + text);
                final boolean isClicked = entity.getField(MultipleFields.TAG);
                final TextView name = holder.getView(R.id.tv_vertical_item_name);
                final View line = holder.getView(R.id.view_line);
                //获取整个Item
                final View itemView = holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //记录当前点击的位置
                        final int currentPosition = holder.getAdapterPosition();
                        if(mPrePosition != currentPosition){
                            //还原上一个 点击状态
                            getData().get(mPrePosition).setField(MultipleFields.TAG,false);
                            notifyItemChanged(mPrePosition);

                            //更新选中的item
                            entity.setField(MultipleFields.TAG,true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;


                            final int contentId = getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });

                if(!isClicked) {  //如果该Item没有被点击
                    //横线就隐藏
                    line.setVisibility(View.INVISIBLE);
                    //颜色变色
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                }else { //被选中
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }

                holder.setText(R.id.tv_vertical_item_name, text);

                break;
            default:
                break;
        }
    }

    //显示右侧的内容
    private void showContent(int contentId){
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate){
        final LatteDelegate contentDelegate  = DELEGATE.findChildFragment(ContentDelegate.class);
        if(contentDelegate != null){
            //是否要加入返回栈
            contentDelegate.replaceFragment(delegate,false);
        }
    }
}