package com.example.commonlib.fragment;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.commonlib.R;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.view.SlideDetailsLayout;

public class GoodsInfoFragment extends BaseFragment<GoodsDetailPresenter> implements SlideDetailsLayout.OnSlideDetailsListener {
    private SlideDetailsLayout sv_switch;
    public GoodsDetailActivity activity;
    private FloatingActionButton fab_up_slide;
    @Override
    public void initData() {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsDetailActivity) context;
    }
    @Override
    public void initView(View view) {
        sv_switch = (SlideDetailsLayout) view.findViewById(R.id.sv_switch);
        fab_up_slide = (FloatingActionButton) view.findViewById(R.id.fab_up_slide);
        sv_switch.setOnSlideDetailsListener(this);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_goodsinfo;
    }

    @Override
    public GoodsDetailPresenter initPresenter() {
        return null;
    }

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            //当前为图文详情页
            fab_up_slide.show();
            activity.vp_content.setNoScroll(true);
            activity.tv_title.setVisibility(View.VISIBLE);
            activity.psts_tabs.setVisibility(View.GONE);
        } else {
            //当前为商品详情页
            fab_up_slide.hide();
            activity.vp_content.setNoScroll(false);
            activity.tv_title.setVisibility(View.GONE);
            activity.psts_tabs.setVisibility(View.VISIBLE);
        }
    }
}
