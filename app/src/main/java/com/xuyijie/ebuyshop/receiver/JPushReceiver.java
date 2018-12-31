package com.xuyijie.ebuyshop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xuyijie.ebuyshop.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class JPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent pushintent=new Intent(context,PushService.class);//启动极光推送的服务
//        context.startService(pushintent);
        Bundle bundle = intent.getExtras();
        //
        if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        } else if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
            chooseMyActivity(context, bundle);
        } else if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
            chooseMyActivity(context, bundle);
        }
    }

    // send msg to MyActivity
    private void chooseMyActivity(Context context, Bundle bundle) {
        // 打开自定义的Activity
        JSONObject jo = null;
        try {
            jo = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            int code = Integer.parseInt(jo.getString("code"));
            bundle.clear();
            switch (code) {
                case 100://100：url,
                    bundle.putString("Code", 100 + "");
                    bundle.putString("url", jo.getString("param"));
                    startMyActivity(context, bundle, MainActivity.class);
                    break;
                case 101://101:纯文本
                    bundle.putString("Code", 101 + "");
                    bundle.putString("param", jo.getString("param"));
                    startMyActivity(context, bundle, MainActivity.class);
                    break;

                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startMyActivity(Context context, Bundle bundle, Class<?> name) {
        Intent i = new Intent(context, name);
        i.putExtras(bundle);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(i);
    }
}
