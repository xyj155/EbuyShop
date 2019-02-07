package com.example.home.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commonlib.base.BaseFragment;
import com.example.home.presenter.MemberShipRightPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MemberCouponFragment extends BaseFragment<MemberShipRightPresenter> {
    @BindView(R2.id.ry_coupon)
    RecyclerView ryCoupon;
    Unbinder unbinder;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_member_ship_coupon_right;
    }

    @Override
    public MemberShipRightPresenter initPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
