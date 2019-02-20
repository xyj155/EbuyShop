package com.example.user.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.MyDialog;
import com.example.user.adapter.UserGoodsStatusAdapter;
import com.example.user.contract.UpdateOrderStatusContract;
import com.example.user.contract.UserAllOrderContract;
import com.example.user.presenter.UpdateOrderStatusPresenter;
import com.example.user.presenter.UserAllOrderPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAllOrdersActivity extends BaseActivity<UserAllOrderContract.View, UserAllOrderPresenter> implements UpdateOrderStatusContract.View, UserAllOrderContract.View {


    @BindView(R2.id.ry_all_order)
    RecyclerView ryAllOrder;
    @BindView(R2.id.sml_all_order)
    SmartRefreshLayout smlAllOrder;
    private UserGoodsStatusAdapter userGoodsStatusAdapter;
    private UpdateOrderStatusPresenter updateOrderStatusPresenter = new UpdateOrderStatusPresenter(this);
    private int page = 1;
    private List<List<UserOrderStatusGson>> lists = new ArrayList<>();

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserAllOrderPresenter getPresenter() {
        return new UserAllOrderPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_all_orders;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("全部订单");

        ryAllOrder.setLayoutManager(new LinearLayoutManager(UserAllOrdersActivity.this));
        smlAllOrder.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                Log.i(TAG, "onLoadMore: " + page);
                mPresenter.queryUserAllOrder(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(page));
            }
        });
        smlAllOrder.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                Log.i(TAG, "onLoadMore: " + page);
                mPresenter.loadUserMoreOrders(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(page));
            }
        });

    }

    @Override
    public void initData() {
        mPresenter.queryUserAllOrder(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), "1");
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
        smlAllOrder.finishRefresh();
    }

    @Override
    public void updateStatus(boolean success) {
        if (success) {
            mPresenter.queryUserAllOrder((String) SharePreferenceUtil.getUser("uid", "String"), String.valueOf(page));
        } else {
            Toast.makeText(this, "收货失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadUserAllOrders(List<List<UserOrderStatusGson>> userOrderStatusGsons) {
        lists.clear();
        lists.addAll(userOrderStatusGsons);
        userGoodsStatusAdapter = new UserGoodsStatusAdapter(null, UserAllOrdersActivity.this);
        userGoodsStatusAdapter.setEmptyView(View.inflate(UserAllOrdersActivity.this, R.layout.order_empty_layout, null));
        userGoodsStatusAdapter.addData(lists);
        userGoodsStatusAdapter.setHasStableIds(true);
        ryAllOrder.setAdapter(userGoodsStatusAdapter);
        userGoodsStatusAdapter.setOnReceiveListener(new UserGoodsStatusAdapter.OnReceiveListener() {
            @Override
            public void onReceive(final String orderNum) {
                final MyDialog myDialog1 = new MyDialog(UserAllOrdersActivity.this, new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
                myDialog1.setContent("是否确认收货");
                myDialog1.setTitle("确认收货");
                myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
                    @Override
                    public void onCenterItemClick(MyDialog dialog, View view) {
                        int i = view.getId();
                        if (i == R.id.dialog_btn_close) {
                            myDialog1.dismiss();
                        } else if (i == R.id.dialog_btn_cancel) {
                            myDialog1.dismiss();
                            Log.i(TAG, "onCenterItemClick: " + orderNum);
                            Log.i(TAG, "onCenterItemClick: " + (String) SharePreferenceUtil.getUser("uid", "String"));
                            updateOrderStatusPresenter.updateOrderStatusByReceive((String) SharePreferenceUtil.getUser("uid", "String"), orderNum);
                        }
                    }
                });
                myDialog1.show();
            }
        });
        ryAllOrder.setAdapter(userGoodsStatusAdapter);
        Log.i(TAG, "onLoadMore: 23232323" + page);
        smlAllOrder.finishRefresh();
        smlAllOrder.finishLoadMore();
    }

    @Override
    public void loadUserMoreOrders(List<List<UserOrderStatusGson>> userOrderStatusGsons) {
        lists.addAll(userOrderStatusGsons);
        Log.i(TAG, "onLoadMore: 54353636364" + page);
        userGoodsStatusAdapter.replaceData(lists);
        userGoodsStatusAdapter.notifyDataSetChanged();
        smlAllOrder.finishRefresh();
        smlAllOrder.finishLoadMore();
    }
}
