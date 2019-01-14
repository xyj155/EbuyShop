package com.example.commonlib.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.fragment.goodsfragment.GoodsDetailPicFragment;
import com.example.commonlib.fragment.goodsfragment.GoodsParameterFragment;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.loader.GoodsBannerViewHolder;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.view.SlideDetailsLayout;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GoodsInfoFragment extends BaseFragment<GoodsDetailPresenter> implements SlideDetailsLayout.OnSlideDetailsListener, GoodsDetailContract.View {
    private static final String TAG = "GoodsInfoFragment";

    Unbinder unbinder1;
    @BindView(R2.id.banner_goods) MZBannerView bannerGoods;
    @BindView(R2.id.tv_banner_size) TextView tvBannerSize;
    @BindView(R2.id.tv_goods_title) TextView tvGoodsTitle;
    @BindView(R2.id.tv_new_price) TextView tvNewPrice;
    @BindView(R2.id.tv_old_price) TextView tvOldPrice;
    @BindView(R2.id.tv_current_goods) TextView tvCurrentGoods;
    @BindView(R2.id.ll_current_goods) LinearLayout llCurrentGoods;
    @BindView(R2.id.ry_ensure) RecyclerView ryEnsure;
    @BindView(R2.id.tv_comment_count) TextView tvCommentCount;
    @BindView(R2.id.tv_good_comment) TextView tvGoodComment;
    @BindView(R2.id.iv_comment_right) ImageView ivCommentRight;
    @BindView(R2.id.ll_comment) LinearLayout llComment;
    @BindView(R2.id.ry_comment) RecyclerView ryComment;
    @BindView(R2.id.tv_commentpic_count) TextView tvCommentpicCount;
    @BindView(R2.id.iv_commentpic_right) ImageView ivCommentpicRight;
    @BindView(R2.id.ll_comment_pic) LinearLayout llCommentPic;
    @BindView(R2.id.ry_comment_pic) RecyclerView ryCommentPic;
    @BindView(R2.id.ry_recommend) RecyclerView ryRecommend;
    @BindView(R2.id.ll_recommend) LinearLayout llRecommend;
    @BindView(R2.id.sv_goods_info) NestedScrollView svGoodsInfo;
    @BindView(R2.id.sv_switch) SlideDetailsLayout svSwitch;


    private GoodsDetailActivity activity;
    private GoodsCommentPicAdapter goodsCommentPicAdapter = new GoodsCommentPicAdapter(null);
    private GoodsCommentAdapter goodsCommentAdapter = new GoodsCommentAdapter(null);

    @Override
    public void initData() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsDetailActivity) context;
    }

    private ServiceAdapter serviceAdapter = new ServiceAdapter(null);

    @Override
    public void initView(View view) {
        unbinder1 = ButterKnife.bind(this, view);
        svSwitch.setOnSlideDetailsListener(this);
        mPresenter.setGoodsDetailById(activity.getIntent().getStringExtra("goodId"));
        Log.i(TAG, "initView: " + activity.getIntent().getStringExtra("goodId"));
        ryEnsure.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ryEnsure.setNestedScrollingEnabled(false);
        ryEnsure.setAdapter(serviceAdapter);
        ryRecommend.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ryRecommend.setNestedScrollingEnabled(false);
        ryRecommend.setAdapter(goodsDetailPurseAdapter);
        ryCommentPic.setLayoutManager(new GridLayoutManager(activity, 4));
        ryCommentPic.setNestedScrollingEnabled(false);
        ryCommentPic.setAdapter(goodsCommentPicAdapter);
        goodsCommentPicAdapter.setEmptyView(View.inflate(activity, R.layout.common_empty_comment_layout, null));
        ryComment.setLayoutManager(new LinearLayoutManager(activity));
        ryComment.setAdapter(goodsCommentAdapter);
    }






    private int nowIndex;
    private float fromX;

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {

    }


    private class ServiceAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ServiceAdapter(@Nullable List<String> data) {
            super(R.layout.abc_goods_detail_service_tag, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_service_name, item);
        }
    }



    private GoodsDetailPurseAdapter goodsDetailPurseAdapter = new GoodsDetailPurseAdapter(null);

    private class GoodsDetailPurseAdapter extends BaseQuickAdapter<GoodsDetailGson.PurseGoodsListBean, BaseViewHolder> {

        public GoodsDetailPurseAdapter(@Nullable List<GoodsDetailGson.PurseGoodsListBean> data) {
            super(R.layout.abc_goods_detail_purse_goods_list_item_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsDetailGson.PurseGoodsListBean item) {
            helper.setText(R.id.tv_goods_name, item.getGoodsName())
                    .setText(R.id.tv_price, "￥" + item.getGoodsPrice());
            RoundedCorners roundedCorners = new RoundedCorners(6);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            Glide.with(getContext()).asBitmap().load(item.getGoodsPic()).apply(options).into((ImageView) helper.getView(R.id.iv_cover));
        }
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_goodsinfo;
    }

    @Override
    public GoodsDetailPresenter initPresenter() {
        return new GoodsDetailPresenter(this);
    }



    @Override
    public void loadGoodsDetail(final GoodsDetailGson goodsGson) {
        tvBannerSize.setText("1/" + goodsGson.getGoodsPicUrl().size());
        Log.i(TAG, "loadGoodsDetail: " + goodsGson.getGoodsPicUrl().size());
        List<String> goodsPicUrl = goodsGson.getGoodsPicUrl();
        bannerGoods.setPages(goodsPicUrl, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new GoodsBannerViewHolder();
            }
        });
        bannerGoods.setDuration(200);
        bannerGoods.setIndicatorVisible(true);
        bannerGoods.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                tvBannerSize.setText((i + 1) + "/" + goodsGson.getGoodsPicUrl().size());
            }
        });
        bannerGoods.start();
        tvGoodsTitle.setText(goodsGson.getGoodsName());
        tvNewPrice.setText(goodsGson.getGoodsPrice());
        tvOldPrice.setText("原价：￥" + goodsGson.getOriginalPrice());
        serviceAdapter.replaceData(goodsGson.getGoodsService());
        goodsDetailPurseAdapter.replaceData(goodsGson.getPurseGoodsList());
        goodsCommentPicAdapter.replaceData(goodsGson.getGoodsCommentPic());
        tvCommentpicCount.setText(goodsGson.getGoodsCommentPic().size() + "");
        tvCommentCount.setText(goodsGson.getComment().size() + "");
    }

    private class GoodsCommentPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public GoodsCommentPicAdapter(@Nullable List<String> data) {
            super(R.layout.abc_goods_detail_comment_pic_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            Glide.with(activity).asBitmap().load(item).apply(options).into((ImageView) helper.getView(R.id.iv_pic));
        }
    }

    private class GoodsCommentAdapter extends BaseQuickAdapter<GoodsDetailGson.CommentBean, BaseViewHolder> {

        public GoodsCommentAdapter(@Nullable List<GoodsDetailGson.CommentBean> data) {
            super(R.layout.abc_goods_detail_comment_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsDetailGson.CommentBean item) {
            helper.setText(R.id.tv_username, item.getUsername())
                    .setText(R.id.tv_comment, item.getComment());
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            Glide.with(activity).asBitmap().load(item.getAvatar()).apply(options).into((ImageView) helper.getView(R.id.iv_user_avatar));
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog(msg);
    }

    @Override
    public void hideDialog() {
        dialogCancel();
    }



}
