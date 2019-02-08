package com.example.home.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.NewestShelfGson;
import com.example.commonlib.util.GlideUtil;
import com.example.home.adapter.NewestAdapter;
import com.example.home.contract.NewUpperShelfContract;
import com.example.home.presenter.NewUpperShelfPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.home.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewestFragment extends BaseFragment<NewUpperShelfPresenter> implements NewUpperShelfContract.View {
    RecyclerView ryNewest;
    Unbinder unbinder;
    SmartRefreshLayout smlTime;
    Unbinder unbinder1;
    private NewestAdapter newestAdapter;
    public static final String EXTRA_TEXT = "extra_text";


    private static final String TAG = "NewestFragment";

    @Override
    public void initData() {

        final Bundle bundle = getArguments();
        if (bundle != null) {
            Log.i(TAG, "initData: " + bundle.getString(EXTRA_TEXT));
            mPresenter.newUpperShelf(bundle.getString(EXTRA_TEXT));
        }
        smlTime.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (bundle != null) {
                    mPresenter.newUpperShelf(bundle.getString(EXTRA_TEXT));
                }

            }
        });
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        ryNewest = view.findViewById(R.id.ry_newest);
        smlTime = view.findViewById(R.id.sml_time);
        ryNewest.setLayoutManager(new LinearLayoutManager(getContext()));
        newestAdapter = new NewestAdapter(null, getActivity());
        ryNewest.setAdapter(newestAdapter);
        View inflate = View.inflate(getContext(), R.layout.common_empty, null);
        ImageView viewById = inflate.findViewById(R.id.iv_empty);
        GlideUtil.loadGeneralImage(R.drawable.ic_empty_new_shelf, viewById);
        newestAdapter.setEmptyView(inflate);


    }


    @Override
    public int initLayout() {
        return R.layout.newest_fragment;
    }

    @Override
    public NewUpperShelfPresenter initPresenter() {
        return new NewUpperShelfPresenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadDateList(NewestShelfGson timeBeans) {
        Log.i(TAG, "loadDateList: " + timeBeans.getGoods().toString());
        if (timeBeans.getGoods().size() > 0) {
            newestAdapter.replaceData(timeBeans.getGoods());
        }
        if (smlTime != null)
            smlTime.finishRefresh();

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
        dialogCancel();
        if (smlTime != null)
            smlTime.finishRefresh();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
