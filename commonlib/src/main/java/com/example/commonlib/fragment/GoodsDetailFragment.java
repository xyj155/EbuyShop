package com.example.commonlib.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.SubmitOrderGson;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.util.SharePreferenceUtil;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GoodsDetailFragment extends BaseFragment<GoodsDetailPresenter> implements GoodsDetailContract.View {
    @BindView(R2.id.wb_goods_detail)
    WebView wbGoodsDetail;
    Unbinder unbinder;
    public GoodsDetailActivity activity;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        if (mPresenter != null) {
            mPresenter.setGoodsDetailById(activity.getIntent().getStringExtra("goodsId"), String.valueOf(SharePreferenceUtil.getUser("uid","String")));
        }

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_goodsdetail;
    }

    @Override
    public GoodsDetailPresenter initPresenter() {
        return new GoodsDetailPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadGoodsDetail(GoodsDetailGson goodsGson) {
        wbGoodsDetail.loadUrl(goodsGson.getGoodsDetailWeb());
        wbGoodsDetail.setFocusable(false);
        com.tencent.smtt.sdk.WebSettings webSettings = wbGoodsDetail.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(com.tencent.smtt.sdk.WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
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
        createDialog(msg);
    }

    @Override
    public void hideDialog() {
        dialogCancel();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsDetailActivity) context;
    }


}
