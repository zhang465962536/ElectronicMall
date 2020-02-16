package com.example.my_core.wechat;


import android.app.Activity;

import com.example.my_core.app.ConfigKeys;
import com.example.my_core.app.Latte;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class LatteWeChat {
    //获取微信的 信息 比如  APP_ID APP_
    public static final String APP_ID = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_ID.name());
    public static final String APP_SECRET = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_SECRET.name());
    private final IWXAPI WXAPI;

    private static final class Holder{
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static LatteWeChat getInstance(){
        return Holder.INSTANCE;
    }

    private LatteWeChat(){
        final Activity activity = Latte.getConfiguration(ConfigKeys.ACTIVITY.name());
        WXAPI = WXAPIFactory.createWXAPI(activity,APP_ID,true);
        WXAPI.registerApp(APP_ID);
    }

    //向微信客户端发送请求 请求登录
    public final  IWXAPI getWXAPI(){
        return WXAPI;
    }

    public final void signIn(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }


}
