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
import com.example.commonlib.view.MyDialog;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.user.adapter.UserGoodsStatusAdapter;
import com.example.user.contract.UserFormStatusContract;
import com.example.user.contract.UserOrderDeleteContract;
import com.example.user.presenter.UserFormStatusPresenter;
import com.example.user.presenter.UserOrderDeletePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubstitutePaymentFragment extends BaseFragment<UserFormStatusPresenter> implements UserFormStatusContract.View, UserOrderDeleteContract.View {
    @BindView(R2.id.ry_payment)
    RecyclerView ryPayment;
    Unbinder unbinder;
    @BindView(R2.id.sml_payment)
    SmartRefreshLayout smlPayment;
    private UserGoodsStatusAdapter userGoodsStatusAdapter;
    final UserOrderDeletePresenter userOrderDeletePresenter = new UserOrderDeletePresenter(this);
    private static final String TAG = "SubstitutePaymentFragme";

    @Override
    public void initData() {
        smlPayment.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "1");
                if (userGoodsStatusAdapter != null)
                    userGoodsStatusAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "1");
    }

    private MyDialog myDialog1;

    @Override
    public void initView(View view) {
        userGoodsStatusAdapter = new UserGoodsStatusAdapter(null, getContext());
        userGoodsStatusAdapter.setEmptyView(View.inflate(getContext(), R.layout.order_empty_layout, null));
        ryPayment.setLayoutManager(new LinearLayoutManager(getContext()));
        ryPayment.setAdapter(userGoodsStatusAdapter);
        userGoodsStatusAdapter.setOnClickListener(new UserGoodsStatusAdapter.onClickListener() {
            @Override
            public void onClickListener(final String orderNum) {
                myDialog1 = new MyDialog(getContext(), new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
                myDialog1.setContent("删除订单你将可以在全部订单中查看");
                myDialog1.setTitle("是否删除此订单");
                myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
                    @Override
                    public void onCenterItemClick(MyDialog dialog, View view) {
                        int i = view.getId();
                        if (i == R.id.dialog_btn_close) {
                            myDialog1.dismiss();
                        } else if (i == R.id.dialog_btn_cancel) {
                            myDialog1.dismiss();
                            userOrderDeletePresenter.deleteOrderByOrderNum(orderNum, (String) SharePreferenceUtil.getUser("uid", "String"));
                        }
                    }
                });
                myDialog1.show();

            }
        });
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_substitutes_payment;
    }

    @Override
    public UserFormStatusPresenter initPresenter() {
        return new UserFormStatusPresenter(this);
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
        smlPayment.finishRefresh();
        dialogCancel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadUserOrderByStatus(List<List<UserOrderStatusGson>> userOrderStatusGsons) {
        Log.i(TAG, "loadUserOrderByStatus:313131231 " + userOrderStatusGsons.size());
        userGoodsStatusAdapter.replaceData(userOrderStatusGsons);
        userGoodsStatusAdapter.notifyDataSetChanged();
        smlPayment.finishRefresh();
    }

    @Override
    public void deleteSuccess(boolean isDelete) {
        if (isDelete) {
            mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "1");
            userGoodsStatusAdapter.notifyDataSetChanged();
            ToastUtils.show("删除成功");
        } else {
            ToastUtils.show("删除失败");
        }

    }
}
