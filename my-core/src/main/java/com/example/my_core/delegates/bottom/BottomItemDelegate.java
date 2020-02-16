package com.example.my_core.delegates.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.my_core.R;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.util.log.ToastUtil;

//底部栏每一个Item 的页面
public abstract class BottomItemDelegate extends LatteDelegate implements View.OnKeyListener {

    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if(rootView != null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    //连续点击返回键 退出app
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > EXIT_TIME) {
                ToastUtil.QuickToast("双击退出" + getString(R.string.app_name));
                mExitTime = System.currentTimeMillis();
            } else { //连续两次返回键直接退出程序
                _mActivity.finish();
                if (mExitTime != 0) {
                    mExitTime = 0;
                }
            }
            return true;
        }

        return false;

    }
}
