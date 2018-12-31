package com.example.commonlib.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.commonlib.util.StatusBarUtil;

public abstract class BaseActivity<V extends BaseView,T extends BasePresenter<V>> extends FragmentActivity { ;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();
    public T mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (isSetStatusBarTranslucent()){
            StatusBarUtil.setStatusBarTranslucent(this);
        }

        mPresenter = getPresenter();
        //设置布局
        setContentView(intiLayout());
        //初始化控件
        initView();
        //设置数据
        initData();
    }
    public abstract boolean isSetStatusBarTranslucent();
    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }



    public abstract T getPresenter();

    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();






}
