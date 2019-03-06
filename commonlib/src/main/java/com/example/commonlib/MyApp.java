package com.example.commonlib;

import android.app.Application;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.commonlib.util.ApplicationInitial;
import com.uuch.adlibrary.utils.DisplayUtil;





public class MyApp extends MultiDexApplication {


    private static MyApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ApplicationInitial applicationInitial = new ApplicationInitial();
        applicationInitial
                .initArouter()
                .initialGlide()
                .initBuygly()
                .initEpay()
                .initGT()
                .initIMClient()
                .initJpush()
                .initMob()
                .initRongIm()
                .initPicaso()
                .initToast()
                .initX5();
        initDisplayOpinion();

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



    private static final String TAG = "MyApp";

    public static Application getInstance() {
        return instance;
    }
}
