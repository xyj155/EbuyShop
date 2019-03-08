package com.example.commonlib.commonactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.R;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.EmptyContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.presenter.EmptyPresenter;
import com.example.commonlib.util.RouterUtil;

@Route(path = RouterUtil.CONVERSATION)
public class ConversationActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {



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
        return R.layout.activity_conversation;

    }

    @Override
    public void initView() {
        initToolBar().setToolBarTitle(getIntent().getData().getQueryParameter("title"));

    }

    @Override
    public void initData() {

    }
}
