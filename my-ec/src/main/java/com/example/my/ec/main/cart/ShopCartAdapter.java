package com.example.my.ec.main.cart;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.my.ec.R;
import com.example.my_core.app.Latte;
import com.example.my_core.ui.recycler.ItemType;
import com.example.my_core.ui.recycler.MultipleItemEntity;
import com.example.my_core.ui.recycler.MultipleRecyclerAdapter;
import com.example.my_core.ui.recycler.MultipleViewHolder;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private boolean mIsSelectedAll = false;

    protected static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        //添加购物车Item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public void setIsSelectedAll(boolean isSelectedAll){
        this.mIsSelectedAll = isSelectedAll;
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //取出所有的值
                final int id = entity.getField(ShopCartItemFields.ID);
                final String thumb = entity.getField(ShopCartItemFields.IMAGE_URL);
                final String title = entity.getField(ShopCartItemFields.TITLE);
                final String desc = entity.getField(ShopCartItemFields.DESC);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final double price = entity.getField(ShopCartItemFields.PRICE);
                //取出所有控件
                final ImageView imgThumb = holder.getView(R.id.image_item_shop_cart);
                final TextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final TextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final TextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final TextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);


                //赋值给控件
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb);

                //在左侧选中框 渲染之前全选与否改变状态
                entity.setField(ShopCartItemFields.IS_SELECTED,mIsSelectedAll);
                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                //根据数据状态显示 左侧选中框
                if(isSelected){
                    //选中
                    iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(),R.color.app_main));
                }else {
                    //没有选中
                    iconIsSelected.setTextColor(Color.GRAY);
                }
                //添加左侧选中框点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                        if(currentSelected){
                            //没有选中
                            iconIsSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartItemFields.IS_SELECTED,false);
                        }else {
                            //选中
                            iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(),R.color.app_main));
                            entity.setField(ShopCartItemFields.IS_SELECTED,true);
                        }
                    }
                });

                break;
            default:
                break;
        }
    }
}
