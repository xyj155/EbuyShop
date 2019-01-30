package com.example.user.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.util.SharePreferenceUtil;
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

public class SubstitutesEvaluatedFragment extends BaseFragment<UserFormStatusPresenter> implements UserFormStatusContract.View {
    @BindView(R2.id.ry_evaluate)
    RecyclerView ryEvaluate;
    @BindView(R2.id.sml_evaluate)
    SmartRefreshLayout smlEvaluate;
    Unbinder unbinder;
    private UserGoodsStatusAdapter userGoodsStatusAdapter;

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "4");
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        smlEvaluate.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "4");
            }
        });
        userGoodsStatusAdapter = new UserGoodsStatusAdapter(null, getContext());
        userGoodsStatusAdapter.setEmptyView(View.inflate(getContext(), R.layout.order_empty_layout, null));
        ryEvaluate.setLayoutManager(new LinearLayoutManager(getContext()));
        ryEvaluate.setAdapter(userGoodsStatusAdapter);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_substitutes_evaluated;
    }

    @Override
    public UserFormStatusPresenter initPresenter() {
        return new UserFormStatusPresenter(this);
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

    @Override
    public void loadUserOrderByStatus(List<List<UserOrderStatusGson>> userOrderStatusGsons) {
        userGoodsStatusAdapter.replaceData(userOrderStatusGsons);
        smlEvaluate.finishRefresh();
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
        dialogCancel();
        smlEvaluate.finishRefresh();
    }
}
