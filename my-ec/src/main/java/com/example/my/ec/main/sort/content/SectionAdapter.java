package com.example.my.ec.main.sort.content;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.my.ec.R;

import java.util.List;

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {


    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    //对于头数据的转换
    @Override
    protected void convertHead(BaseViewHolder holder, SectionBean item) {
        holder.setText(R.id.header,item.header);
        holder.setVisible(R.id.more,item.isMore());
        holder.addOnClickListener(R.id.more);
    }

    //对于商品数据转换
    @Override
    protected void convert(@NonNull BaseViewHolder holder, SectionBean item) {
        //item.t 返回SectionBean类型
        final String thumb = item.t.getGoodsThumb();
        final String name = item.t.getGoodsName();
        final int goodsid = item.t.getGoodsId();
        final SectionContentItemEntity entity = item.t;
        holder.setText(R.id.tv,name);
        final ImageView goodsImageView = holder.getView(R.id.iv);
        Glide.with(mContext)
                .load(thumb)
                .into(goodsImageView);


    }
}
