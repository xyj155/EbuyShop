package com.example.commonlib;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.commonlib.commonactivity.ShopServiceConversationActivity;
import com.example.commonlib.util.ApplicationInitial;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.uuch.adlibrary.utils.DisplayUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;


public class MyApp extends MultiDexApplication {


    private static MyApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JMessageClient.registerEventReceiver(this);
        JMessageClient.setDebugMode(true);
        JMessageClient.init(MyApp.getInstance(), true);
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        ApplicationInitial applicationInitial = new ApplicationInitial();
        applicationInitial
                .initArouter()
                .initBuygly()
                .initEpay()
                .initGT()
                .initIMClient()
                .initJpush()
                .initMob()
                .initPermission()
                .initToast()
                .initX5();

        initDisplayOpinion();
        Fresco.initialize(this);
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }

    public void onEventMainThread(MessageEvent event) {
        //do your own business
        Log.i(TAG, "onEventMainThread: ");
    }

    public void onEvent(NotificationClickEvent event) {
        Intent notificationIntent = new Intent(MyApp.getInstance(), ShopServiceConversationActivity.class);
        notificationIntent.putExtra("username", event.getMessage().getFromUser().getUserName());
        startActivity(notificationIntent);//自定义跳转到指定页面
    }

    private static final String TAG = "MyApp";

    public static Application getInstance() {
        return instance;
    }
}
