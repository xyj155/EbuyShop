package com.example.commonlib.commonactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.adapter.GoodsCommentAdapter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsCommentContract;
import com.example.commonlib.gson.GoodsCommentGson;
import com.example.commonlib.presenter.GoodsCommentPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsCommentActivity extends BaseActivity<GoodsCommentContract.View, GoodsCommentPresenter> implements GoodsCommentContract.View {


    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.ry_comment)
    RecyclerView ryComment;
    private GoodsCommentAdapter commnetAdater;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsCommentPresenter getPresenter() {
        return new GoodsCommentPresenter(this);
    }


    @Override
    public int intiLayout() {
        return R.layout.activity_goods_comment;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("商品评价");
        mPresenter.queryGoodsComment(getIntent().getStringExtra("goodsId"));
        ryComment.setLayoutManager(new LinearLayoutManager(GoodsCommentActivity.this));
        commnetAdater = new GoodsCommentAdapter(null, GoodsCommentActivity.this);
        View inflate = View.inflate(GoodsCommentActivity.this, R.layout.common_empty, null);
        TextView viewById = inflate.findViewById(R.id.tv_empty);
        viewById.setText("还没有人评论哦！要不坐个沙发？");
        commnetAdater.setEmptyView(inflate);
        ryComment.setAdapter(commnetAdater);
    }

    @Override
    public void initData() {

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

    @Override
    public void setGoodsComment(List<GoodsCommentGson> list) {
        commnetAdater.replaceData(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
