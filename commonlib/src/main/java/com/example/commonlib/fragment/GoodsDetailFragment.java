package com.example.commonlib.fragment;

import android.view.View;

import com.example.commonlib.R;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.presenter.GoodsDetailPresenter;

public class GoodsDetailFragment extends BaseFragment<GoodsDetailPresenter> {
    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_goodsdetail;
    }

    @Override
    public GoodsDetailPresenter initPresenter() {
        return null;
    }
}