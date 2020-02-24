package com.example.my_core.ui.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

//购物车上面的小红圈 记录商品个数
public class CircleTextView extends AppCompatTextView {

    private final Paint PAINT; //背景的画笔
    private final PaintFlagsDrawFilter FILTER;



    //通过xml布局传入的构造方法
    public CircleTextView(Context context) {
        this(context,null);
    }


    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        PAINT = new Paint();
        FILTER = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        PAINT.setColor(Color.WHITE);
        PAINT.setAntiAlias(true);
    }

    public final void setCircleBackground(@ColorInt int color){
        PAINT.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        final int max = Math.max(width,height);
        setMeasuredDimension(max,max);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.setDrawFilter(FILTER);
        canvas.drawCircle(getWidth()/2,getHeight()/2,Math.max(getHeight(),getWidth())/2,PAINT);
        super.draw(canvas);
    }
}
