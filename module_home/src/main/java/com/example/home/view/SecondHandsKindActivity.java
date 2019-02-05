package com.example.home.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.SecondKindGson;
import com.example.home.contract.SecondHandsKindContract;
import com.example.home.presenter.SecondHandsKindPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondHandsKindActivity extends BaseActivity<SecondHandsKindContract.View, SecondHandsKindPresenter> implements SecondHandsKindContract.View {

    @BindView(R2.id.ry_kind)
    RecyclerView ryKind;
    private KindAdapter kindAdapter;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public SecondHandsKindPresenter getPresenter() {
        return new SecondHandsKindPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_second_hands_kind;
    }

    @Override
    public void initView() {
        initToolBar().setToolBarTitle("选择种类");
        ButterKnife.bind(this);
        ryKind.setLayoutManager(new LinearLayoutManager(SecondHandsKindActivity.this));
    }

    @Override
    public void initData() {
        mPresenter.queryAllSecondKind();
    }

    @Override
    public void setSecondKind(List<SecondKindGson> list) {
        kindAdapter = new KindAdapter(list);

        ryKind.setAdapter(kindAdapter);
        Log.i(TAG, "setSecondKind: " + list.size());
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
        mhideDialog();

    }


    public class KindAdapter extends BaseQuickAdapter<SecondKindGson, BaseViewHolder> {

        public KindAdapter(@Nullable List<SecondKindGson> data) {
            super(R.layout.dialog_kind_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final SecondKindGson item) {
            Log.i(TAG, ": "+item.getGoodsKind());
            helper.setText(R.id.tv_title, item.getGoodsKind())
                    .setOnClickListener(R.id.tv_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SecondHandsKindActivity.this, AddSecondHandTradingActivity.class);
                            intent.putExtra("name", item.getGoodsKind());
                            setResult(CODE, intent);
                            finish();
                        }
                    });
        }
    }

    public static final int CODE = 0x1;
}
