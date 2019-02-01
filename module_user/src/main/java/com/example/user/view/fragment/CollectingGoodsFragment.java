package com.example.user.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.MyDialog;
import com.example.user.adapter.UserGoodsStatusAdapter;
import com.example.user.contract.UpdateOrderStatusContract;
import com.example.user.contract.UserFormStatusContract;
import com.example.user.contract.UserOrderDeleteContract;
import com.example.user.presenter.UpdateOrderStatusPresenter;
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

public class CollectingGoodsFragment extends BaseFragment<UserFormStatusPresenter> implements UserFormStatusContract.View, UserOrderDeleteContract.View, UpdateOrderStatusContract.View {
    @BindView(R2.id.ry_collecting)
    RecyclerView ryCollecting;
    @BindView(R2.id.sml_collecting)
    SmartRefreshLayout smlCollecting;
    Unbinder unbinder;
    private UserGoodsStatusAdapter userGoodsStatusAdapter;

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        ryCollecting.setLayoutManager(new LinearLayoutManager(getContext()));
        userGoodsStatusAdapter = new UserGoodsStatusAdapter(null, getContext());
        smlCollecting.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "3");
            }
        });
        ryCollecting.setAdapter(userGoodsStatusAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "3");
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_collection_goods;
    }

    @Override
    public UserFormStatusPresenter initPresenter() {
        return new UserFormStatusPresenter(this);
    }

    @Override
    public void loadUserOrderByStatus(List<List<UserOrderStatusGson>> userOrderStatusGsons) {
        userGoodsStatusAdapter.setEmptyView(View.inflate(getContext(), R.layout.order_empty_layout, null));
        userGoodsStatusAdapter.replaceData(userOrderStatusGsons);
        smlCollecting.finishRefresh();
        userGoodsStatusAdapter.setOnReceiveListener(new UserGoodsStatusAdapter.OnReceiveListener() {
            @Override
            public void onReceive(final String orderNum) {
                final MyDialog myDialog1 = new MyDialog(getContext(), new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
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
                            Log.i(TAG, "onCenterItemClick: "+orderNum);
                            Log.i(TAG, "onCenterItemClick: "+(String) SharePreferenceUtil.getUser("uid", "String"));
                            updateOrderStatusPresenter.updateOrderStatusByReceive("1", orderNum);
                        }
                    }
                });
                myDialog1.show();
            }
        });
    }

    private UpdateOrderStatusPresenter updateOrderStatusPresenter = new UpdateOrderStatusPresenter(this);
    private static final String TAG = "CollectingGoodsFragment";
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
        smlCollecting.finishRefresh();
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
    public void deleteSuccess(boolean isDelete) {

    }

    @Override
    public void updateStatus(boolean success) {
        if (success) {
            mPresenter.queryUserOrderByStatus((String) SharePreferenceUtil.getUser("uid", "String"), "3");
        }else {
            Toast.makeText(getContext(), "确认收货失败！", Toast.LENGTH_SHORT).show();
        }
    }
}
