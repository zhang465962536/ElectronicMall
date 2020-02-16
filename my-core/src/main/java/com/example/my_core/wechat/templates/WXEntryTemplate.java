package com.example.my_core.wechat.templates;

import com.example.my_core.activities.ProxyActivity;
import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.wechat.BaseWXActivity;
import com.example.my_core.wechat.BaseWXEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

//微信入口模板
public class WXEntryTemplate extends BaseWXEntryActivity {
    //微信登录完成之后 会返回的是 WXEntryTemplate 这个Activity。这个Activity是不会自动取消的 即登录完成之后 会回到我们不想回到的页面
    //将该Activity 设置成透明的 然后等登录完成之后马上finish掉
    @Override
    protected void onSignInSuccess(String userInfo) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //再次进来的时候 finish掉
        finish();
        //禁止掉所有的动画
        overridePendingTransition(0,0);
    }
}
