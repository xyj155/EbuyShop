package com.xuyijie.ebuyshop;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.presenter.LoginPresent;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.view.MyDialog;
import com.example.home.fragment.view.HomeFragment;


import java.util.List;


@Route(path = RouterUtil.HomePage)
public class MainActivity extends BaseActivity<HomeContract.View, LoginPresent> implements HomeContract.View {


    private RadioGroup bottomBar;

    private MyDialog myDialog1;
    private FragmentManager supportFragmentManager;
    Fragment homeFragment;
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

    //    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        myDialog1 = new MyDialog(this,R.layout.common_dialog,new int[]{R.id.dialog_btn_close,R.id.dialog_btn_cancel});
//        myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
//            @Override
//            public void onCenterItemClick(MyDialog dialog, View view) {
//
//            }
//        });
//        myDialog1.show();
//    }
    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            myDialog1 = new MyDialog(this, R.layout.common_dialog, new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
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
                    case R.id.rb_chat:
                        if (goodsCarFragment == null) {
                            goodsCarFragment = (Fragment) ARouter.getInstance().build(RouterUtil.ShopCar_Fragment_Main).navigation();
                            transaction.add(R.id.flContainer, goodsCarFragment);
                        } else {
                            transaction.show(goodsCarFragment);
                        }
//                        Fragment chatFragment=(Fragment)ARouter.getInstance().build(RouterUtil.ShopCar_Fragment_Main).navigation();
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
//        onUmengPush();


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
        if (goodsCarFragment != null) {
            transaction.hide(goodsCarFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
    }

    @Override
    public void initData() {

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


}