package com.example.module_message.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.BrowserActivity;
import com.example.commonlib.gson.MessageExpressTraceGson;
import com.example.commonlib.gson.SystemMessageGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.module_message.R;
import com.example.module_message.R2;
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

public class MessageExpressActivity extends BaseActivity<MessageContract.View, MessagePresenter> implements MessageContract.View {


    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.ry_trace)
    RecyclerView ryTrace;
    @BindView(R2.id.sml_trace)
    SmartRefreshLayout smlTrace;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public MessagePresenter getPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_message_express;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        ryTrace.setLayoutManager(new LinearLayoutManager(MessageExpressActivity.this));
    }

    @Override
    public void initData() {
        initToolBar().setToolBarTitle("交易物流");
        mPresenter.queryUserGoodsTrace(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), "1");
        smlTrace.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                if (snackOrderAdapter != null)
                    snackOrderAdapter.notifyDataSetChanged();
                mPresenter.queryUserGoodsTrace(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), "1");
                isFirst = true;
            }
        });
        smlTrace.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                mPresenter.queryUserGoodsTrace(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(page));
                isFirst = false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void querySystemPushMessage(List<SystemMessageGson> systemMessageGsonList) {

    }

    private boolean isFirst = true;
    List<MessageExpressTraceGson> systemMessageGsonArrayList = new ArrayList<>();
    private int page = 1;

    @Override
    public void queryUserGoodsTrace(List<MessageExpressTraceGson> messageExpressTraceGsonList) {
        if (isFirst) {
            Log.i(TAG, "querySystemPushMessage: 33333");
            systemMessageGsonArrayList.addAll(messageExpressTraceGsonList);
            snackOrderAdapter = new SnackOrderAdapter(null, MessageExpressActivity.this);
            snackOrderAdapter.setEmptyView(View.inflate(MessageExpressActivity.this, R.layout.common_empty_message, null));
            snackOrderAdapter.addData(systemMessageGsonArrayList);
            ryTrace.setAdapter(snackOrderAdapter);
        } else {

            systemMessageGsonArrayList.addAll(messageExpressTraceGsonList);
            snackOrderAdapter.replaceData(systemMessageGsonArrayList);
            Log.i(TAG, "queryMoreSystemPushMessage: 22222");
        }
        smlTrace.finishRefresh();
        smlTrace.finishLoadMore();
    }

    private SnackOrderAdapter snackOrderAdapter;

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog(msg);
    }

    @Override
    public void hideDialog() {
        smlTrace.finishRefresh();
        smlTrace.finishLoadMore();
        mhideDialog();
    }

    private class SnackOrderAdapter extends BaseQuickAdapter<MessageExpressTraceGson, BaseViewHolder> {

        private Context context;

        public SnackOrderAdapter(@Nullable List<MessageExpressTraceGson> data, Context context) {
            super(R.layout.ry_express_order_layout_item, data);
            this.context = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, final MessageExpressTraceGson item) {
            if (item.getCount() > 100) {
                helper.setText(R.id.tv_count, "99");
            } else {
                helper.setText(R.id.tv_count, item.getCount() + "");
            }
            helper.setText(R.id.tv_order_num, "订单编号：" + item.getOrderNum())
                    .setText(R.id.tv_goods_name, item.getGoodsName() + "")
                    .setText(R.id.tv_goods_time, "创建时间：" + item.getAddTime().substring(0, 16))
            .setOnClickListener(R.id.rl_goods, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BrowserActivity.class);
                    intent.putExtra("url", RetrofitUtils.BASE_URL+"/StuShop/public/index.php/index/index/expressTrace?expressNum="+item.getExpressNum());
                    startActivity(intent);
                }
            });
            GlideUtil.loadRoundCornerAvatarImage(item.getGoodsPicUrl(), (ImageView) helper.getView(R.id.iv_goods_pic), 20);

        }
    }
}
