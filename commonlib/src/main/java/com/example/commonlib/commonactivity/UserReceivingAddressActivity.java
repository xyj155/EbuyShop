package com.example.commonlib.commonactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.adapter.UserReceiveAddressAdapter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.UserReceivingAddressContract;
import com.example.commonlib.gson.UserReceiveAddressGson;
import com.example.commonlib.presenter.UserReceivingAddressPresenter;
import com.example.commonlib.util.RouterUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterUtil.USERSAVEADDRESS)
public class UserReceivingAddressActivity extends BaseActivity<UserReceivingAddressContract.View, UserReceivingAddressPresenter> implements UserReceivingAddressContract.View {


    @BindView(R2.id.ry_receive)
    RecyclerView ryReceive;
    @BindView(R2.id.tv_add_news_address)
    TextView tvAddNewsAddress;
    private UserReceiveAddressAdapter userReceiveAddressAdapter ;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserReceivingAddressPresenter getPresenter() {
        return new UserReceivingAddressPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_receiving_address;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        userReceiveAddressAdapter = new UserReceiveAddressAdapter(null,UserReceivingAddressActivity.this);
        initToolBar().setToolBarTitle("我的收货地址");
        ryReceive.setLayoutManager(new LinearLayoutManager(UserReceivingAddressActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.queryUserReceiveAddress("1");
    }

    @Override
    public void initData() {

    }

    private List<UserReceiveAddressGson> userReceiveAddressGsonList = new ArrayList<>();

    @Override
    public void loadUserReceiveAddress(List<UserReceiveAddressGson> userReceiveAddressGsons) {
        userReceiveAddressGsonList.clear();
        for (int i = 0; i < userReceiveAddressGsons.size(); i++) {
            if (userReceiveAddressGsons.get(i).getIsDefault().equals("1")) {
                userReceiveAddressGsonList.add(0, userReceiveAddressGsons.get(i));
            } else {
                userReceiveAddressGsonList.add(userReceiveAddressGsons.get(i));
            }
        }
        userReceiveAddressAdapter.replaceData(userReceiveAddressGsonList);
        ryReceive.setAdapter(userReceiveAddressAdapter);
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
        hideDlalog();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.tv_add_news_address})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_add_news_address) {
            startActivity(AddressOfNewReceiptActivity.class);
        }
    }
}
