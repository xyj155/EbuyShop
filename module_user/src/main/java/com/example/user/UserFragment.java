package com.example.user;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.presenter.HomePresent;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.view.WaveView;
import com.xuyijie.user.R;

@Route(path = RouterUtil.Me_Fragment_Main)
public class UserFragment extends BaseFragment<HomePresent> {
    private WaveView waveView;
    private ImageView ivHead;

    @Override
    public void initData() {

    }


    @Override
    public void initView(View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        DataBindingUtil.inflate(inflater, R.layout.fragment_user, null, false);
        waveView =view.findViewById(R.id.wave_view);
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
        Glide.with(getContext()).asBitmap().load(R.mipmap.ic_user_avatar_bg).into(ivHead);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public HomePresent initPresenter() {
        return null;
    }
}
