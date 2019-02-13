package com.example.home.view;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.MemberGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.PaymentInterface;
import com.example.commonlib.util.PaymentUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.CircleImageView;
import com.example.commonlib.view.ObservableScrollView;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.home.contract.MembershipOpeningContract;
import com.example.home.presenter.MembershipOpeningPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MembershipOpeningActivity extends BaseActivity<MembershipOpeningContract.View, MembershipOpeningPresenter> implements MembershipOpeningContract.View {


    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tb_common)
    LinearLayout tbCommon;
    @BindView(R2.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.iv_rank)
    ImageView ivRank;
    @BindView(R2.id.ll_user)
    LinearLayout llUser;
    @BindView(R2.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R2.id.rb_rank1)
    CheckBox rbRank1;
    @BindView(R2.id.rb_rank2)
    CheckBox rbRank2;
    @BindView(R2.id.rb_rank3)
    CheckBox rbRank3;
    @BindView(R2.id.ll_recharge)
    LinearLayout llRecharge;
    @BindView(R2.id.sl_member)
    ObservableScrollView slMember;
    @BindView(R2.id.tv_member_price1)
    TextView tvMemberPrice1;
    @BindView(R2.id.tv_member_price2)
    TextView tvMemberPrice2;
    @BindView(R2.id.tv_member_price3)
    TextView tvMemberPrice3;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    public MembershipOpeningPresenter getPresenter() {
        return new MembershipOpeningPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_membership_opening;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        GlideUtil.loadGeneralImage( RetrofitUtils.BASE_URL +SharePreferenceUtil.getUser("avatar", "String"), ivAvatar);
        String username = String.valueOf(SharePreferenceUtil.getUser("username", "String"));
        tvUsername.setText(username.replace(username.substring(3, 7), "****"));
        hideBottomButton();
        ViewTreeObserver viewTreeObserver = tbCommon.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int mHeight;

            @Override
            public void onGlobalLayout() {
                tbCommon.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = tbCommon.getHeight();//这里取的高度应该为图片的高度-标题栏
                //注册滑动监听
                slMember.setOnObservableScrollViewListener(new ObservableScrollView.OnObservableScrollViewListener() {
                    @Override
                    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
                        Log.i(TAG, "onObservableScrollViewListener: " + t);
                        if (t <= 0) {
                            tbCommon.setBackgroundColor(Color.argb(0, 255, 255, 255));
                        } else if (t > 0 && t < mHeight) {
                            float scale = (float) t / mHeight;
                            float alpha = (255 * scale);
                            tbCommon.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        } else {
                            tbCommon.setBackgroundColor(Color.argb(255, 255, 255, 255));
                        }

                    }
                });
            }
        });
        mPresenter.queryMemberPrice();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    private int rank;

    @OnClick({R2.id.tv_submit, R2.id.iv_close, R2.id.rb_rank1, R2.id.rb_rank2, R2.id.rb_rank3})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.rb_rank1) {
            if (rbRank1.isChecked()) {
                rank = 1;
                showBottomButton();
                rbRank2.setChecked(false);
                rbRank3.setChecked(false);
            } else {
                hideBottomButton();
            }
        } else if (i == R.id.rb_rank2) {
            if (rbRank2.isChecked()) {
                rank = 2;
                showBottomButton();
                rbRank3.setChecked(false);
                rbRank1.setChecked(false);
            } else {
                hideBottomButton();
            }
        } else if (i == R.id.rb_rank3) {
            if (rbRank3.isChecked()) {
                rank = 3;
                showBottomButton();
                rbRank2.setChecked(false);
                rbRank1.setChecked(false);
            } else {
                hideBottomButton();
            }
        } else if (i == R.id.iv_close) {
            finish();
        } else if (i == R.id.tv_submit) {
            PaymentUtil.paymentByGoods("会员充值", "商学院会员充值", rank, new PaymentInterface() {
                @Override
                public void paySuccess() {
                    mPresenter.submitUserMemberShip(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(rank));
                }

                @Override
                public void payFailed() {
                    ToastUtils.show("会员充值失败！");
                }
            });

        }

    }

    private void showBottomButton() {
        llRecharge.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private void hideBottomButton() {
        Resources resources = MembershipOpeningActivity.this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int height = dm.heightPixels;
        llRecharge.animate()
                .translationY(height - llRecharge.getHeight())
                .setInterpolator(new AccelerateInterpolator(2))
                .start();

    }

    @Override
    public void queryMemberPrice(List<MemberGson> memberGsons) {
        tvMemberPrice1.setText("￥" + memberGsons.get(0).getVipPrice());
        tvMemberPrice2.setText("￥" + memberGsons.get(1).getVipPrice());
        tvMemberPrice3.setText("￥" + memberGsons.get(2).getVipPrice());
    }

    @Override
    public void submitUserMember(boolean isSubmit) {
        if (isSubmit) {
            ToastUtils.show("会员充值成功！");
            Map<String, Object> map = new HashMap<>();
            map.put("member", String.valueOf(rank));
            SharePreferenceUtil.saveUser(map);
            finish();
        } else {
            ToastUtils.show("会员充值失败！");
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog("");
    }

    @Override
    public void hideDialog() {
        mhideDialog();
    }
}
