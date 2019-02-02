package com.example.commonlib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.MyApp;
import com.example.commonlib.R;
import com.example.commonlib.commonactivity.ShopServiceConversationActivity;
import com.example.commonlib.view.toast.ToastUtils;
import com.getui.gis.sdk.GInsightManager;
import com.mob.MobSDK;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.payelves.sdk.EPay;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.ui.UILifecycleListener;
import com.tencent.smtt.sdk.QbSdk;

import java.io.IOException;
import java.text.SimpleDateFormat;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class ApplicationInitial {
    public ApplicationInitial initArouter() {
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(MyApp.getInstance());
        return this;
    }

    public ApplicationInitial initEpay() {
        EPay.getInstance(MyApp.getInstance()).init("wAwS4BHkB", "1b0ccf51458c4053ae2931772fbbfb97",
                "7169149861036033", "baidu");
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
        Beta.initDelay = 6 * 1000;
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        Beta.smallIconId = R.mipmap.app_icon;
        Beta.strUpgradeDialogCancelBtn = MyApp.getInstance().getString(R.string.text_remind_late);
        Beta.strNetworkTipsConfirmBtn = MyApp.getInstance().getString(R.string.update_now);
        Beta.strUpgradeDialogUpgradeBtn = MyApp.getInstance().getString(R.string.update_now);
        Beta.strUpgradeDialogInstallBtn = MyApp.getInstance().getString(R.string.install_now);
        Beta.upgradeDialogLifecycleListener = new UILifecycleListener<UpgradeInfo>() {
            @Override
            public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {


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

    public ApplicationInitial initIMClient() {
        NIMClient.init(MyApp.getInstance(), loginInfo(), options());
        return this;
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = ShopServiceConversationActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.app_icon;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        String sdkPath = getAppCacheDir(MyApp.getInstance()) + "/nim"; // 可以不设置，那么将采用默认路径
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;
        WindowManager wm = (WindowManager) MyApp.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        options.thumbnailSize = wm.getDefaultDisplay().getWidth() / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionType, String sessionId) {
                return null;
            }
        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        return null;
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

    public ApplicationInitial initX5() {
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
        QbSdk.preInit(MyApp.getInstance(), cb);
        return this;
    }
}
