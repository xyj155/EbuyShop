package com.xuyijie.stushop.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.UserMemberDateContract;
import com.example.commonlib.gson.AdvertisementGson;
import com.example.commonlib.gson.Lottery;
import com.example.commonlib.gson.PopAdvertisementGson;
import com.example.commonlib.presenter.UserMemberDatePresenter;
import com.example.commonlib.service.OnePixelReceiver;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.xuyijie.stushop.R;
import com.xuyijie.stushop.contract.AdvertisementContract;
import com.xuyijie.stushop.contract.LotteryContract;
import com.xuyijie.stushop.presenter.AdvertisementPresenter;
import com.xuyijie.stushop.presenter.LotteryPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterUtil.Splash)
public class SplashActivity extends BaseActivity<AdvertisementContract.View, AdvertisementPresenter> implements AdvertisementContract.View, UserMemberDateContract.View, LotteryContract.View {


    @BindView(R.id.iv_ad)
    ImageView ivAd;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private MyCountDownTimer mc;
    private UserMemberDatePresenter userMemberDatePresenter = new UserMemberDatePresenter(this);
    LotteryPresenter lotteryPresenter=new LotteryPresenter(this);
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
        if (!String.valueOf(SharePreferenceUtil.getUser("uid", "String")).isEmpty()) {
            userMemberDatePresenter.judgementMember(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
        }
        lotteryPresenter.queryLottery("");
        GlideUtil.loadRoundCornerAvatarImage(R.mipmap.app_icon, (ImageView) findViewById(R.id.iv_logo), 30);
        mc = new MyCountDownTimer(4000, 1000);
        mc.start();
        mPresenter.queryFlashAdvertisement();



    }


    @Override
    public void initData() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(new OnePixelReceiver(), filter);
    }

    private static final String TAG = "SplashActivity";

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            boolean user = (boolean) SharePreferenceUtil.getUser("islogin", "boolean");
            Log.i(TAG, "run: " + user);
            if (user) {
                ARouter.getInstance().build(RouterUtil.HomePage).navigation();
            } else {
                ARouter.getInstance().build(RouterUtil.LOGIN).navigation();
            }
            finish();
        }
    };

    @Override
    public void loadAdvertisement(final AdvertisementGson advertisementGson) {
        if (advertisementGson.getIsShow().equals("1")) {
            GlideUtil.loadGeneralImage(advertisementGson.getBannerUrl(), ivAd);
            ivAd.setVisibility(View.VISIBLE);
            ivAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                    intent.putExtra("url", advertisementGson.getWebUrl());
                    startActivityForResult(intent, 88);
                    handler.removeCallbacks(runnable);
                }
            });
        }


    }

    @Override
    public void loadEmpty() {
        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void queryPopWindowAd(List<PopAdvertisementGson> popAdvertisementGsons) {

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

    private Handler handler = new Handler();

    @Override
    public void loadUserMember(int code) {
        if (code != 200) {
            Map<String, Object> map = new HashMap<>();
            map.put("islogin", true);
            map.put("member", "0");
            SharePreferenceUtil.saveUser(map);
        }
    }

    @Override
    public void getLottery(Lottery lottery) {
        Log.i(TAG, "getLottery: "+lottery.getStatus().equals("1"));
        Log.i(TAG, "getLottery: "+lottery.getUrl());
        if (lottery.getStatus().equals("1")){
            Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
            intent.putExtra("url",lottery.getUrl());
            startActivity(intent);
        }else {
            handler.postDelayed(runnable, 4000);
            tvTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean user = (boolean) SharePreferenceUtil.getUser("islogin", "boolean");
                    if (user) {
                        ARouter.getInstance().build(RouterUtil.HomePage).navigation();
                    } else {
                        ARouter.getInstance().build(RouterUtil.LOGIN).navigation();
                    }
                    handler.removeCallbacks(runnable);
                    mc.toStop();
                    finish();
                }
            });
        }
    }

    class MyCountDownTimer extends CountDownTimer {
        private long currrntTime;
        private boolean mCancelled;

        public boolean ismCancelled() {
            return mCancelled;
        }

        public long getCurrrntTime() {
            return currrntTime;
        }


        public void setmCancelled(boolean cancelled) {
            if (mCancelled != cancelled)
                mCancelled = cancelled;
        }

        public void setCurrrntTime(long currrntTime) {
            this.currrntTime = currrntTime;
        }

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        public long toStop() {
            if (!ismCancelled()) {
                setmCancelled(true);
                cancel();
            }
            return getCurrrntTime();
        }

        public void onTick(long millisUntilFinished) {
            tvTime.setText(millisUntilFinished / 1000 + " 秒后跳过");
        }

        @Override
        public void onFinish() {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: "+requestCode+"sdsadsad"+resultCode);
        if (resultCode == 0) {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        }
    }
}
