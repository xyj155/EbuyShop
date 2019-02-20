package com.example.home.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.util.RouterUtil;
import com.example.home.adapter.PurseGoodsAdapter;
import com.example.home.contract.GoodsSearchContract;
import com.example.home.presenter.GoodsSeaechPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = RouterUtil.GOODSSEARCH)
public class SearchGoodsActivity extends BaseActivity<GoodsSearchContract.View, GoodsSeaechPresenter> implements GoodsSearchContract.View {


    @BindView(R2.id.tv_cancel)
    TextView tvCancel;
    @BindView(R2.id.rl_input)
    RelativeLayout rlInput;
    @BindView(R2.id.tv_reconmend)
    TextView tvReconmend;
    @BindView(R2.id.ll_remend)
    LinearLayout llRemend;
    @BindView(R2.id.ry_goods)
    RecyclerView ryGoods;
    @BindView(R2.id.et_input)
    EditText etInput;
    private PurseGoodsAdapter purseGoodsAdapter;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsSeaechPresenter getPresenter() {
        return new GoodsSeaechPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_search_goods;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        ryGoods.setLayoutManager(new GridLayoutManager(SearchGoodsActivity.this,2));
        purseGoodsAdapter = new PurseGoodsAdapter(null, SearchGoodsActivity.this);
        ryGoods.setAdapter(purseGoodsAdapter);
        purseGoodsAdapter.setEmptyView(View.inflate(SearchGoodsActivity.this,R.layout.empty_search_layout,null));
    }

    @Override
    public void initData() {
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: " + s);
                if (!s.toString().isEmpty()) {
                    tvReconmend.setText(s);
                    llRemend.setVisibility(View.VISIBLE);
                } else {
                    llRemend.setVisibility(View.GONE);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvReconmend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = tvReconmend.getText().toString();
                mPresenter.querySearchByLikeWord(input);
                llRemend.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick(R2.id.tv_cancel)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void querySearchByLikeWord(List<GoodsGson> list) {
        purseGoodsAdapter.replaceData(list);
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
    }


}
