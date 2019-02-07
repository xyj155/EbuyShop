package com.example.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.MyApp;
import com.example.commonlib.gson.GoodsShareGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.GlideUtil;
import com.example.home.view.GoodsShareCommentDetailActivity;
import com.xuyijie.home.R;

import java.util.List;
import java.util.regex.Pattern;

public class GoodsShareAdapter extends BaseQuickAdapter<GoodsShareGson, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context context;

    public GoodsShareAdapter(List<GoodsShareGson> data, Context context) {
        super(R.layout.abc_ry_goods_share_comment_item_layout, data);
        this.context = context;
    }



    @Override
    protected void convert(BaseViewHolder helper, final GoodsShareGson item) {
        GoodsPictureAdapter goodsPictureAdapter = new GoodsPictureAdapter(null);
        RecyclerView recyclerView = helper.getView(R.id.ry_goods_pic);
        if (helper.getPosition()==0){
            helper.setVisible(R.id.view_empty,false);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(goodsPictureAdapter);
        String username = item.getUser().getUsername();
        helper.setText(R.id.tv_username, isMobile(username) ? username.replace(username.substring(3, 7), "****") : username)
                .setText(R.id.tv_comment, item.getComment())
                .setText(R.id.tv_comment_count, helper.getPosition() + "")
                .setText(R.id.tv_goods_name, item.getGoods().getGoodsName())
                .setOnClickListener(R.id.ll_comment_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GoodsShareCommentDetailActivity.class);
                        intent.putExtra("commentId", String.valueOf(item.getCid()));
                        context.startActivity(intent);
                    }
                });
        Glide.with(MyApp.getInstance()).asBitmap().load(RetrofitUtils.BASE_URL + item.getUser().getAvatar()).into((ImageView) helper.getView(R.id.iv_avatar));
        if (item.getGoodsPicture().size() > 0) {

            goodsPictureAdapter.replaceData(item.getGoodsPicture());
        }
    }

    private class GoodsPictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public GoodsPictureAdapter(@Nullable List<String> data) {
            super(R.layout.ry_goods_share_picture_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView view = helper.getView(R.id.iv_picture);
            GlideUtil.loadRoundCornerAvatarImage(RetrofitUtils.BASE_URL + item, view, 8);
        }
    }

    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    public boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
}
