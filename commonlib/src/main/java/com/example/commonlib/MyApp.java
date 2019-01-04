package com.example.commonlib;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;


public class MyApp extends Application {
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
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"");
        UMConfigure.setLogEnabled(false);
//        UMConfigure.init(this,"5c2cd62cb465f580200003c5"
//                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wxc329f4902defc332", "0a862006e67e9abd75d07140afac12b7");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

    }

    public static Context getInstance() {
        return instance;
    }

}
