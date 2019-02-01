package com.example.commonlib.mreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.util.RouterUtil;

import cn.jpush.android.api.JPushInterface;

public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = JpushReceiver.class.getSimpleName();
    private static final int NOTIFICATION_SHOW_SHOW_AT_MOST = 3;   //推送通知最多显示条数

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //
        if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            Log.i(TAG, "接收到了通知");
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.i(TAG, "标题:【" + title + "】，内容：【" + content + "】，附加参数:【" + extra + "】");
        } else if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
            Log.i(TAG, "接收到了消息");
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            Log.i(TAG, "onReceive: title" + title);
            Log.i(TAG, "接收到的消息是:【" + message + "】");
        } else if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
            Log.i(TAG, "用户正在打开通知");
            ARouter.getInstance().build(RouterUtil.HomePage).navigation();
        }
    }

}