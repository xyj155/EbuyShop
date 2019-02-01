package com.example.commonlib.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonlib.R;
import com.example.commonlib.annotation.UserType;
import com.example.commonlib.util.StatusBarUtil;


public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>> extends FragmentActivity {
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();
    public T mPresenter;
    private Dialog progressDialog;

    public void loginWraper(UserType type, Class context) {
        switch (type) {
            case ISPERMITED:
                startActivity(new Intent(this, context));
                break;
            case NOTPERMITED:
                Toast.makeText(this, "你还没有登录哦！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (isSetStatusBarTranslucent()) {
            StatusBarUtil.setStatusBarTranslucent(this);
        }

        mPresenter = getPresenter();
        //设置布局
        setContentView(intiLayout());
        getWindow().setEnterTransition(new Explode().setDuration(400));
        getWindow().setExitTransition(new Explode().setDuration(400));
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

    public void createDialog(String msgStr) {
        progressDialog = new Dialog(BaseActivity.this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.base_dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(msgStr);
        progressDialog.show();
    }

    public void mhideDialog() {
        progressDialog.cancel();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

    }

    private ImageView ivClose;
    private TextView tvTitle;
    private TextView tvMenu;

    public BaseActivity initToolBar() {
        ivClose = findViewById(R.id.iv_close);
        tvTitle = findViewById(R.id.tv_title);
        tvMenu = findViewById(R.id.tv_menu);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return this;
    }

    public BaseActivity setToolBarTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public BaseActivity setToolBarMenu(String menu) {
        tvMenu.setText(menu);
        return this;
    }

    public BaseActivity setToolBarMenuClickListener(final onMenuClickListener onMenuClickListener) {
        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuClickListener.onMenuClickListener();
            }
        });
        return this;
    }

    private onMenuClickListener onMenuClickListener;

    public void setOnMenuClickListener(BaseActivity.onMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public interface onMenuClickListener {
        void onMenuClickListener();
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