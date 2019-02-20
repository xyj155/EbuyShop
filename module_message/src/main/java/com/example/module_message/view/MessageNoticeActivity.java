package com.example.module_message.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.MessageExpressTraceGson;
import com.example.commonlib.gson.SystemMessageGson;
import com.example.module_message.R;
import com.example.module_message.R2;
import com.example.module_message.adapter.SystemPushAdapter;
import com.example.module_message.contract.MessageContract;
import com.example.module_message.presenter.MessagePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessageNoticeActivity extends BaseActivity<MessageContract.View, MessagePresenter> implements MessageContract.View {
    @BindView(R2.id.ry_news)
    RecyclerView ryNews;
    @BindView(R2.id.sml_message)
    SmartRefreshLayout smlMessage;
    private SystemPushAdapter systemPushAdapter;


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public MessagePresenter getPresenter() {
        return new MessagePresenter(this);
    }

    private boolean isFirst = true;

    @Override
    public int intiLayout() {
        return R.layout.activity_message_notice;
    }

    List<SystemMessageGson> systemMessageGsonArrayList = new ArrayList<>();
    private int page = 1;

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("通知消息");

        ryNews.setLayoutManager(new LinearLayoutManager(MessageNoticeActivity.this));

        mPresenter.querySystemPushMessage("1");
        smlMessage.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                systemMessageGsonArrayList.clear();
                systemPushAdapter.notifyDataSetChanged();
                mPresenter.querySystemPushMessage("1");
                isFirst = true;
            }
        });
        smlMessage.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                mPresenter.querySystemPushMessage(String.valueOf(page));
                isFirst = false;
            }
        });
    }

    @Override
    public void initData() {

    }


    @Override
    public void querySystemPushMessage(List<SystemMessageGson> systemMessageGsonList) {
        if (isFirst) {
            Log.i(TAG, "querySystemPushMessage: 33333");
            systemMessageGsonArrayList.addAll(systemMessageGsonList);
            systemPushAdapter = new SystemPushAdapter(null,MessageNoticeActivity.this);
            systemPushAdapter.setEmptyView(View.inflate(MessageNoticeActivity.this,R.layout.common_empty_message,null));
            systemPushAdapter.addData(systemMessageGsonArrayList);
            ryNews.setAdapter(systemPushAdapter);
        } else {
            smlMessage.finishRefresh();
            smlMessage.finishLoadMore();
            systemMessageGsonArrayList.addAll(systemMessageGsonList);
            systemPushAdapter.replaceData(systemMessageGsonArrayList);
            Log.i(TAG, "queryMoreSystemPushMessage: 22222");
        }
    }

    @Override
    public void queryUserGoodsTrace(List<MessageExpressTraceGson> messageExpressTraceGsonList) {

    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog("");
    }

    @Override
    public void hideDialog() {
        mhideDialog();
        smlMessage.finishRefresh();
        smlMessage.finishLoadMore();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

}
