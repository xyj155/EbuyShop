package com.example.kind.view;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.presenter.HomePresent;
import com.example.commonlib.util.RouterUtil;
import com.example.kind.adapter.ItemListAdapter;
import com.xuyijie.kind.R;

import java.util.ArrayList;
import java.util.Arrays;

@Route(path = RouterUtil.Kind_Fragment_Main)
public class KindFragment extends BaseFragment<HomePresent> {

    private ItemListAdapter itemListAdapter;
    private ViewDataBinding bind;
    private RecyclerView ryItem;
    private String[] arr = {"推荐分类", "上衣", "裙装", "鞋靴", "包类", "服配", "居家", "美妆", "护理", "数码3C", "电器", "运动"};

    @Override
    public void initData() {

    }

    private static final String TAG = "KindFragment";

    @Override
    public void initView(View view) {
        ryItem = view.findViewById(R.id.ry_item);
        ryItem.setLayoutManager(new LinearLayoutManager(getContext()));
        itemListAdapter = new ItemListAdapter(new ArrayList<>(Arrays.asList(arr)));
        ryItem.setAdapter(itemListAdapter);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_kind;
    }

    @Override
    public HomePresent initPresenter() {
        return null;
    }
}
