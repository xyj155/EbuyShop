package com.example.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.util.GlideUtil;
import com.xuyijie.home.R;

import java.util.List;

public class MemberGoodsAdapter extends BaseQuickAdapter<GoodsGson, BaseViewHolder> {
    private Context context;

    public MemberGoodsAdapter(@Nullable List<GoodsGson> data, Context context) {
        super(R.layout.ry_member_goods_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsGson item) {
        helper.setText(R.id.tv_goods_name, item.getGoodsName())
                .setText(R.id.tv_price, "￥ " + item.getGoodsPrice())
                .setText(R.id.tv_content, item.getGoodsDescribe())
                .setOnClickListener(R.id.fl_goods, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                        intent.putExtra("goodsId", String.valueOf(item.getId()));
                        context.startActivity(intent);
                    }
                });
        GlideUtil.loadRoundCornerAvatarImage(item.getGoodsPic(), (ImageView) helper.getView(R.id.iv_goods_pic), 15);
    }
}
