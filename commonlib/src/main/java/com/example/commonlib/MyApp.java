package com.example.commonlib;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;


public class MyApp extends MultiDexApplication {
    /**
     * 上下文
     */

    private static MyApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        UMConfigure.setLogEnabled(false);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "84ce8f7be275b278fd190c3ebb2ae6bc");
        PlatformConfig.setWeixin("wxc329f4902defc332", "0a862006e67e9abd75d07140afac12b7");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d(TAG, "onSuccess: deviceToken-->" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d(TAG, "onFailure: s-->" + s);
                Log.d(TAG, "onFailure: s1-->" + s1);
            }
        });
        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            /**
             * 自定义通知栏样式的回调方法
             */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                                R.layout.upush_notification);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon1, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_large_icon2,
                                getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler(){

            @Override
            public void dealWithCustomAction(Context context, UMessage msg){
                Log.e(TAG,"click");
            }

        };

        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        initX5();
    }

    private static final String TAG = "MyApp";

    public static Context getInstance() {
        return instance;
    }

    /**
     * 初始化X5
     */
    private void initX5() {
        //x5內核初始化回调
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {


            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onViewInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.preInit(getApplicationContext(), cb);
    }




}
