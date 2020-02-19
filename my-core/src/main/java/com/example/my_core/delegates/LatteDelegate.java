package com.example.my_core.delegates;

//正式使用的Delegate 是个抽象类 不打算让别人直接使用
public abstract class LatteDelegate extends PermissionCheckerDelegate {

    //获取当前fragment的父亲fragment
    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
