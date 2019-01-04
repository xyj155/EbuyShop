package com.example.kind.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.util.RouterUtil;
import com.example.kind.adapter.GoodsKindItemAdapter;
import com.example.kind.adapter.ItemListAdapter;
import com.example.kind.contract.KindContract;
import com.example.kind.presenter.KindPresenter;
import com.xuyijie.kind.R;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtil.Kind_Fragment_Main)
public class KindFragment extends BaseFragment<KindPresenter> implements KindContract.View {

    private ItemListAdapter itemListAdapter;
    private RecyclerView ryItem, ryItemList;
    private GoodsKindItemAdapter goodsKindItem;
    List<KindItemGson> kindItemGsonList = new ArrayList<>();
    List<KindItemGson> goodsList = new ArrayList<>();

    @Override
    public void initData() {
        mPresenter.getGoodsList();

    }

    private static final String TAG = "KindFragment";

    @Override
    public void initView(View view) {
        ryItem = view.findViewById(R.id.ry_item);
        ryItemList = view.findViewById(R.id.ry_item_list);
        ryItemList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ryItem.setLayoutManager(new LinearLayoutManager(getContext()));
        itemListAdapter = new ItemListAdapter(kindItemGsonList);
        goodsKindItem = new GoodsKindItemAdapter(goodsList, getContext());
        mPresenter.getGoodsItemList(1);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_kind;
    }

    @Override
    public KindPresenter initPresenter() {
        return new KindPresenter(this);
    }

    @Override
    public void setGoodsList(List<KindItemGson> userGson) {
        kindItemGsonList.clear();
        kindItemGsonList.addAll(userGson);
        ryItem.setAdapter(itemListAdapter);
        itemListAdapter.setOnItemClickListener(new ItemListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(int pid) {
                Log.i(TAG, "onClickListener: " + pid);
                mPresenter.getGoodsItemList(pid);
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

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }


//    @Override
//    public void setGoodsList(List<KindItemGson> userGson) {
//        Log.i(TAG, "setGoodsList: " + userGson.get(0));
//
//
//
//
//
//
//
//
//    }
//
//    @Override
//    public void setGoodsItemList(List<KindItemGson> userGson) {
//        Log.i(TAG, "setGoodsItemList: " + userGson);
//    }
//
//    @Override
//    public void showError(String msg) {
//        Log.i(TAG, "showError: " + msg);
//    }
//
//    @Override
//    public void showDialog(String msg) {
//
//    }
//
//    @Override
//    public void showDialog() {
//
//    }
//
//    @Override
//    public void hideDialog() {
//
//    }
}