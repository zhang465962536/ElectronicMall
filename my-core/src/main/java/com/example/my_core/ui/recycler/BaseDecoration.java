package com.example.my_core.ui.recycler;

import androidx.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

public class BaseDecoration extends DividerItemDecoration {

    public BaseDecoration(@ColorInt int color,int size){
         setDividerLookup(new DividerLookupImp(color,size));
    }

    public static BaseDecoration create(@ColorInt int color,int size){
        return new BaseDecoration(color,size);
    }
}
