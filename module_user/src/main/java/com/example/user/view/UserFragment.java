package com.example.user.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.presenter.LoginPresent;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.WaveView;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route(path = RouterUtil.Me_Fragment_Main)
public class UserFragment extends BaseFragment<LoginPresent> {
    @BindView(R2.id.tv_waitpay)
    TextView tvWaitpay;
    @BindView(R2.id.tv_waitsend)
    TextView tvWaitsend;
    @BindView(R2.id.tv_wait_receiver)
    TextView tvWaitReceiver;
    @BindView(R2.id.tv_wait_evaluate)
    TextView tvWaitEvaluate;
    Unbinder unbinder1;
    private WaveView waveView;
    private ImageView ivHead;
    @BindView(R2.id.tv_my_vip)
    TextView tvCoupon;
    Unbinder unbinder;

    @Override
    public void initData() {

    }


    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        waveView = view.findViewById(R.id.wave_view);
        ivHead = view.findViewById(R.id.ivHead);
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0, 0, 0, (int) y + 18);
                ivHead.setLayoutParams(lp);
            }
        });
        Glide.with(getContext()).asBitmap().load(SharePreferenceUtil.getUser("avatar", "String")).into(ivHead);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public LoginPresent initPresenter() {
        return null;
    }


    @OnClick({R2.id.tv_my_vip, R2.id.tv_waitpay, R2.id.tv_waitsend, R2.id.tv_wait_receiver, R2.id.tv_wait_evaluate})
    public void onViewClicked(View view) {
        int id = view.getId();
        Intent intent = new Intent(getContext(), UserFormStatusActivity.class);
        if (id == R.id.tv_my_vip) {
        } else if (id == R.id.tv_waitpay) {
            intent.putExtra("index", 1);
        } else if (id == R.id.tv_waitsend) {
            intent.putExtra("index", 2);
        } else if (id == R.id.tv_wait_receiver) {
            intent.putExtra("index", 3);
        } else if (id == R.id.tv_wait_evaluate) {
            intent.putExtra("index", 4);
        }
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

}
