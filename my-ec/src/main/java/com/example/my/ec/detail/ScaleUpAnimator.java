package com.example.my.ec.detail;

import android.animation.Animator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;
import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

//加入购物车之后 显示数量的圆圈 放大缩小的 动画
public class ScaleUpAnimator extends BaseViewAnimator {

    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "scaleX", 0.8f, 1f, 1.4f, 1.2f, 1),
                ObjectAnimator.ofFloat(target, "scaleY", 0.8f, 1f, 1.4f, 1.2f, 1)
        );
    }
}
