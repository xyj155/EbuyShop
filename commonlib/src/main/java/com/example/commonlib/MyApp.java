package com.example.commonlib;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.example.commonlib.util.ApplicationInitial;


public class MyApp extends MultiDexApplication {


    private static MyApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        ApplicationInitial applicationInitial = new ApplicationInitial();
        applicationInitial
                .initArouter()
                .initBuygly()
                .initEpay()
                .initGT()
                .initIMClient()
                .initMob()
                .initJpush()
                .initToast()
                .initX5();

    }


    private static final String TAG = "MyApp";

    public static Application getInstance() {
        return instance;
    }
}
