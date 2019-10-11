package com.example.commonlib.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonlib.R;
import com.example.commonlib.annotation.UserType;
import com.example.commonlib.receiver.LoginOutBroadcastReceiver;
import com.example.commonlib.util.ActivityCollector;
import com.example.commonlib.util.StatusBarUtil;
import com.example.commonlib.view.MyDialog;


public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>> extends FragmentActivity {
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();
    public T mPresenter;
    public Dialog progressDialog;
    private MyDialog myDialog1;

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

    //    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        ActivityCompat.finishAfterTransition(this);
//    }
    public void showMsgDialog(String title, String content, final OnItemClickListener onItemClickListener) {
        if (myDialog1 != null) {
            myDialog1.setContent(content);
            myDialog1.setTitle(title);
            myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
                @Override
                public void onCenterItemClick(MyDialog dialog, View view) {
                    int i = view.getId();
                    if (i == R.id.dialog_btn_close) {
                        dialog.dismiss();
                    } else if (i == R.id.dialog_btn_cancel) {
                        onItemClickListener.onConfirm(dialog);
                        dialog.dismiss();
                    }
                }
            });
            myDialog1.show();
        }

    }


    public interface OnItemClickListener {
        void onConfirm(MyDialog dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (isSetStatusBarTranslucent()) {
            StatusBarUtil.setStatusBarTranslucent(this);
            StatusBarUtil.setStatusBarTextColor(this);
        }

        ActivityCollector.addActivity(this);
        mPresenter = getPresenter();
        //设置布局
        setContentView(intiLayout());
        progressDialog = new Dialog(BaseActivity.this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.base_dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("");
        //初始化控件
        initView();
        //设置数据
        initData();
        myDialog1 = new MyDialog(this, new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
    }


    public abstract boolean isSetStatusBarTranslucent();

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xuyijie.ebuyshop.loginout");
        locallReceiver = new LoginOutBroadcastReceiver();
        registerReceiver(locallReceiver, intentFilter);

        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 取消注册广播接收器
        unregisterReceiver(locallReceiver);
    }


    protected LoginOutBroadcastReceiver locallReceiver;

    public void createDialog(String msgStr) {
        if (progressDialog!=null){
            progressDialog.show();
        }
    }

    public void mhideDialog() {
        if ( progressDialog != null)
            progressDialog.cancel();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        ActivityCollector.removeActivity(this);
        progressDialog = null;

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
                overridePendingTransition(R.anim.anim_popup_zoom_enter, R.anim.anim_popup_zoom_exit);
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