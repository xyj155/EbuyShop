package com.example.home.fragment.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.xuyijie.home.R;

import java.util.List;

public class HomeHotGoodsActivityAdapter extends BaseQuickAdapter<HotPurseActivityGson,BaseViewHolder> {
    private Context context;

    public HomeHotGoodsActivityAdapter(@Nullable List<HotPurseActivityGson> data, Context context) {
        super(R.layout.abc_ry_home_hot_goods_item_layout, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HotPurseActivityGson item) {
        helper.setText(R.id.tv_title,item.getActivityTitle())
                .setText(R.id.tv_describe,item.getActivityDesc());
        Glide.with(context).asBitmap().load(item.getActivityPicurl()).into((ImageView) helper.getView(R.id.tv_pic));
    }
}
