package com.example.my.ec.main.personal.list;

import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.my.ec.R;

import java.util.List;

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();

    public ListAdapter(List<ListBean> data) {
        super(data);
        //添加Item布局  以后将在RecyclerView进行显示
        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout);
        addItemType(ListItemType.ITEM_AVATAR,R.layout.arrow_item_avatar);
        addItemType(ListItemType.ITEM_SWITCH,R.layout.arrow_switch_layout);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, ListBean item) {
        switch (holder.getItemViewType()) {
            case ListItemType.ITEM_NORMAL:
                holder.setText(R.id.tv_arrow_text,item.getText());
                holder.setText(R.id.tv_arrow_value,item.getValue());
                break;

        case ListItemType.ITEM_AVATAR:
            Glide.with(mContext)
                    .load(item.getImageUrl())
                    .apply(OPTIONS)
                    .into((ImageView)holder.getView(R.id.img_arrow_avatar));
            break;
            case ListItemType.ITEM_SWITCH:
                holder.setText(R.id.tv_arrow_switch_text,item.getText());
                final SwitchCompat switchCompat = holder.getView(R.id.list_item_switch);
                switchCompat.setChecked(true);  //默认允许消息推送
                switchCompat.setOnCheckedChangeListener(item.getmOnCheckedChangeListener());
                break;
            default:
                break;
        }
    }
}
