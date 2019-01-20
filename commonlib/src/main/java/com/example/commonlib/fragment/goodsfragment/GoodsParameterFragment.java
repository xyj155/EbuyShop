package com.example.commonlib.fragment.goodsfragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.presenter.GoodsDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GoodsParameterFragment extends BaseFragment<GoodsDetailPresenter> implements GoodsDetailContract.View {

    @BindView(R2.id.wb_goods_detail)
    WebView wbGoodsDetail;
    Unbinder unbinder;
    public GoodsDetailActivity activity;

    @Override
    public void initData() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsDetailActivity) context;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        Log.i(TAG, "initView: " + getActivity().getIntent().getStringExtra("goodsId"));

            mPresenter.setGoodsDetailById("1");
    }

    private static final String TAG = "GoodsDetailPicFragment";

    @Override
    public int initLayout() {
        return R.layout.fragment_goods_detail_parameter;
    }

    @Override
    public GoodsDetailPresenter initPresenter() {
        return new GoodsDetailPresenter(this);
    }


    private WebSettings webSettings;

    @Override
    public void loadGoodsDetail(GoodsDetailGson goodsGson) {
        Log.i(TAG, "loadGoodsDetail: "+goodsGson.getGoodsDetailWeb());
        wbGoodsDetail.loadUrl(goodsGson.getGoodsParameter());
        wbGoodsDetail.setFocusable(false);
        webSettings = wbGoodsDetail.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wbGoodsDetail.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            return true;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
