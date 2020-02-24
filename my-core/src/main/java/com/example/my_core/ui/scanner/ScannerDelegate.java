package com.example.my_core.ui.scanner;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.ui.camera.RequestCodes;
import com.example.my_core.util.callback.CallBackManager;
import com.example.my_core.util.callback.CallBackType;
import com.example.my_core.util.callback.IGlobalCallBack;
import com.example.my_core.util.log.LatteLogger;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerDelegate extends LatteDelegate implements ZBarScannerView.ResultHandler {

    private ScanView mScanView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mScanView == null){
            mScanView = new ScanView(getContext());
        }
        mScanView.setAutoFocus(true); //自动聚焦
        mScanView.setResultHandler(this);
    }

    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) { }

    @Override
    public void handleResult(Result result) {
        //将二维码扫描处理的字符串 放入 回调 传出去
        final IGlobalCallBack<String> callBack = CallBackManager.getInstance().getCallback(CallBackType.ON_SCAN);
        if(callBack != null){
            callBack.executeCallBack(result.getContents());
        }
        //扫描完之后 将扫描器退出栈  关闭扫描
        getSupportDelegate().pop();
    }

    //当界面呈现在用户面前的时候 就开始扫描
    @Override
    public void onResume() {
        super.onResume();
        if(mScanView != null){
            mScanView.startCamera();
        }
    }

    //当界面消失的时候 停止扫描

    @Override
    public void onPause() {
        super.onPause();
        if(mScanView != null){
            mScanView.stopCameraPreview(); //停止预览
            mScanView.stopCamera();
        }
    }
}
