package com.xuyijie.ebuyshop;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.util.RouterUtil;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;


public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //设置自定义通知栏样式1
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(SplashActivity.this);
        //指定状态栏的小图标
        builder.statusBarDrawable =R.mipmap.app_icon;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);

        //定制带按钮的Notification样式
        MultiActionsNotificationBuilder builder2 = new MultiActionsNotificationBuilder(SplashActivity.this);
        //添加按钮，参数(按钮图片、按钮文字、扩展数据)
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(2, builder2);

        //自定义通知栏样式3
        // 指定定制的 Notification Layout
        CustomPushNotificationBuilder builder3 = new
                CustomPushNotificationBuilder(SplashActivity.this,
                R.layout.fragment_home,
                R.id.icon,
                R.id.title,
                R.id.text);
        // 指定最顶层状态栏小图标
        builder3.statusBarDrawable = R.mipmap.app_icon;
        // 指定下拉状态栏时显示的通知图标
        builder3.layoutIconDrawable = R.mipmap.app_icon;
        JPushInterface.setPushNotificationBuilder(3, builder3);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build(RouterUtil.Splash).navigation();
                finish();
            }
        }, 2500);


    }
}
