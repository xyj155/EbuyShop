package com.xuyijie.ebuyshop;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;


public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean user = (Boolean) SharePreferenceUtil.getUser("islogin", "boolean");
                if (user) {
                    ARouter.getInstance().build(RouterUtil.HomePage).navigation();
                } else {
                    ARouter.getInstance().build(RouterUtil.LOGIN).navigation();

                }
                finish();
            }
        }, 1);

    }

    private static final String TAG = "SplashActivity";


}
