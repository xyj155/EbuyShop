package com.example.home.fragment.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.home.fragment.entity.HotGoodsEntity;
import com.xuyijie.home.R;

import java.util.List;

public class HomeHotGoodsItemAdapter extends BaseQuickAdapter<HotGoodsEntity,BaseViewHolder> {
    private Context context;

    public HomeHotGoodsItemAdapter( @Nullable List<HotGoodsEntity> data,Context context) {
        super(R.layout.abc_ry_home_hot_goods_item_layout, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HotGoodsEntity item) {
        helper.setText(R.id.tv_title,item.getName())
                .setText(R.id.tv_describe,item.getDecribe());
        Glide.with(context).asBitmap().load(item.getUrl()).into((ImageView) helper.getView(R.id.tv_pic));
    }
}
