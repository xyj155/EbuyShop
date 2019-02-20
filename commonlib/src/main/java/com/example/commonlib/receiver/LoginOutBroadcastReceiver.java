package com.example.commonlib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.util.ActivityCollector;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;

public class LoginOutBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharePreferenceUtil.clear();
        ActivityCollector.finishAll();  // 销毁所有活动
        ARouter.getInstance().build(RouterUtil.Splash).navigation();
    }
}
