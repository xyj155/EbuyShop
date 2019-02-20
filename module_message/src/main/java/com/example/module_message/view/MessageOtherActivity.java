package com.example.module_message.view;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.EmptyContract;
import com.example.commonlib.presenter.EmptyPresenter;
import com.example.module_message.R;

public class MessageOtherActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public EmptyPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_message_other;
    }

    @Override
    public void initView() {
        initToolBar().setToolBarTitle("其他消息");
    }

    @Override
    public void initData() {

    }
}
