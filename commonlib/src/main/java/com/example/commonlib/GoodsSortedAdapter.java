package com.example.commonlib;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.comonactivity.GoodsDetailActivity;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.view.MoneyView;

import java.util.List;

public class GoodsSortedAdapter extends BaseQuickAdapter<GoodsGson, BaseViewHolder> {
    private Context context;

    public GoodsSortedAdapter(Context context, @Nullable List<GoodsGson> data) {
        super(R.layout.abc_ry_goods_sorted_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsGson item) {
        MoneyView moneyView = helper.getView(R.id.tv_money);
        ImageView ivCover = helper.getView(R.id.iv_cover);
        moneyView.setMoneyText(item.getGoodsPrice());
        helper.setText(R.id.tv_goods_name, item.getGoodsName())
                .setText(R.id.tv_payed, item.getOrderNum() + "人付款")
                .setOnClickListener(R.id.cv_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                        intent.putExtra("goodsId", String.valueOf(item.getId()));
                        context.startActivity(intent);
                    }
                });
        Glide.with(context).asBitmap().load(item.getGoodsPic()).into(ivCover);
    }
}
