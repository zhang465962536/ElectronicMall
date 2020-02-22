package com.example.my.ec.main.personal.order;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.my.ec.R;
import com.example.my_core.ui.recycler.MultipleFields;
import com.example.my_core.ui.recycler.MultipleItemEntity;
import com.example.my_core.ui.recycler.MultipleRecyclerAdapter;
import com.example.my_core.ui.recycler.MultipleViewHolder;

import java.util.List;

public class OrderListAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.item_order_list);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case OrderListItemType.ITEM_ORDER_LIST:
                final ImageView imageView = holder.getView(R.id.image_order_list);
                final TextView title = holder.getView(R.id.tv_order_list_title);
                final TextView price = holder.getView(R.id.tv_order_list_price);
                final TextView time = holder.getView(R.id.tv_order_list_time);

                final String titleVal = entity.getField(MultipleFields.TITLE);
                final String timeVal = entity.getField(OrderItemFields.TIME);
                final double priceVal = entity.getField(OrderItemFields.PRICE);
                final String imageUrl = entity.getField(MultipleFields.IMAGE_URL);

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);

                title.setText(titleVal);
                price.setText("价格：" + String.valueOf(priceVal));
                time.setText("时间：" + timeVal);
                break;
            default:
                break;
        }
    }
}
