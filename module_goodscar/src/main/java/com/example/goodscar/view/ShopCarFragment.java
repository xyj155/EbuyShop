package com.example.goodscar.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.commonactivity.GoodsPaymentActivity;
import com.example.commonlib.gson.ShopCarGson;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.goodscar.adapter.ShopCarAdapter;
import com.example.goodscar.contract.ShopCarContract;
import com.example.goodscar.contract.SubmitOrderContract;
import com.example.goodscar.presenter.ShopCarPresenter;
import com.example.goodscar.presenter.SubmitOrderPresenter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.goodscar.R;
import com.xuyijie.goodscar.R2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterUtil.ShopCar_Fragment_Main)
public class ShopCarFragment extends BaseFragment<ShopCarPresenter> implements ShopCarContract.View, SubmitOrderContract.View {

    @BindView(R2.id.llTitle)
    LinearLayout llTitle;
    @BindView(R2.id.ry_goodscar)
    RecyclerView ryGoodscar;
    @BindView(R2.id.sml_shopcar)
    SmartRefreshLayout smlShopcar;
    @BindView(R2.id.rb_all_check)
    CheckBox rbAllCheck;
    @BindView(R2.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R2.id.tv_total_count)
    TextView tvTotalCount;
    @BindView(R2.id.ll_shopcar)
    LinearLayout llShopcar;
    Unbinder unbinder;
    @BindView(R2.id.iv_count)
    ImageView ivCount;
    Unbinder unbinder1;
    @BindView(R2.id.rlshopcar)
    LinearLayout rlShopcar;
    public ShopCarAdapter shopCarAdapter;
    private SubmitOrderPresenter submitOrderPresenter = new SubmitOrderPresenter(this);

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        shopCarAdapter = new ShopCarAdapter(null, this, getActivity());
        ryGoodscar.setLayoutManager(new LinearLayoutManager(getContext()));
        ryGoodscar.setAdapter(shopCarAdapter);
        shopCarAdapter.bindToRecyclerView(ryGoodscar);
        smlShopcar.autoRefresh();
        smlShopcar.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryUserShopCarByUid("1", true);
                rbAllCheck.setChecked(false);
            }
        });
        shopCarAdapter.setUpdateShopCar(new ShopCarAdapter.UpdateShopCarInterface() {
            @Override
            public void uploadShopCar() {
                mPresenter.queryUserShopCarByUid("1", false);
                createDialog("");
            }

            @Override
            public void finishUpload() {
                dialogCancel();
            }
        });
        rbAllCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCarAdapter.getData().size() != 0) {
                    if (rbAllCheck.isChecked()) {
                        for (int i = 0; i < shopCarAdapter.getData().size(); i++) {
                            shopCarAdapter.getData().get(i).setCheck(true);
                        }
                    } else {
                        for (int i = 0; i < shopCarAdapter.getData().size(); i++) {
                            shopCarAdapter.getData().get(i).setCheck(false);
                        }
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shopCarAdapter.notifyDataSetChanged();
                        }
                    }, 100);
                    statistics();
                }
            }
//        rbAllCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (shopCarAdapter.getData().size() != 0) {
//                    if (isChecked) {
//                        for (int i = 0; i < shopCarAdapter.getData().size(); i++) {
//                            shopCarAdapter.getData().get(i).setCheck(true);
//                        }
//                    } else {
//                        for (int i = 0; i < shopCarAdapter.getData().size(); i++) {
//                            shopCarAdapter.getData().get(i).setCheck(false);
//                        }
//                    }
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            shopCarAdapter.notifyDataSetChanged();
//                        }
//                    }, 100);
//                    statistics();
//                }
//            }
        });
        shopCarAdapter.setCheckInterface(new ShopCarAdapter.CheckInterface() {
            @Override
            public void checkGroup(int position, boolean isChecked) {
                if (isAllCheck()) {
                    rbAllCheck.setChecked(true);
                } else {
                    rbAllCheck.setChecked(false);
                }
                statistics();
            }
        });
        shopCarAdapter.setOnGoodsItemCheckChangeListener(new ShopCarAdapter.onGoodsItemCheckChangeListener() {
            @Override
            public void onGoodsItemCheckChangeListener(String goodsName, int goodsId, boolean isCheck) {
                if (isCheck) {
                    goodsList.add(String.valueOf(goodsId));
                } else {
                    goodsList.remove(String.valueOf(goodsId));
                }
            }
        });
    }

    private List<String> goodsList = new ArrayList<>();

    private boolean isAllCheck() {
        for (ShopCarGson group : shopCarAdapter.getData()) {
            if (!group.isCheck())
                return false;
        }
        return true;
    }


    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量

    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;

        for (int i = 0; i < shopCarAdapter.getData().size(); i++) {
            ShopCarGson shoppingCartBean = shopCarAdapter.getData().get(i);
            if (shoppingCartBean.isCheck()) {
                totalCount += shopCarAdapter.getData().get(i).getCount();
                totalPrice += Double.valueOf(shoppingCartBean.getGoodsPrice()) * shoppingCartBean.getCount();
            }
        }
        BigDecimal bigDecimal = new BigDecimal(totalPrice);
        tvTotalMoney.setText("合计:" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        tvTotalCount.setText("共  " + totalCount + " 件商品");
        if (totalCount <= 0) {
            ivCount.setVisibility(View.GONE);
            llShopcar.setVisibility(View.GONE);
        } else {
            ivCount.setVisibility(View.VISIBLE);
            llShopcar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryUserShopCarByUid("1", false);

    }

    private static final String TAG = "ShopCarFragment";

    @Override
    public int initLayout() {
        return R.layout.fragment_goodscar;
    }

    @Override
    public ShopCarPresenter initPresenter() {
        return new ShopCarPresenter(this);
    }

    private List<String> goodsIdList = new ArrayList<>();
    private List<ShopCarGson> shopCarGso = new ArrayList<>();

    @Override
    public void loadUserShopCar(final List<ShopCarGson> shopCarGsons) {
        if (shopCarGsons.size() == 0) {
            rlShopcar.setVisibility(View.VISIBLE);
        } else {
            rlShopcar.setVisibility(View.GONE);
        }
        shopCarGso.clear();
        shopCarGso.addAll(shopCarGsons);
        shopCarAdapter.replaceData(shopCarGso);
        smlShopcar.finishRefresh();

        llShopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                for (int i = 0; i < shopCarGsons.size(); i++) {
                    if (shopCarGsons.get(i).isCheck())
                        goodsIdList.add(String.valueOf(shopCarGsons.get(i).getId()));
                }
                submitOrderPresenter.submitUserOrder((String) SharePreferenceUtil.getUser("uid", "String"), gson.toJson(goodsIdList));
            }
        });
        statistics();
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
        dialogCancel();
        smlShopcar.finishRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void submitUserOrder(boolean submit,String orderNum) {
        Log.i(TAG, "submitUserOrder: " + submit);
        mPresenter.queryUserShopCarByUid("1", false);
        if (submit){
            Gson gson = new Gson();
            Intent intent = new Intent(getContext(), GoodsPaymentActivity.class);
            String jsonStr = gson.toJson(goodsList);
            intent.putExtra("goodsArray", jsonStr);
            intent.putExtra("orderNum", orderNum);
            startActivity(intent);
            goodsIdList.clear();
        }

    }
}
