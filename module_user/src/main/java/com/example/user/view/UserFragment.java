package com.example.user.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.commonactivity.BrowserActivity;
import com.example.commonlib.gson.UserPaymentGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.ObservableScrollView;
import com.example.commonlib.view.WaveView;
import com.example.user.contract.UserPaymentContract;
import com.example.user.presenter.UserPaymentPresenter;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route(path = RouterUtil.Me_Fragment_Main)
public class UserFragment extends BaseFragment<UserPaymentPresenter> implements UserPaymentContract.View {
    @BindView(R2.id.tv_waitpay)
    TextView tvWaitpay;
    @BindView(R2.id.iv_setting)
    ImageView ivSetting;
    @BindView(R2.id.tv_my_response)
    TextView tvMyResponse;
    @BindView(R2.id.tv_waitsend)
    TextView tvWaitsend;
    @BindView(R2.id.tv_wait_receiver)
    TextView tvWaitReceiver;
    @BindView(R2.id.tv_wait_evaluate)
    TextView tvWaitEvaluate;
    Unbinder unbinder1;
    @BindView(R2.id.tv_substitute_pay)
    TextView tvSubstitutePay;
    @BindView(R2.id.tv_substitute_ship)
    TextView tvSubstituteShip;
    @BindView(R2.id.tv_substitute_collection)
    TextView tvSubstituteCollection;
    @BindView(R2.id.tv_substitute_evluate)
    TextView tvSubstituteEvluate;
    @BindView(R2.id.tv_my_collection)
    TextView tvMyCollection;
    @BindView(R2.id.tv_my_recruit)
    TextView tvMyRecruit;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.rl_orders)
    RelativeLayout rlOrder;
    @BindView(R2.id.sv_mine)
    ObservableScrollView svMine;
    @BindView(R2.id.rl_toolbar)
    RelativeLayout rlToolbar;
    private WaveView waveView;
    private ImageView ivHead;
    @BindView(R2.id.tv_my_vip)
    TextView tvCoupon;
    Unbinder unbinder;
    private int mHeight;

    @Override
    public void initData() {

    }


    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        tvUsername.setText(String .valueOf(SharePreferenceUtil.getUser("username","String")).replace(String .valueOf(SharePreferenceUtil.getUser("username","String")).substring(3,7),"****"));
        waveView = view.findViewById(R.id.wave_view);
        ivHead = view.findViewById(R.id.ivHead);
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0, 0, 0, (int) y + 18);
                ivHead.setLayoutParams(lp);
            }
        });
        Log.i(TAG, "initView: " + SharePreferenceUtil.getUser("avatar", "String"));
        Glide.with(getContext()).asBitmap().apply(new RequestOptions().error(R.mipmap.ic_user_avatar_bg)).load(SharePreferenceUtil.getUser("avatar", "String")).into(ivHead);
        Log.i(TAG, "initView: queryUserOrderCount" + String.valueOf(SharePreferenceUtil.getUser("uid", "1")));
        ViewTreeObserver viewTreeObserver = rlToolbar.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlToolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = rlToolbar.getHeight();//这里取的高度应该为图片的高度-标题栏
                svMine.setOnObservableScrollViewListener(new ObservableScrollView.OnObservableScrollViewListener() {
                    @Override
                    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {

                        if (t <= 0) {
                            //顶部图处于最顶部，标题栏透明
                            rlToolbar.setBackgroundColor(Color.argb(0, 255, 255, 255));
                        } else if (t > 0 && t < mHeight) {
                            float scale = (float) t / mHeight;//算出滑动距离比例
                            float alpha = (255 * scale);//得到透明度
                            rlToolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        } else {
                            rlToolbar.setBackgroundColor(Color.argb(255, 255, 255, 255));
                        }

                    }
                });
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryUserOrderCount((String) SharePreferenceUtil.getUser("uid", "String"));
    }

    private static final String TAG = "UserFragment";

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public UserPaymentPresenter initPresenter() {
        return new UserPaymentPresenter(this);
    }


    @OnClick({R2.id.iv_setting, R2.id.tv_my_recruit, R2.id.tv_my_collection, R2.id.tv_my_response, R2.id.rl_orders, R2.id.tv_my_vip, R2.id.tv_waitpay, R2.id.tv_waitsend, R2.id.tv_wait_receiver, R2.id.tv_wait_evaluate})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_my_vip) {
            Intent intent = new Intent(getContext(), UserCouponActivity.class);
            intent.putExtra("isUse", true);
            startActivity(intent);
        } else if (id == R.id.tv_waitpay) {
            Intent intent = new Intent(getContext(), UserFormStatusActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
        } else if (id == R.id.tv_waitsend) {
            Intent intent = new Intent(getContext(), UserFormStatusActivity.class);
            intent.putExtra("index", 2);
            startActivity(intent);
        } else if (id == R.id.tv_wait_receiver) {
            Intent intent = new Intent(getContext(), UserFormStatusActivity.class);
            intent.putExtra("index", 3);
            startActivity(intent);
        } else if (id == R.id.tv_wait_evaluate) {
            Intent intent = new Intent(getContext(), UserFormStatusActivity.class);
            intent.putExtra("index", 4);
            startActivity(intent);
        } else if (id == R.id.rl_orders) {
            startActivity(new Intent(getContext(), UserAllOrdersActivity.class));
        } else if (id == R.id.tv_my_response) {
            startActivity(new Intent(getContext(), UserFeedBackActivity.class));
        } else if (id == R.id.tv_my_collection) {
            startActivity(new Intent(getContext(), UserCollectionActivity.class));
        } else if (id == R.id.tv_my_recruit) {
            Intent intent = new Intent(getContext(), BrowserActivity.class);
            intent.putExtra("url", RetrofitUtils.BASE_URL + "/StuShop/public/index.php/index/Index/recruitView");
            startActivity(intent);
        } else if (id == R.id.iv_setting) {
            startActivity(new Intent(getContext(), UserSettingActivity.class));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void setUserOrderCount(UserPaymentGson userOrderCount) {
        Log.i(TAG, "setUserOrderCount: " + userOrderCount.toString());
        if (userOrderCount.getCollectionGoods() > 0) {
            tvSubstituteCollection.setText(userOrderCount.getCollectionGoods() + "");
            tvSubstituteCollection.setVisibility(View.VISIBLE);
        }else {
            tvSubstituteCollection.setVisibility(View.GONE);
        }
        if (userOrderCount.getSubstitutePayment() > 0) {
            tvSubstitutePay.setText(userOrderCount.getSubstitutePayment() + "");
            tvSubstitutePay.setVisibility(View.VISIBLE);
        }else {
            tvSubstitutePay.setVisibility(View.GONE);
        }
        if (userOrderCount.getSubstitutesEvaluated() > 0) {
            tvSubstituteEvluate.setText(userOrderCount.getSubstitutesEvaluated() + "");
            tvSubstituteEvluate.setVisibility(View.VISIBLE);
        }else {
            tvSubstituteEvluate.setVisibility(View.GONE);
        }
        if (userOrderCount.getSubstituteShipment() > 0) {
            tvSubstituteShip.setText(userOrderCount.getSubstituteShipment() + "");
            tvSubstituteShip.setVisibility(View.VISIBLE);
        }else {
            tvSubstituteShip.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog(msg);
    }

    @Override
    public void hideDialog() {
        dialogCancel();
    }
}
