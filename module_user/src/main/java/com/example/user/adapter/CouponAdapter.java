package com.example.user.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.commonactivity.GoodsPaymentActivity;
import com.example.commonlib.gson.CouponGson;
import com.xuyijie.user.R;

import java.util.List;

public class CouponAdapter extends BaseQuickAdapter<CouponGson, BaseViewHolder> {
    private Activity context;
    private double index;

    public CouponAdapter(@Nullable List<CouponGson> data, Activity context, double index) {
        super(R.layout.ry_coupon_item, data);
        this.context = context;
        this.index = index;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CouponGson item) {
        helper.setText(R.id.tv_name, item.getCouponName())
                .setText(R.id.tv_comment, "满减优惠券：满 " + item.getCouponTotal() + " 减 " + item.getCouponReduce())
                .setText(R.id.tv_price, "￥" + item.getCouponReduce())
                .setText(R.id.tv_date, "有效日期：" + item.getStartTime() + " -- " + item.getEndTime())
                .setOnClickListener(R.id.ll_coupon, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GoodsPaymentActivity.class);
                        intent.putExtra("startIndex", item.getCouponTotal());
                        intent.putExtra("endIndex", item.getCouponReduce());
                        intent.putExtra("couponId", String.valueOf(item.getId()));
                        context.setResult(0x11, intent);
                        context.finish();
                    }
                });
        ImageView view = helper.getView(R.id.iv_cant_use);
        View view1 = helper.getView(R.id.ll_coupon);
        if (index > Double.valueOf(item.getCouponTotal())) {
            view.setVisibility(View.GONE);
            view1.setClickable(true);
        } else {
            view.setVisibility(View.VISIBLE);
            view1.setClickable(false);
        }

    }
}
