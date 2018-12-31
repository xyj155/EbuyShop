package com.example.commonlib.view;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.util.RouterUtil;

public class FragmentUtils {

    public static Fragment getHomeFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouterUtil.Home_Fragment_Main).navigation();
        return fragment;
    }

    public static Fragment getKindFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouterUtil.Kind_Fragment_Main).navigation();
        return fragment;
    }

    public static Fragment getShopCarFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouterUtil.ShopCar_Fragment_Main).navigation();
        return fragment;
    }

    public static Fragment getMeFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouterUtil.Me_Fragment_Main).navigation();
        return fragment;
    }
}
