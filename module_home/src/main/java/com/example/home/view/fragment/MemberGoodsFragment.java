package com.example.home.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.GoodsGson;
import com.example.home.adapter.MemberGoodsAdapter;
import com.example.home.contract.MemberShipRightContract;
import com.example.home.presenter.MemberShipRightPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MemberGoodsFragment extends BaseFragment<MemberShipRightPresenter> implements MemberShipRightContract.View {
    @BindView(R2.id.ry_goods)
    RecyclerView ryGoods;
    Unbinder unbinder;
    private MemberGoodsAdapter newestAdapter;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        ryGoods.setLayoutManager(new LinearLayoutManager(getContext()));
        mPresenter.queryMemberShipGoods();
        newestAdapter = new MemberGoodsAdapter(null, getContext());
        ryGoods.setAdapter(newestAdapter);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_member_ship_goods_right;
    }

    @Override
    public MemberShipRightPresenter initPresenter() {
        return new MemberShipRightPresenter(this);
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
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void queryMemberShipGoods(List<GoodsGson> goodsGsonList) {
        newestAdapter.replaceData(goodsGsonList);
    }
}
