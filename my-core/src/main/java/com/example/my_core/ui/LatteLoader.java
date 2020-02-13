package com.example.my_core.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;

import com.example.my_core.R;
import com.example.my_core.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class LatteLoader {

    //设置加载窗体的缩放比
    private static final int LOADER_SIZE_SCALE = 8;
    //加载窗体的偏移量
    private static final int LOADER_OFFSET_SCALE = 10;

    //存放所有Load 窗体的 集合 方便管理
    // 在不需要 Load 或者 想统一地关闭 Load 时候  只需要遍历 LOADERS 一一关闭即可 省下了很多同步的问题
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    //默认提供一种Loading 样式
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    //有枚举类参数的showLoading
    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    //展示加载圈
    public static void showLoading(Context context, String type) {
        //使用Dialog展示加载圈
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        //获取屏幕宽高
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow  = dialog.getWindow();

        //设置加载窗体的宽高属性
        if(dialogWindow != null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight/LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    //重载showLoading()  如果传入的是 ApplicationContext 会在WebView 或者其他View 上 会报错 最好传入当前的Context
    // 如果使用在Fragment上 可以传入Fragment.getContext() 如果使用在Activity 传入Activity的Context
    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    //停止Dialog
    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADERS){
            if(dialog != null){
                if(dialog.isShowing()){
                    dialog.cancel();
                    // dialog.dismiss();  dialog.cancel(); 区别。 cancel()会执行onCancel 回调  dismiss()只是单纯的消失掉他
                }
            }
        }
    }
}
