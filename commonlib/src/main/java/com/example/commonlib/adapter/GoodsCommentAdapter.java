package com.example.commonlib.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.gson.GoodsCommentGson;
import com.example.commonlib.util.GlideUtil;

import java.util.List;

public class GoodsCommentAdapter extends BaseQuickAdapter<GoodsCommentGson, BaseViewHolder> {
    private Context context;

    public GoodsCommentAdapter(@Nullable List<GoodsCommentGson> data, Context context) {
        super(R.layout.abc_goods_comment_list_layout, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsCommentGson item) {
        Log.i(TAG, "convert: "+item.getGoodsName());
        String username = item.getUser().getUsername();
        String replace = username.replace(username.substring(4, 8), "****");
        helper.setText(R.id.tv_username, replace)
                .setText(R.id.tv_time, item.getGoodsName())
                .setText(R.id.tv_content, item.getComment());
        GlideUtil.loadGeneralImage(item.getUser().getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
        PictureAdapter pictureAdapter = new PictureAdapter(null);
        RecyclerView view = helper.getView(R.id.ry_comment);
        if (item.getPicture().size()==1){
            view.setLayoutManager(new GridLayoutManager(context,1));
        }else if (item.getPicture().size()==2){
            view.setLayoutManager(new GridLayoutManager(context,2));
        }else if (item.getPicture().size()>=3){
            view.setLayoutManager(new GridLayoutManager(context,3));
        }
        pictureAdapter.replaceData(item.getPicture());
        view.setAdapter(pictureAdapter);
    }

    private class PictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public PictureAdapter(@Nullable List<String> data) {
            super(R.layout.abc_goods_comment_picture_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            GlideUtil.loadGeneralImage(item, (ImageView) helper.getView(R.id.iv_pic));
        }
    }
}
