package com.example.user.view;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
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


    @OnClick({R2.id.tv_my_vip})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_my_vip) {
            startActivity(new Intent(getContext(), UserCouponActivity.class));
        }
    }
}
