package com.example.user.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commonlib.MyApp;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.BrowserActivity;
import com.example.commonlib.commonactivity.UserReceivingAddressActivity;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.DataCleanManager;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.MyDialog;
import com.example.commonlib.view.SwitchButton;
import com.example.user.contract.UserPaymentContract;
import com.example.user.presenter.UserPaymentPresenter;
import com.tencent.bugly.beta.Beta;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserSettingActivity extends BaseActivity<UserPaymentContract.View, UserPaymentPresenter> {


    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.tv_message_push)
    TextView tvMessagePush;
    @BindView(R2.id.tv_user_address)
    TextView tvUserAddress;
    @BindView(R2.id.tv_clean_cache)
    TextView tvCleanCache;
    @BindView(R2.id.tv_update)
    TextView tvUpdate;
    @BindView(R2.id.tv_reback)
    TextView tvReback;
    @BindView(R2.id.tv_about)
    TextView tvAbout;
    @BindView(R2.id.tv_login)
    Button tvLogin;
    @BindView(R2.id.sw_default)
    SwitchButton swDefault;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserPaymentPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_setting;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("通用设置");
        try {
            tvCleanCache.setText(DataCleanManager.getTotalCacheSize(MyApp.getInstance()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        boolean user = (boolean) SharePreferenceUtil.getUser("isOpenSound", "boolean");
        swDefault.setChecked(user);
        swDefault.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Map<String, Object> map = new HashMap<>();
                if (isChecked) {
                    map.put("isOpenSound", true);
                } else {
                    map.put("isOpenSound", false);
                }
                SharePreferenceUtil.saveUser(map);
            }
        });

    }

    @OnClick({R2.id.tv_message_push, R2.id.tv_user_address, R2.id.tv_clean_cache, R2.id.tv_update, R2.id.tv_reback, R2.id.tv_about, R2.id.tv_login})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_message_push) {

        } else if (id == R.id.tv_user_address) {
            startActivity(UserReceivingAddressActivity.class);
        } else if (id == R.id.tv_clean_cache) {
            MyDialog myDialog1 = new MyDialog(this, new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
            myDialog1.setContent("确认清除应用缓存？");
            myDialog1.setTitle("清除应用缓存");
            myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
                @Override
                public void onCenterItemClick(MyDialog dialog, View view) {
                    int i = view.getId();
                    if (i == R.id.dialog_btn_close) {
                        dialog.dismiss();
                    } else if (i == R.id.dialog_btn_cancel) {
                        tvCleanCache.setText("0.0 KB");
                        DataCleanManager.clearAllCache(MyApp.getInstance());
                    }
                }
            });
            myDialog1.show();
        } else if (id == R.id.tv_update) {
            Beta.checkUpgrade();
        } else if (id == R.id.tv_reback) {
            startActivity(UserFeedBackActivity.class);
        } else if (id == R.id.tv_about) {
            Intent intent = new Intent(UserSettingActivity.this, BrowserActivity.class);
            intent.putExtra("url", RetrofitUtils.BASE_URL + "/StuShop/public/index.php/index/Index/about");
            startActivity(intent);
        } else if (id == R.id.tv_login) {
            showMsgDialog("退出登录", "是否退出登录？退出后将清除所有信息", new OnItemClickListener() {
                @Override
                public void onConfirm(MyDialog dialog) {
                    Intent intent = new Intent("com.xuyijie.ebuyshop.loginout");
                    sendBroadcast(intent);
                }
            });

        }
    }
}
