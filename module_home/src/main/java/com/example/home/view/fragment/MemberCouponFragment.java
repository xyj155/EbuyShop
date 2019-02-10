package com.example.home.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.CouponGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.home.contract.MemberShipRightContract;
import com.example.home.presenter.MemberShipRightPresenter;
import com.example.home.view.GoodsKindSortedActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MemberCouponFragment extends BaseFragment<MemberShipRightPresenter> implements MemberShipRightContract.View {
    @BindView(R2.id.ry_coupon)
    RecyclerView ryCoupon;
    Unbinder unbinder;
    @BindView(R2.id.sml_coupon)
    SmartRefreshLayout smlCoupon;
    Unbinder unbinder1;
    private MemberShipCouponAdapter memberShipCouponAdapter = new MemberShipCouponAdapter(null);

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        ryCoupon.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("string" + 1);
        }

        ryCoupon.setAdapter(memberShipCouponAdapter);
        mPresenter.queryMemberShipCoupon(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
        smlCoupon.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryMemberShipCoupon(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
            }
        });
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_member_ship_coupon_right;
    }

    @Override
    public MemberShipRightPresenter initPresenter() {
        return new MemberShipRightPresenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void queryMemberShipGoods(List<GoodsGson> goodsGsonList) {

    }

    @Override
    public void queryMemberShipCoupon(List<CouponGson> couponGsonList) {
        memberShipCouponAdapter.replaceData(couponGsonList);
        smlCoupon.finishRefresh();
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
        smlCoupon.finishRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private class MemberShipCouponAdapter extends BaseQuickAdapter<CouponGson, BaseViewHolder> {

        public MemberShipCouponAdapter(@Nullable List<CouponGson> data) {
            super(R.layout.ry_coupoon_member_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CouponGson item) {
            helper.setText(R.id.tv_money, item.getCouponReduce())
                    .setText(R.id.tv_coupon_msg, item.getCouponName())
                    .setText(R.id.tv_msg, "满 " + item.getCouponTotal() + " 元减 " + item.getCouponReduce() + "元")
                    .setOnClickListener(R.id.ll_coupon, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), GoodsKindSortedActivity.class));
                        }
                    });
            if (item.getStartTime().equals("0000-00-00 00:00:00")) {
                helper.setText(R.id.tv_time, "有效日期：无限期");
            } else {
                helper.setText(R.id.tv_time, "有效日期：" + item.getStartTime() + " -- " + item.getEndTime());
            }
        }
    }
}
