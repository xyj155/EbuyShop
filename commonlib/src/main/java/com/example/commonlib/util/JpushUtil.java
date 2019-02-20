package com.example.commonlib.util;

import android.app.Notification;

import com.example.commonlib.MyApp;
import com.example.commonlib.R;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class JpushUtil {

    public static void setSoundAndVibrate(boolean isOpenSound,boolean isOpenVibrate){
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MyApp.getInstance());
        builder.statusBarDrawable = R.mipmap.app_icon;//设置推送的图标
        if (isOpenVibrate && !isOpenSound) {//只有振动
            builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
        } else if (isOpenSound && !isOpenVibrate) {//只有声音
            builder.notificationDefaults = Notification.DEFAULT_SOUND;
        } else if (isOpenSound && isOpenVibrate) {//两个都有
            builder.notificationDefaults = Notification.DEFAULT_ALL;
        } else {//只有呼吸灯
            builder.notificationDefaults = Notification.DEFAULT_LIGHTS;
        }
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }
}
