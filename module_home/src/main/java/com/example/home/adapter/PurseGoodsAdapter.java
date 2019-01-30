package com.example.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.gson.GoodsGson;
import com.xuyijie.home.R;

import java.util.List;

public class PurseGoodsAdapter extends BaseQuickAdapter<GoodsGson, BaseViewHolder> {
    private Context context;

    public PurseGoodsAdapter(@Nullable List<GoodsGson> data, Context context) {
        super(R.layout.ry_home_pursegoods_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsGson item) {
        helper.setText(R.id.tvPrice, item.getGoodsPrice())
                .setText(R.id.tvGoodsName, item.getGoodsName())
                .setText(R.id.tvTotal, item.getGoodsLocation())
                .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent view = new Intent(context, GoodsDetailActivity.class);
                        view.putExtra("goodsId", String.valueOf(item.getId()));
                        context.startActivity(view);
                    }
                });
        ;
        Glide.with(context).asBitmap().load(item.getGoodsPic()).into((ImageView) helper.getView(R.id.ivCover));
    }
}
