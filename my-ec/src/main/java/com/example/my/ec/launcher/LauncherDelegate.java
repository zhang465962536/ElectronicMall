package com.example.my.ec.launcher;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.ui.launcher.ScrollLauncherTag;
import com.example.my_core.util.storage.LattePreference;
import com.example.my_core.util.timer.BaseTimerTask;
import com.example.my_core.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

//启动页
public class LauncherDelegate extends LatteDelegate implements ITimerListener {
    @BindView(R2.id.tv_launcher_timer)
    TextView mTvTimer = null;

    private Timer mTimer = null;
    //定义倒计时 数字
    private int mCount = 3;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
            start(new LauncherScrollDelegate(),SINGLETASK);
            //checkIsShowScroll();
        }
    }

    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0,1000);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    //是否展示第一次启动的轮播图
    private void checkIsShowScroll(){
        if(!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){  //如果是第一次进入该app 展示轮播图
            start(new LauncherScrollDelegate(),SINGLETASK);
        }else {
            //检查用户是否已经登录
        }
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTvTimer != null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount --;
                    if(mCount < 0){
                        if(mTimer != null){
                            mTimer.cancel();
                            mTimer = null;
                            //checkIsShowScroll();
                            start(new LauncherScrollDelegate(),SINGLETASK);
                        }
                    }
                }
            }
        });
    }
}
