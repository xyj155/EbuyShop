package com.example.home.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.SnackPaymentActivity;
import com.example.commonlib.gson.SnackOrderGson;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.home.contract.QuerySnackOrderContract;
import com.example.home.presenter.QuerySnackOrderPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnackOrderListActivity extends BaseActivity<QuerySnackOrderContract.View, QuerySnackOrderPresenter> implements QuerySnackOrderContract.View {


    @BindView(R2.id.ry_order)
    RecyclerView ryOrder;
    @BindView(R2.id.sml_order)
    SmartRefreshLayout smlOrder;
    private SnackOrderAdapter snackOrderAdapter;
    private int page = 1;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public QuerySnackOrderPresenter getPresenter() {
        return new QuerySnackOrderPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_snack_order_list;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        ryOrder.setLayoutManager(new LinearLayoutManager(SnackOrderListActivity.this));
        initToolBar().setToolBarTitle("我的订单");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.queryUserSnackOrderListByUserId(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), "1", "10");
    }

    @Override
    public void initData() {
        mPresenter.queryUserSnackOrderListByUserId(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), "1", "10");

        smlOrder.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                mPresenter.queryUserMoreSnackOrderListByUserId(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(page), "10");
            }
        });
    }

    private List<SnackOrderGson> snackOrderGsons = new ArrayList<>();

    @Override
    public void queryUserSnackOrderListByUserId(List<SnackOrderGson> list) {
        snackOrderGsons.clear();
        snackOrderGsons.addAll(list);
        snackOrderAdapter = new SnackOrderAdapter(snackOrderGsons);
        ryOrder.setAdapter(snackOrderAdapter);
        smlOrder.finishRefresh();
    }

    @Override
    public void queryUserMoreSnackOrderListByUserId(List<SnackOrderGson> list) {
        snackOrderGsons.addAll(list);
        snackOrderAdapter.replaceData(snackOrderGsons);
        smlOrder.finishLoadMore();
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
        mhideDialog();
        smlOrder.finishRefresh();
        smlOrder.finishLoadMore();
    }

    private class SnackOrderAdapter extends BaseQuickAdapter<SnackOrderGson, BaseViewHolder> {

        public SnackOrderAdapter(@Nullable List<SnackOrderGson> data) {
            super(R.layout.ry_snack_order_layout_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final SnackOrderGson item) {
            if (item.getCount() > 100) {
                helper.setText(R.id.tv_count, "99");
            } else {
                helper.setText(R.id.tv_count, item.getCount() + "");
            }
            if (item.getStatus().equals("1")) {
                helper.setText(R.id.tv_status, "待支付");
                helper.setOnClickListener(R.id.rl_goods, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SnackOrderListActivity.this, SnackPaymentActivity.class);
                        intent.putExtra("orderNum",item.getOrderNum());
                        startActivity(intent);
                    }
                });
            } else if (item.getStatus().equals("2")) {
                helper.setText(R.id.tv_status, "待发货");
                helper.setOnClickListener(R.id.rl_goods, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SnackOrderListActivity.this, SnackOrderDetailActivity.class);
                        intent.putExtra("orderNum",item.getOrderNum());
                        startActivity(intent);
                    }
                });
            } else if (item.getStatus().equals("3")) {
                helper.setText(R.id.tv_status, "已发货");
            }
            helper.setText(R.id.tv_order_num, "订单编号：" + item.getOrderNum())
                    .setText(R.id.tv_goods_name, item.getFoodName() + "")
                    .setText(R.id.tv_goods_time, "创建时间：" + item.getCreateTime().substring(0, 16));
            GlideUtil.loadRoundCornerAvatarImage(item.getFoodPicture(), (ImageView) helper.getView(R.id.iv_goods_pic), 20);

        }
    }
}
