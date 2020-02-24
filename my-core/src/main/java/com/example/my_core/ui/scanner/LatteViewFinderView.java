package com.example.my_core.ui.scanner;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import com.example.my_core.R;

import me.dm7.barcodescanner.core.ViewFinderView;

public class LatteViewFinderView extends ViewFinderView {

    public LatteViewFinderView(Context context) {
        this(context,null);
    }

    public LatteViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        //设置扫描框为正方形
        mSquareViewFinder = true;
        //设置边框颜色
        mBorderPaint.setColor(ContextCompat.getColor(getContext(), R.color.app_main));
        mLaserPaint.setColor(ContextCompat.getColor(getContext(), R.color.app_main));
    }
}
