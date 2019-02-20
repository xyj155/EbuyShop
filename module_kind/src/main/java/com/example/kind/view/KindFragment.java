package com.example.kind.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.util.RouterUtil;
import com.example.kind.adapter.GoodsKindItemAdapter;
import com.example.kind.adapter.ItemListAdapter;
import com.example.kind.contract.KindContract;
import com.example.kind.presenter.KindPresenter;
import com.xuyijie.kind.R;
import com.xuyijie.kind.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterUtil.Kind_Fragment_Main)
public class KindFragment extends BaseFragment<KindPresenter> implements KindContract.View {

    @BindView(R2.id.tv_search)
    TextView tvSearch;
    Unbinder unbinder;
    private ItemListAdapter itemListAdapter;
    private RecyclerView ryItem, ryItemList;
    private GoodsKindItemAdapter goodsKindItem;
    List<KindItemGson> kindItemGsonList = new ArrayList<>();
    List<KindItemGson> goodsList = new ArrayList<>();
    private TextView tvName;

    @Override
    public void initData() {
        mPresenter.getGoodsList();

    }

    private static final String TAG = "KindFragment";

    @Override
    public void initView(View view) {
        tvName = view.findViewById(R.id.tv_name);
        ryItem = view.findViewById(R.id.ry_item);
        ryItemList = view.findViewById(R.id.ry_item_list);
        ryItemList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ryItem.setLayoutManager(new LinearLayoutManager(getContext()));
        itemListAdapter = new ItemListAdapter(kindItemGsonList);
        goodsKindItem = new GoodsKindItemAdapter(goodsList, getActivity());
        mPresenter.getGoodsItemList(1);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterUtil.GOODSSEARCH).navigation();
            }
        });
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
}
