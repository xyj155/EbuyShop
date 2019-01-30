package com.example.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.MyApp;
import com.example.commonlib.gson.GoodsShareGson;
import com.example.home.entity.GoodsShareEntity;
import com.xuyijie.home.R;

import java.util.List;
import java.util.regex.Pattern;

public class GoodsShareAdapter extends BaseMultiItemQuickAdapter<GoodsShareEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GoodsShareAdapter(List<GoodsShareEntity> data) {
        super(data);
        addItemType(GoodsShareEntity.TYPE_ONE, R.layout.abc_ry_goods_share_comment_item_layout);
        addItemType(GoodsShareEntity.TYPE_TWO, R.layout.common_header);
    }
//    public GoodsShareAdapter(@Nullable List<GoodsShareEntity> data) {
//        super(R.layout.abc_ry_goods_share_comment_item_layout, data);
////        addItemType(GoodsShareEntity.TYPE_ONE, R.layout.layout_presale);
////        addItemType(GoodsShareEntity.TYPE_TWO, R.layout.layout_series);
//    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsShareEntity item) {
        GoodsShareEntity homeEntity = (GoodsShareEntity) item;
        switch (homeEntity.getItemType()) {
            case GoodsShareEntity.TYPE_ONE:
                GoodsShareGson seriesEntity1 = homeEntity.getSeriesEntity();
                String username = seriesEntity1.getUser().getUsername();
                helper.setText(R.id.tv_comment_count, seriesEntity1.getCount() + "")
                        .setText(R.id.tv_username, isMobile(username) ? username.replace(username.substring(3, 7), "****") : username)
                        .setText(R.id.tv_comment, seriesEntity1.getUserComment())
                        .setText(R.id.tv_goods_name, seriesEntity1.getGoodsName());
                Glide.with(MyApp.getInstance()).asBitmap().load(seriesEntity1.getUser().getAvatar()).into((ImageView) helper.getView(R.id.iv_avatar));
                break;
            case GoodsShareEntity.TYPE_TWO:
                break;
        }


    }

    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
//
//    @Override
//    protected void convert(BaseViewHolder helper, GoodsShareGson item) {
//        String username = item.getUser().getUsername();
//        helper.setText(R.id.tv_comment_count, item.getCount() + "")
//                .setText(R.id.tv_username, isMobile(username) ? username.replace(username.substring(3, 7), "****") : username)
//                .setText(R.id.tv_comment, item.getUserComment())
//                .setText(R.id.tv_goods_name, item.getGoodsName());
//        Glide.with(MyApp.getInstance()).asBitmap().load(item.getUser().getAvatar()).into((ImageView) helper.getView(R.id.iv_avatar));
//    }

    public boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
}
