package com.example.module_message.view;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.util.RouterUtil;
import com.example.module_message.R;
@Route(path = RouterUtil.MESSAGE_Fragment_Main)
public class MessageFragment extends BaseFragment<GoodsDetailPresenter> {
    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public GoodsDetailPresenter initPresenter() {
        return null;
    }
}
