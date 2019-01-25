package com.example.commonlib.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonlib.R;
import com.example.commonlib.annotation.UserType;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected T mPresenter;
    protected Context mContext;//activity的上下文对象
    protected Bundle mBundle;
    private Dialog progressDialog;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBundle != null) {
            outState.putBundle("bundle", mBundle);
        }
    }

    public void loginWraper(UserType type, Class context) {
        switch (type) {
            case ISPERMITED:
                startActivity(new Intent(getContext(), context));
                break;
            case NOTPERMITED:
                Toast.makeText(getContext(), "你还没有登录哦！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public BaseFragment createDialog(String msgStr) {
        progressDialog = new Dialog(getActivity(), R.style.progress_dialog);
        progressDialog.setContentView(R.layout.base_dialog);

        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(msgStr);
        progressDialog.show();
        return this;
    }

    public BaseFragment setDialogCancelable(boolean cancelable) {
        progressDialog.setCancelable(cancelable);
        return this;
    }

    public void dialogCancel() {
        progressDialog.dismiss();
    }

    /**
     * 绑定activity
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public abstract void initData();

    public abstract void initView(View view);

    /**
     * 运行在onAttach之后
     * 可以接受别人传递过来的参数,实例化对象.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取bundle,并保存起来
        if (savedInstanceState != null) {
            mBundle = savedInstanceState.getBundle("bundle");
        } else {
            mBundle = getArguments() == null ? new Bundle() : getArguments();
        }
        //创建presenter
        mPresenter = initPresenter();
    }

    /**
     * 运行在onCreate之后
     * 生成view视图
     */
    Unbinder unbinder;
    private ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(initLayout(), container, false);
        unbinder = ButterKnife.bind(this, inflate);
        this.viewGroup = container;
        initView(inflate);
        initData();
        return inflate;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public abstract int initLayout();

    /**
     * 类似Activity的OnBackgress
     * fragment进行回退
     */
    public void onBack() {
        getFragmentManager().popBackStack();
    }

    /**
     * 初始化Fragment应有的视图
     *
     * @return
     */
//    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 创建prensenter
     *
     * @return <T extends BasePresenter> 必须是BasePresenter的子类
     */
    public abstract T initPresenter();

    @Override
    public Context getContext() {
        return mContext;
    }


}
