package com.example.commonlib.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.gson.OrderDetailGson;
import com.example.commonlib.util.GlideUtil;

import java.util.List;

public class OrderDetailGoodsAdapter extends BaseQuickAdapter<OrderDetailGson.GoodsBean, BaseViewHolder> {
    public OrderDetailGoodsAdapter(@Nullable List<OrderDetailGson.GoodsBean> data) {
        super(R.layout.ry_goods_deail_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailGson.GoodsBean item) {
        helper.setText(R.id.tv_count, " x" + item.getGoodsCount())
                .setText(R.id.tv_goods_name, item.getGoodsCommonName())
                .setText(R.id.tv_goods_style, item.getGoodsName())
                .setText(R.id.mv_price, "￥ " + item.getGoodsPrice());
        if (item.getIsDiscount().equals("1")){
            helper.setVisible(R.id.tv_isdiscount,false);
        }else {
            helper.setVisible(R.id.tv_isdiscount,true);
        }
        GlideUtil.loadRoundCornerAvatarImage(item.getGoodsPicUrl(), (ImageView) helper.getView(R.id.iv_goods_pic), 8);
    }
}
