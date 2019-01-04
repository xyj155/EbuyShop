package com.example.goodscar;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.presenter.HomePresent;
import com.example.commonlib.util.RouterUtil;
import com.xuyijie.goodscar.R;

@Route(path = RouterUtil.ShopCar_Fragment_Main)
public class GoodsCarFragment extends BaseFragment<HomePresent> {


    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_goodscar;
    }

    @Override
    public HomePresent initPresenter() {
        return null;
    }
}