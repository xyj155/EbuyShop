package com.example.home.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.CommentDetailGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.CircleImageView;
import com.example.commonlib.view.toast.ToastUtils;
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
import butterknife.OnClick;

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
    @BindView(R2.id.tv_submit)
    TextView tvSubmit;
    @BindView(R2.id.et_content)
    EditText etContent;
    @BindView(R2.id.rl_content)
    RelativeLayout rlContent;

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
        initToolBar().setToolBarTitle("评论详情");
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
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        rlContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        rlContent.getWindowVisibleDisplayFrame(r);
                        int screenHeight = rlContent.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);
                        if (heightDifference > 200) {
                            Log.i(TAG, "onFocusChange: 1111");
                            tvSubmit.setVisibility(View.VISIBLE);
                        } else {
                            //软键盘隐藏
                            tvSubmit.setVisibility(View.GONE);
                            Log.i(TAG, "onFocusChange: 22222");
                        }
                    }

                });
    }

    @OnClick(R2.id.tv_submit)
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_submit) {
            Log.i(TAG, "onViewClicked: ");
//            etContent.clearFocus();//失去焦点
            View views = GoodsShareCommentDetailActivity.this.getCurrentFocus();
            if (views != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) GoodsShareCommentDetailActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(views.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            if (etContent.getText().toString().isEmpty()) {
                ToastUtils.show("评论不可为空！");
            } else {

                mPresenter.submitGoodsComment(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), etContent.getText().toString(), getIntent().getStringExtra("commentId"));
            }
            tvSubmit.setVisibility(View.GONE);
        }
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

    @Override
    public void submitGoodsComment(boolean isSubmit) {
        if (isSubmit) {
            etContent.setText("");
            mPresenter.queryGoodsCommentById(getIntent().getStringExtra("commentId"));
        } else {
            ToastUtils.show("评论失败！");
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
