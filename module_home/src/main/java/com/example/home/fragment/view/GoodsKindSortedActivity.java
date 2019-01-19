package com.example.home.fragment.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.KindItemGson;
import com.example.home.fragment.adapter.GoodsKindItemAdapter;
import com.example.home.fragment.adapter.ItemListAdapter;
import com.example.home.fragment.contract.KindContract;
import com.example.home.fragment.presenter.KindPresenter;
import com.xuyijie.home.R;

import java.util.ArrayList;
import java.util.List;

public class GoodsKindSortedActivity extends BaseActivity<KindContract.View, KindPresenter> implements KindContract.View {

    private ItemListAdapter itemListAdapter;
    private RecyclerView ryItem, ryItemList;
    private GoodsKindItemAdapter goodsKindItem;
    List<KindItemGson> kindItemGsonList = new ArrayList<>();
    List<KindItemGson> goodsList = new ArrayList<>();
    private TextView tvName;
    private ImageView ivBack;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public KindPresenter getPresenter() {
        return new KindPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_kind_sorted;
    }

    @Override
    public void initView() {
        tvName = findViewById(R.id.tv_name);
        ryItem = findViewById(R.id.ry_item);
        ivBack = findViewById(R.id.iv_back);
        ryItemList = findViewById(R.id.ry_item_list);
        ryItemList.setLayoutManager(new GridLayoutManager(GoodsKindSortedActivity.this, 3));
        ryItem.setLayoutManager(new LinearLayoutManager(GoodsKindSortedActivity.this));
        itemListAdapter = new ItemListAdapter(kindItemGsonList);
        goodsKindItem = new GoodsKindItemAdapter(goodsList, GoodsKindSortedActivity.this);
        mPresenter.getGoodsItemList(1);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getGoodsList();
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
    public void setGoodsList(List<KindItemGson> userGson) {
        Log.i(TAG, "setGoodsList: " + userGson.size());
        kindItemGsonList.clear();
        kindItemGsonList.addAll(userGson);
        ryItem.setAdapter(itemListAdapter);
        itemListAdapter.setOnItemClickListener(new ItemListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(int pid, String name) {
                Log.i(TAG, "onClickListener: " + pid);
                mPresenter.getGoodsItemList(pid);
                tvName.setText(name);
            }
        });
    }

    @Override
    public void setGoodsItemList(List<KindItemGson> userGson) {
        Log.i(TAG, "setGoodsItemList: " + userGson.size());
        goodsList.clear();
        goodsList.addAll(userGson);
        ryItemList.setAdapter(goodsKindItem);
        goodsKindItem.notifyDataSetChanged();
    }
}
