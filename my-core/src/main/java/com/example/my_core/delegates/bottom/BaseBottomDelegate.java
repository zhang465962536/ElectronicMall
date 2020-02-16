package com.example.my_core.delegates.bottom;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.my_core.R;
import com.example.my_core.R2;
import com.example.my_core.delegates.LatteDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

//底部选择栏
public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {

    //存储所有的子fragment
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATE = new ArrayList<>();
    //存储所有的子bean
    private final ArrayList<BottomTabBean> TAB_BEAN = new ArrayList<>();
    //存储 fragment 和 bean 的映射
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    //记录当前显示的fragment
    private int mCurrentDelegate = 0;
    //进入app显示的fragment
    private int mIndexDelegate = 0;
    //点击底部栏Item变色的颜色
    private int mClickedColor = Color.RED;

    @BindView(R2.id.bottom_bar)
    LinearLayout mBottomBar = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

    public abstract int setmIndexDelegate();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setmIndexDelegate();
        if (mClickedColor != 0) {
            mClickedColor = setClickedColor();
        }

        //构建键值对
        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        //遍历获取
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEAN.add(key);
            ITEM_DELEGATE.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        //循环将 LinearLayout mBottomBar的子元素 的 bottom_item_icon_text_layout View 取出来
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            //取出每一个item
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //设置每个Item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            //获取 bottom_item_icon_text_layout 试图里面的  IconTextView
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            //获取 bottom_item_icon_text_layout 试图里面的  TextView
            final TextView itemTitle = (TextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEAN.get(i);
            //初始化数据
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            //如果循环的角标正好等于要展示的fragment
            if(i == mIndexDelegate){
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }

         //跳出循环 初始化 要展示的fragment 主界面
        final SupportFragment[] delegateArray = ITEM_DELEGATE.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container,mIndexDelegate,delegateArray);
    }

    //重置底部栏Item颜色
    private void resetColor(){
        final int count = mBottomBar.getChildCount();
        for(int i = 0;i < count;i ++){
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final TextView itemTitle = (TextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
            itemIcon.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        final TextView itemTitle = (TextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
        itemIcon.setTextColor(mClickedColor);
        //点击之后 显示 tag fragment   然后隐藏  mCurrentDelegate
        showHideFragment(ITEM_DELEGATE.get(tag),ITEM_DELEGATE.get(mCurrentDelegate));
        //注意先后顺序
        mCurrentDelegate = tag;
    }
}
