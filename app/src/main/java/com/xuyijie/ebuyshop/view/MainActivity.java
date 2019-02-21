package com.xuyijie.ebuyshop.view;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.MyApp;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.BrowserActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.gson.AdvertisementGson;
import com.example.commonlib.gson.PopAdvertisementGson;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.presenter.LoginPresent;
import com.example.commonlib.util.JpushUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.MyDialog;
import com.example.home.view.HomeFragment;
import com.example.kind.view.KindFragment;
import com.uuch.adlibrary.AdConstant;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.uuch.adlibrary.transformer.DepthPageTransformer;
import com.xuyijie.ebuyshop.R;
import com.xuyijie.ebuyshop.contract.AdvertisementContract;
import com.xuyijie.ebuyshop.presenter.AdvertisementPresenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


@Route(path = RouterUtil.HomePage)
public class MainActivity extends BaseActivity<HomeContract.View, LoginPresent> implements HomeContract.View, AdvertisementContract.View {


    private RadioGroup bottomBar;

    private MyDialog myDialog1;
    private FragmentManager supportFragmentManager;
    Fragment homeFragment;
    Fragment messageFragment;
    Fragment kindFragment;
    Fragment goodsCarFragment;
    Fragment userFragment;


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


    private void setTagAndAlias() {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        if (!TextUtils.isEmpty((String) SharePreferenceUtil.getUser("uid", "String"))) {
            tags.add(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));//设置tag
        }
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setAliasAndTags(MyApp.getInstance(), String.valueOf(SharePreferenceUtil.getUser("uid", "String")), tags,
                mAliasCallback);
        // }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };
    private AdvertisementPresenter advertisementPresenter = new AdvertisementPresenter(this);

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initView() {
        setTagAndAlias();
        advertisementPresenter.queryPopWindowAd();
        bottomBar = findViewById(R.id.bottomBar);
        supportFragmentManager = getSupportFragmentManager();

        bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                final FragmentTransaction transaction = supportFragmentManager.beginTransaction();
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
                            kindFragment = new KindFragment();
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
        boolean user = (boolean) SharePreferenceUtil.getUser("isOpenSound", "boolean");
        JpushUtil.setSoundAndVibrate(user, true);
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

    @Override
    public void loadAdvertisement(AdvertisementGson advertisementGson) {

    }

    @Override
    public void loadEmpty() {

    }

    @Override
    public void queryPopWindowAd(final List<PopAdvertisementGson> popAdvertisementGsons) {
        Log.i(TAG, "queryPopWindowAd: " + popAdvertisementGsons.get(0).getIsShow());
        if (popAdvertisementGsons.get(0).getIsShow().equals("1")) {
            ArrayList<AdInfo> advList = new ArrayList<>();
            for (int i = 0; i < popAdvertisementGsons.size(); i++) {
                AdInfo adInfo = new AdInfo();
                adInfo.setActivityImg(popAdvertisementGsons.get(i).getImgUrl());
                adInfo.setUrl(popAdvertisementGsons.get(i).getWebUrl());
                advList.add(adInfo);
            }
            final AdManager adManager = new AdManager(MainActivity.this, advList);
            adManager.setOverScreen(true)
                    .setPageTransformer(new DepthPageTransformer());
            adManager.setOnImageClickListener(new AdManager.OnImageClickListener() {
                @Override
                public void onImageClick(View view, AdInfo advInfo) {
                    Log.i(TAG, "onImageClick: " + advInfo.getUrl());
                    if (!advInfo.getUrl().isEmpty()) {
                        Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
                        intent.putExtra("url", advInfo.getUrl());
                        startActivity(intent);
                        adManager.dismissAdDialog();
                    } else {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(MainActivity.this, "com.example.commonlib.commonactivity.GoodsDetailActivity"));
                        intent.putExtra("goodsId", String.valueOf(popAdvertisementGsons.get(0).getGoodsId()));
                        startActivity(intent);
                        adManager.dismissAdDialog();
                    }
                }
            });
            adManager.showAdDialog(AdConstant.ANIM_DOWN_TO_UP);
        }

    }
}