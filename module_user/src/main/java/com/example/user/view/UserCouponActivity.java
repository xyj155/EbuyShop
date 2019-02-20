package com.example.user.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.CouponGson;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.user.adapter.CouponAdapter;
import com.example.user.contract.UserCouponContract;
import com.example.user.presenter.UserCouponPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterUtil.USERCOUPON)
public class UserCouponActivity extends BaseActivity<UserCouponContract.View, UserCouponPresenter> implements UserCouponContract.View {

    @BindView(R2.id.ry_coupon)
    RecyclerView ryCoupon;
    @BindView(R2.id.sml_coupon)
    SmartRefreshLayout smlCoupon;
    private CouponAdapter couponAdapter;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserCouponPresenter getPresenter() {
        return new UserCouponPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_coupon;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("我的优惠券");
        ryCoupon.setLayoutManager(new LinearLayoutManager(UserCouponActivity.this));
        double money = getIntent().getDoubleExtra("money", 0);
        boolean isUse = getIntent().getBooleanExtra("isUse", false);
        Log.i(TAG, "initView: " + isUse);
        couponAdapter = new CouponAdapter(null, UserCouponActivity.this, money, isUse);
        couponAdapter.bindToRecyclerView(ryCoupon);
        View inflate = View.inflate(UserCouponActivity.this, R.layout.coupon_empty, null);
        TextView viewById = inflate.findViewById(R.id.tv_empty);
        viewById.setText("你还没有优惠券哦！");
        couponAdapter.setEmptyView(inflate);
        ryCoupon.setAdapter(couponAdapter);
        mPresenter.queryUserCouponList((String) SharePreferenceUtil.getUser("uid", "String"));
        smlCoupon.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryUserCouponList((String) SharePreferenceUtil.getUser("uid", "String"));
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void loadCouponList(List<CouponGson> couponGsons) {
        couponAdapter.replaceData(couponGsons);
        smlCoupon.finishRefresh();
    }

    @Override
    public void showError(String msg) {
        smlCoupon.finishRefresh();
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
