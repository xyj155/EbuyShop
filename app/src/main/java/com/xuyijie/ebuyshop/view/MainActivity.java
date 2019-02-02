package com.xuyijie.ebuyshop.view;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.presenter.LoginPresent;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.view.MyDialog;
import com.example.home.view.HomeFragment;
import com.xuyijie.ebuyshop.R;

import java.util.List;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


@Route(path = RouterUtil.HomePage)
public class MainActivity extends BaseActivity<HomeContract.View, LoginPresent> implements HomeContract.View {


    private RadioGroup bottomBar;

    private MyDialog myDialog1;
    private FragmentManager supportFragmentManager;
    Fragment homeFragment;
    Fragment messageFragment;
    Fragment kindFragment;
    Fragment goodsCarFragment;
    Fragment userFragment;
    public static final String UPDATE_STATUS_ACTION = "com.xuyijie.ebuyshop.action.UPDATE_STATUS";

    @Override
    public LoginPresent getPresenter() {
        return new LoginPresent(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }


    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            myDialog1 = new MyDialog(this, new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
            myDialog1.setContent("退出后你将无法获取最新优惠推送");
            myDialog1.setTitle("是否退出应用");
            myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
                @Override
                public void onCenterItemClick(MyDialog dialog, View view) {
                    switch (view.getId()) {
                        case R.id.dialog_btn_close:
                            dialog.dismiss();
                            break;
                        case R.id.dialog_btn_cancel:
                            finish();
                            System.exit(0);

                            break;
                    }
                }
            });
            myDialog1.show();
            mExitTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void initView() {
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                hideAllFragment(transaction);
                switch (checkedId) {
                    case R.id.rb_home:
                        if (homeFragment == null) {
                            homeFragment = (Fragment) ARouter.getInstance().build(RouterUtil.Home_Fragment_Main).navigation();
                            transaction.add(R.id.flContainer, homeFragment);
                        } else {
                            transaction.show(homeFragment);
                        }
                        break;
                    case R.id.rb_resource:
                        if (kindFragment == null) {
                            kindFragment = (Fragment) ARouter.getInstance().build(RouterUtil.Kind_Fragment_Main).navigation();
                            transaction.add(R.id.flContainer, kindFragment);
                        } else {
                            transaction.show(kindFragment);
                        }
                        break;
                    case R.id.rb_message:
                        if (messageFragment == null) {
                            messageFragment = (Fragment) ARouter.getInstance().build(RouterUtil.MESSAGE_Fragment_Main).navigation();
                            transaction.add(R.id.flContainer, messageFragment);
                        } else {
                            transaction.show(messageFragment);
                        }
                        break;
                    case R.id.rb_chat:
                        if (goodsCarFragment == null) {
                            goodsCarFragment = (Fragment) ARouter.getInstance().build(RouterUtil.ShopCar_Fragment_Main).navigation();
                            transaction.add(R.id.flContainer, goodsCarFragment);
                        } else {
                            transaction.show(goodsCarFragment);
                        }
                        break;
                    case R.id.rb_user:
                        if (userFragment == null) {
                            userFragment = (Fragment) ARouter.getInstance().build(RouterUtil.Me_Fragment_Main).navigation();
                            transaction.add(R.id.flContainer, userFragment);
                        } else {
                            transaction.show(userFragment);
                        }
                        break;
                }
                transaction.commit();
            }
        });
        showFirstPosition();


    }


    private void showFirstPosition() {
        supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.flContainer, homeFragment);
        transaction.commit();
    }

    public void hideAllFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (kindFragment != null) {
            transaction.hide(kindFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (goodsCarFragment != null) {
            transaction.hide(goodsCarFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
    }

    @Override
    public void initData() {
        CustomPushNotificationBuilder builder = new
                CustomPushNotificationBuilder(MainActivity.this,
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
    }


    @Override
    public void showError(String msg) {
        Log.i(TAG, "showError: " + msg);
    }

    @Override
    public void showDialog(String msg) {

    }


    @Override
    public void hideDialog() {

    }

    @Override
    public void loadUser(List<UserGson> userGson) {
        Log.i(TAG, "loadUser: " + userGson);
    }


    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}