package com.example.kind.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.commonactivity.GoodsListSortedActivity;
import com.example.commonlib.gson.KindItemGson;
import com.xuyijie.kind.R;

import java.util.List;

public class GoodsKindItemAdapter extends BaseQuickAdapter<KindItemGson, BaseViewHolder> {
    private Activity context;

    public GoodsKindItemAdapter(@Nullable List<KindItemGson> data, Activity context) {
        super(R.layout.ry_item_goodskind_list_layout, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, final KindItemGson item) {
        helper.setText(R.id.tv_goodsName, item.getKind_name())
                .setOnClickListener(R.id.ll_kind, new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GoodsListSortedActivity.class);
                        intent.putExtra("kind", item.getKind_name());
                        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
                    }
                });
        Glide.with(context).asBitmap().load(item.getKind_url()).into((ImageView) helper.getView(R.id.iv_goods));
    }
}
