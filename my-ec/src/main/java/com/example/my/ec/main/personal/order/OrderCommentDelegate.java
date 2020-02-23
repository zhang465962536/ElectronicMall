package com.example.my.ec.main.personal.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.ui.widget.StarLayout;
import com.example.my_core.util.log.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

//订单评价
public class OrderCommentDelegate extends LatteDelegate {

    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout = null;

    @OnClick(R2.id.top_tv_comment_commit)
    void onClickSubmit(){
        ToastUtil.QuickToast("评分  : " + mStarLayout.getStartCount() + " 颗星 ");
    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
