package com.example.my_core.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

//自定义星型评分
import androidx.annotation.Nullable;

import com.example.my_core.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

public class StarLayout extends LinearLayout implements View.OnClickListener {

    private static final CharSequence ICON_UN_SELECT = "{fa-star-o}"; //空心星星
    private static final CharSequence ICON_SELECTED = "{fa-star}";  //实心星星
    private static final int STAR_TOTAL_COUNT = 5;
    //定义List 存储start 数量
    private static final ArrayList<IconTextView> STARS = new ArrayList<>();


    public StarLayout(Context context) {
        this(context, null);
    }

    public StarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStarIcon();
    }
    public int getStartCount(){
        int count = 0;
        for(int i = 0; i <STAR_TOTAL_COUNT; i ++){
            final IconTextView star = STARS.get(i);
            final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);
            if(isSelect){
                count ++;
            }
        }
        return count;
    }

    //初始化星星图标
    private void initStarIcon() {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = new IconTextView(getContext());
            star.setGravity(Gravity.CENTER);
            final LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            star.setLayoutParams(lp);
            star.setText(ICON_UN_SELECT);
            star.setTag(R.id.star_count, i);
            star.setTag(R.id.star_is_select, false);
            star.setOnClickListener(this);
            STARS.add(star);
            this.addView(star);
        }
    }

    private void selectStar(int count) {
        for (int i = 0; i <= count; i++) {
            if (i <= count) {
                final IconTextView star = STARS.get(i);
                star.setText(ICON_SELECTED);
                star.setTextColor(Color.RED);
                star.setTag(R.id.star_is_select, true);
            }
        }
    }


    private void unSelectStar(int count) {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            if (i >= count) {
                //比如 已经点亮了3颗星  点击第4颗星 的时候 将前面4颗星变红 最好一颗星 置空 所以需要判断超出的部分
                final IconTextView star = STARS.get(i);
                star.setText(ICON_UN_SELECT);
                star.setTextColor(Color.GRAY);
                star.setTag(R.id.star_is_select, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        final IconTextView star = (IconTextView) v;
        //获取第几个星星
        final int count = (int) star.getTag(R.id.star_count);
        //获取点击状态
        final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);
        if (!isSelect) {
            selectStar(count);
        } else {
            unSelectStar(count);
        }

    }
}
