package com.example.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuyijie.home.R;

import java.util.List;

public class MemberCouponAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {
    public MemberCouponAdapter(@Nullable List<String> data) {
        super(R.layout.ry_member_coupon_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
