package com.example.user.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.user.adapter.UserCollectionAdapter;
import com.example.user.contract.UserCollectionContract;
import com.example.user.presenter.UserCollectionPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCollectionActivity extends BaseActivity<UserCollectionContract.View, UserCollectionPresenter> implements UserCollectionContract.View {


    @BindView(R2.id.ry_collection)
    RecyclerView ryCollection;
    @BindView(R2.id.sml_collection)
    SmartRefreshLayout smlCollection;
    private UserCollectionAdapter userCollectionAdapter;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserCollectionPresenter getPresenter() {
        return new UserCollectionPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_collection;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("用户收藏");
        userCollectionAdapter = new UserCollectionAdapter(null, UserCollectionActivity.this);
        ryCollection.setLayoutManager(new LinearLayoutManager(UserCollectionActivity.this));
        mPresenter.queryUserCollection(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
        smlCollection.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryUserCollection(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
            }
        });
        ryCollection.setAdapter(userCollectionAdapter);
        View inflate = View.inflate(UserCollectionActivity.this, R.layout.collection_empty_layout, null);
        GlideUtil.loadGeneralImage(R.drawable.live_pack_empty_ic, (ImageView) inflate.findViewById(R.id.iv_empty));
        userCollectionAdapter.setEmptyView(inflate);

    }

    @Override
    public void initData() {

    }


    @Override
    public void setUserCollection(List<GoodsGson> goodsGsons) {
        userCollectionAdapter.replaceData(goodsGsons);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog(msg);
        smlCollection.finishRefresh();
    }

    @Override
    public void hideDialog() {
        mhideDialog();
        smlCollection.finishRefresh();
    }
}
