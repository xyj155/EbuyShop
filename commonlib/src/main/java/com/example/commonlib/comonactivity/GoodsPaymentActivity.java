package com.example.commonlib.comonactivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.R;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsPaymentContract;
import com.example.commonlib.presenter.GoodsPaymentPresenter;
import com.example.commonlib.util.RouterUtil;


@Route(path = RouterUtil.PAYMENT_PAGE)
public class GoodsPaymentActivity extends BaseActivity<GoodsPaymentContract.View, GoodsPaymentPresenter> implements GoodsPaymentContract.View {

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsPaymentPresenter getPresenter() {
        return new GoodsPaymentPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_payment;
    }

    @Override
    public void initView() {
        initToolBar().setToolBarTitle("订单详情");
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
}
