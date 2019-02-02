package com.xuyijie.ebuyshop.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.AdvertisementGson;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.xuyijie.ebuyshop.R;
import com.xuyijie.ebuyshop.contract.AdvertisementContract;
import com.xuyijie.ebuyshop.presenter.AdvertisementPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends BaseActivity<AdvertisementContract.View, AdvertisementPresenter> implements AdvertisementContract.View {


    @BindView(R.id.iv_ad)
    ImageView ivAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public AdvertisementPresenter getPresenter() {
        return new AdvertisementPresenter(this);
    }

    @Override
    public int intiLayout() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mPresenter.queryFlashAdvertisement();
    }

    private static final String TAG = "SplashActivity";


    @Override
    public void loadAdvertisement(AdvertisementGson advertisementGson) {
        GlideUtil.loadGeneralImage(advertisementGson.getBannerUrl(), ivAd);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean user = (boolean) SharePreferenceUtil.getUser("islogin", "boolean");
                if (user) {
                    ARouter.getInstance().build(RouterUtil.HomePage).navigation();
                } else {
                    ARouter.getInstance().build(RouterUtil.LOGIN).navigation();
                }
                finish();
            }
        }, 4000);
    }

    @Override
    public void loadEmpty() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean user = (boolean) SharePreferenceUtil.getUser("islogin", "boolean");
                if (user) {
                    ARouter.getInstance().build(RouterUtil.HomePage).navigation();
                } else {
                    ARouter.getInstance().build(RouterUtil.LOGIN).navigation();
                }
                finish();
            }
        }, 4000);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void hideDialog() {

    }
}
