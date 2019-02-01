package com.example.user.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.SubmitOrderGson;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.user.view.UserCollectionActivity;
import com.xuyijie.user.R;

import java.util.List;

public class UserCollectionAdapter extends BaseQuickAdapter<GoodsGson, BaseViewHolder> implements GoodsDetailContract.View {
    private UserCollectionActivity context;
    private GoodsDetailPresenter userCollectionPresenter = new GoodsDetailPresenter(this);


    public UserCollectionAdapter(@Nullable List<GoodsGson> data, UserCollectionActivity context) {
        super(R.layout.ry_user_collection_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsGson item) {
        helper.setText(R.id.tv_goods_name, item.getGoodsName())
                .setText(R.id.tv_goods_price, "￥" + item.getGoodsPrice())
                .setText(R.id.tv_time, item.getAddTime().substring(0, 11))
                .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                        intent.putExtra("goodsId", String.valueOf(item.getId()));
                        context.startActivity(intent);
                    }
                })
                .setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userCollectionPresenter.addUserCollection(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(item.getId()), "1");
                        context.getPresenter().queryUserCollection(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
                    }
                });

        GlideUtil.loadRoundCornerAvatarImage(item.getGoodsPic(), (ImageView) helper.getView(R.id.iv_goods_pic), 8);

    }

    @Override
    public void loadGoodsDetail(GoodsDetailGson goodsGson) {

    }

    @Override
    public void insertUserOrder(SubmitOrderGson goodsGson) {

    }

    @Override
    public void addCollectionSuccess(boolean success) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void hideDialog() {

    }
}
