package com.example.user.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.user.adapter.UserGoodsStatusAdapter;
import com.example.user.contract.UserFormStatusContract;
import com.example.user.presenter.UserFormStatusPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubstituteShipmentFragment extends BaseFragment<UserFormStatusPresenter> implements UserFormStatusContract.View {
    @BindView(R2.id.ry_send)
    RecyclerView rySend;
    @BindView(R2.id.sml_send)
    SmartRefreshLayout smlSend;
    Unbinder unbinder;
    private UserGoodsStatusAdapter userGoodsStatusAdapter;

    @Override
    public void initData() {
        smlSend.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "2");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "2");
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        userGoodsStatusAdapter = new UserGoodsStatusAdapter(null, getContext());
        userGoodsStatusAdapter.setEmptyView(View.inflate(getContext(), R.layout.order_empty_layout, null));
        rySend.setLayoutManager(new LinearLayoutManager(getContext()));
        rySend.setAdapter(userGoodsStatusAdapter);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_substitutes_shipment;
    }

    @Override
    public UserFormStatusPresenter initPresenter() {
        return new UserFormStatusPresenter(this);
    }

    private static final String TAG = "SubstituteShipmentFragm";

    @Override
    public void loadUserOrderByStatus(List<List<UserOrderStatusGson>> userOrderStatusGsons) {
        Log.i(TAG, "loadUserOrderByStatus: " + userOrderStatusGsons.size());
        userGoodsStatusAdapter.replaceData(userOrderStatusGsons);
        smlSend.finishRefresh();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.show("暂无物流信息！");
    }

    @Override
    public void showDialog(String msg) {
        createDialog(msg);
    }

    @Override
    public void hideDialog() {
        dialogCancel();
        smlSend.finishRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
