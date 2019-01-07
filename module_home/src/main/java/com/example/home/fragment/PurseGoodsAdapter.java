package com.example.home.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.gson.GoodsGson;
import com.xuyijie.home.R;

import java.util.List;

public class PurseGoodsAdapter extends BaseQuickAdapter<GoodsGson, BaseViewHolder> {
private Context context;
    public PurseGoodsAdapter(@Nullable List<GoodsGson> data, Context context) {
        super(R.layout.ry_home_pursegoods_item, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsGson item) {
        helper.setText(R.id.tvPrice, item.getGoodsPrice())
                .setText(R.id.tvGoodsName, item.getGoodsName());
        Glide.with(context).asBitmap().load(item.getGoodsPic()).into((ImageView) helper.getView(R.id.ivCover));
    }
}
