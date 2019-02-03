package com.example.user.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class UserGoodsStatusFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String > title;

    public UserGoodsStatusFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> title) {
        super(fm);
        this.fragmentList = fragmentList;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
