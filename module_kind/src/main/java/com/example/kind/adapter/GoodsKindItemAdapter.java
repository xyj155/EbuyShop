package com.example.kind.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.gson.KindItemGson;
import com.xuyijie.kind.R;

import java.util.List;

public class GoodsKindItemAdapter extends BaseQuickAdapter<KindItemGson, BaseViewHolder> {
    private Context context;

    public GoodsKindItemAdapter(@Nullable List<KindItemGson> data, Context context) {
        super(R.layout.ry_item_goodskind_list_layout, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, KindItemGson item) {
        helper.setText(R.id.tv_goodsName, item.getKind_name());
        Glide.with(context).asBitmap().load(item.getKind_url()).into((ImageView) helper.getView(R.id.iv_goods));
    }
}
