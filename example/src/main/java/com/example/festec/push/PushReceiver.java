package com.example.festec.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.example.festec.ExampleActivity;
import com.example.my_core.util.log.LatteLogger;

import org.json.JSONException;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //判断极光推送的所有信息
        final Bundle bundle = intent.getExtras();
        final Set<String> keys = bundle.keySet();
        final JSONObject json = new JSONObject();
        for(String key:keys){
            final Object val = bundle.get(key);
            json.put(key,val);
        }
        LatteLogger.json("PushReceiver",json.toJSONString());

        final String pushAction = intent.getAction();
        if(pushAction.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)){
            //处理接收到的信息
            onReceivedMessage(bundle);
        }else if(pushAction.equals(JPushInterface.ACTION_NOTIFICATION_OPENED)){
            //打开相应的Notification
            onOpenNotification(context,bundle);
        }

    }

    //处理接收到的信息
    private void onReceivedMessage(Bundle bundle) {
        final String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        final String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        final int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        final String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
    }

    private void onOpenNotification(Context context,Bundle bundle){
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        //Bundle 传递信息 比如进入该app之后 跳转到哪个fragment
        final Bundle openActivityBundle = new Bundle();
        //当点击Notification的时候  就进入了 唯一的Activity  这样就实现了 后台运行app的时候 收到通知可以及时打开app
        final Intent intent = new Intent(context, ExampleActivity.class);
        intent.putExtras(openActivityBundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ContextCompat.startActivity(context, intent, null);

    }
}
