package com.example.my_core.util.timer;

import java.util.TimerTask;

//计时器 倒计时
public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    //在构造方法传入回调
    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if(mITimerListener != null){
            mITimerListener.onTimer();
        }
    }
}
