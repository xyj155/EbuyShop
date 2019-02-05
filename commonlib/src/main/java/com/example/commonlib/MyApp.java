package com.example.commonlib;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.commonlib.util.ApplicationInitial;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;


public class MyApp extends MultiDexApplication {


    private static MyApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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
                .initMob()
                .initPermission()
                .initJpush()
                .initToast()
                .initX5();

    }


    private static final String TAG = "MyApp";

    public static Application getInstance() {
        return instance;
    }
}
