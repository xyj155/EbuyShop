package com.example.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.gson.GoodsGson;
import com.example.home.view.TimeFlashSaleActivity;
import com.xuyijie.home.R;

import java.util.List;

public class HomeGoodsTimerPurseAdapter extends BaseQuickAdapter<GoodsGson, BaseViewHolder> {
    private Context context;

    public HomeGoodsTimerPurseAdapter(@Nullable List<GoodsGson> data, Context context) {
        super(R.layout.abc_ry_home_timepurse_item_layout, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsGson item) {
        helper.setText(R.id.tv_price, "￥" + item.getGoodsPrice())
        .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TimeFlashSaleActivity.class);
                context.startActivity(intent);
            }
        });
        TextView view = helper.getView(R.id.tv_price_orign);
        view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        view.getPaint().setAntiAlias(true);
        RoundedCorners roundedCorners = new RoundedCorners(18);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Log.i(TAG, "convert: " + item.getGoodsPic());
        Glide.with(context).asBitmap().load(item.getGoodsPic()).apply(options).into((ImageView) helper.getView(R.id.iv_goodsPic));
    }
}
