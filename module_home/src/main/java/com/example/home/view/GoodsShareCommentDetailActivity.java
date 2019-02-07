package com.example.home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.CommentDetailGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.view.CircleImageView;
import com.example.home.contract.CommentDetailContract;
import com.example.home.presenter.CommentDetailPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsShareCommentDetailActivity extends BaseActivity<CommentDetailContract.View, CommentDetailPresenter> implements CommentDetailContract.View {

    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_content)
    TextView tvContent;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R2.id.ry_picture)
    RecyclerView ryPicture;
    @BindView(R2.id.tv_time)
    TextView tvTime;
    @BindView(R2.id.sml_comment)
    SmartRefreshLayout smlComment;
    @BindView(R2.id.tv_comment_count_tag)
    TextView tvCommentCountTag;
    @BindView(R2.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R2.id.ry_comment)
    RecyclerView ryComment;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public CommentDetailPresenter getPresenter() {
        return new CommentDetailPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_share_comment_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        smlComment.autoRefresh();
        smlComment.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryGoodsCommentById(getIntent().getStringExtra("commentId"));
            }
        });
        ryComment.setLayoutManager(new LinearLayoutManager(GoodsShareCommentDetailActivity.this));
        ryPicture.setLayoutManager(new GridLayoutManager(GoodsShareCommentDetailActivity.this, 3));
        ryPicture.setAdapter(goodsPictureAdapter);
        ryComment.setAdapter(goodsUserCommentAdapter);
        ryComment.setNestedScrollingEnabled(false);
    }

    @Override
    public void initData() {

    }

    private GoodsPictureAdapter goodsPictureAdapter = new GoodsPictureAdapter(null);

    @Override
    public void setGoodsComment(CommentDetailGson goodsComment) {
        tvUsername.setText(goodsComment.getUser().getUsername().replace(goodsComment.getUser().getUsername().substring(3, 7), "****"));
        GlideUtil.loadGeneralImage(RetrofitUtils.BASE_URL + goodsComment.getUser().getAvatar(), ivAvatar);
        tvGoodsName.setText(goodsComment.getGoods().getGoodsName());
        tvContent.setText(goodsComment.getComment());
        tvTime.setText(goodsComment.getTime());

        Log.i(TAG, "setGoodsComment: " + goodsComment.getGoodsPicture().size());
        if (goodsComment.getGoodsPicture().size() > 0) {
            goodsPictureAdapter.replaceData(goodsComment.getGoodsPicture());
        }
        tvCommentCountTag.setText(goodsComment.getDiscussUser().size() + "");
        tvCommentCount.setText("全部留言 ( " + goodsComment.getDiscussUser().size() + " )");
        goodsUserCommentAdapter.replaceData(goodsComment.getDiscussUser());
        smlComment.finishRefresh();
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

    private GoodsUserCommentAdapter goodsUserCommentAdapter = new GoodsUserCommentAdapter(null);

    private class GoodsUserCommentAdapter extends BaseQuickAdapter<CommentDetailGson.DiscussUserBean, BaseViewHolder> {

        public GoodsUserCommentAdapter(@Nullable List<CommentDetailGson.DiscussUserBean> data) {
            super(R.layout.ry_comment_discuss_user_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommentDetailGson.DiscussUserBean item) {
            ImageView view = helper.getView(R.id.iv_avatar);
            helper.setText(R.id.tv_username, item.getUser().getUsername())
                    .setText(R.id.tv_comment, item.getCommentContent())
                    .setText(R.id.tv_time, item.getCreateTime().substring(11, item.getCreateTime().length()));
            GlideUtil.loadRoundCornerAvatarImage(RetrofitUtils.BASE_URL + item.getUser().getAvatar(), view, 8);
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
        mhideDialog();
        smlComment.finishRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
