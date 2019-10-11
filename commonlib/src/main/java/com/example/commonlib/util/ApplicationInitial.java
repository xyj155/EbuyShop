package com.example.commonlib.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;


import com.bumptech.glide.Glide;
import com.example.commonlib.MyApp;
import com.example.commonlib.R;
import com.example.commonlib.view.toast.ToastUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.getui.gis.sdk.GInsightManager;
import com.mob.MobSDK;
import com.payelves.sdk.EPay;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.ui.UILifecycleListener;

import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;

import java.io.IOException;
import java.text.SimpleDateFormat;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import io.rong.imkit.RongIM;


public class ApplicationInitial {
    public ApplicationInitial initArouter() {
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(MyApp.getInstance());
        return this;
    }

    public ApplicationInitial initEpay() {
        EPay.getInstance(MyApp.getInstance()).init("wAwS4BHkB", "1b0ccf51458c4053ae2931772fbbfb97",
                "7778897507581955", "baidu");
        return this;
    }

    public ApplicationInitial initZxing() {
//        ZXingLibrary.initDisplayOpinion(MyApp.getInstance());
        return this;
    }

    public ApplicationInitial initialGlide() {
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        return this;
    }

    public ApplicationInitial initBaiduCount() {
        StatConfig.setDebugEnable(true);
        // 基础统计API
        StatService.registerActivityLifecycleCallbacks(MyApp.getInstance());
        return this;
    }

    public ApplicationInitial initBaiduVR() {
        // 设置获取资源的上下文Context
//        Res.addResource(MyApp.getInstance());
//// 设置App Id
//        DuMixARConfig.setAppId("15791457");
//// 设置API Key
//        DuMixARConfig.setAPIKey("trK2Q1ripnzKQseUCeWrigig");
//// 设置Secret Key
//        DuMixARConfig.setSecretKey("rTvvbzq6m8gh7ZydHP1fem3ALBg9gfAt");
        return this;
    }

    public ApplicationInitial initMob() {
//        MobPush.addTags(java.lang.String[] tags);
        MobSDK.init(MyApp.getInstance(), "29cbff9d24b0b", "83fe8985b2647f0041f9cfb3487492d6");
        return this;
    }

    public ApplicationInitial initJpush() {

        CustomPushNotificationBuilder builder = new
                CustomPushNotificationBuilder(MyApp.getInstance(),
                R.layout.customer_notitfication_layout,
                R.id.icon,
                R.id.title,
                R.id.text);
        // 指定定制的 Notification Layout
        builder.statusBarDrawable = R.mipmap.app_icon;
        // 指定最顶层状态栏小图标
        builder.layoutIconDrawable = R.mipmap.app_icon;
        // 指定下拉状态栏时显示的通知图标
        JPushInterface.setPushNotificationBuilder(2, builder);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(MyApp.getInstance());

        return this;
    }

    public ApplicationInitial initBuygly() {
        Beta.autoCheckUpgrade = true;
        Beta.largeIconId = R.mipmap.app_icon;
        Beta.smallIconId = R.mipmap.app_icon;
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        Beta.strUpgradeDialogCancelBtn = MyApp.getInstance().getString(R.string.text_remind_late);
        Beta.strNetworkTipsConfirmBtn = MyApp.getInstance().getString(R.string.update_now);
        Beta.strUpgradeDialogUpgradeBtn = MyApp.getInstance().getString(R.string.update_now);
        Beta.strUpgradeDialogInstallBtn = MyApp.getInstance().getString(R.string.install_now);
        Beta.upgradeDialogLifecycleListener = new UILifecycleListener<UpgradeInfo>() {
            @Override
            public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {
                Log.i(TAG, "onCreate: " + upgradeInfo);

            }

            @Override
            public void onStart(Context context, View view, UpgradeInfo upgradeInfo) {

            }

            @Override
            public void onResume(Context context, View view, UpgradeInfo upgradeInfo) {

                TextView textView = (TextView) view.findViewWithTag("beta_upgrade_feature");
                StringBuilder info = new StringBuilder();
                info.append("更新日志: ").append(upgradeInfo.newFeature).append("\n\n");
                info.append("版本名称: ").append(upgradeInfo.versionName).append("\n\n");
                info.append("发布时间: ").append(getCurDate("yyyy-mm-dd", upgradeInfo.publishTime)).append("\n\n");
                info.append("安装包大小: ").append(SizeConverter.BTrim.convert(upgradeInfo.fileSize)).append("\n");
                textView.setText(info);

            }

            @Override
            public void onPause(Context context, View view, UpgradeInfo upgradeInfo) {

            }

            @Override
            public void onStop(Context context, View view, UpgradeInfo upgradeInfo) {

            }

            @Override
            public void onDestroy(Context context, View view, UpgradeInfo upgradeInfo) {

            }

        };
        Bugly.init(MyApp.getInstance(), "090ab44906", false);

        return this;
    }

    public static String getCurDate(String pattern, long time) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(time);
    }

    private static final String TAG = "ApplicationInitial";

    public ApplicationInitial initIMClient() {

        //注册全局事件监听类
//        JMessageClient.registerEventReceiver(this);

        return this;
    }


    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * 这里示例用SD卡的应用扩展存储目录
     */
    static String getAppCacheDir(Context context) {
        String storageRootPath = null;
        try {
            // SD卡应用扩展存储区(APP卸载后，该目录下被清除，用户也可以在设置界面中手动清除)，请根据APP对数据缓存的重要性及生命周期来决定是否采用此缓存目录.
            // 该存储区在API 19以上不需要写权限，即可配置 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="18"/>
            if (context.getExternalCacheDir() != null) {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(storageRootPath)) {
            // SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
            storageRootPath = Environment.getExternalStorageDirectory() + "/" + MyApp.getInstance().getPackageName();
        }

        return storageRootPath;
    }

    public ApplicationInitial initGT() {
        GInsightManager.getInstance().init(MyApp.getInstance(), "DsM2BPaiLT89Vw5fRW34d");
        return this;
    }

    public ApplicationInitial initToast() {
        ToastUtils.init(MyApp.getInstance());
        return this;
    }

    public ApplicationInitial initPicaso() {
        Fresco.initialize(MyApp.getInstance());
        return this;

    }

    public ApplicationInitial initRongIm() {
        RongIM.init(MyApp.getInstance(), "kj7swf8ok3nq2");
        return this;
    }

    public ApplicationInitial initX5() {
//        //x5內核初始化回调
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void onViewInitFinished() {
//
//            }
//        };
//        //x5内核初始化接口
//        QbSdk.preInit(MyApp.getInstance(), cb);
        return this;
    }
}
