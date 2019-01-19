package com.example.module_login.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.util.ThirdLoginUtil;
import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.UserContract;
import com.example.module_login.presenter.UserPresenter;
import com.qq.e.comm.util.Md5Util;
import com.qq.e.comm.util.StringUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = RouterUtil.LOGIN)
public class LoginActivity extends BaseActivity<UserContract.View, UserPresenter> implements UserContract.View {


    @BindView(R2.id.tv_login)
    TextView tvLogin;
    @BindView(R2.id.tv_register)
    TextView tvRegister;
    @BindView(R2.id.iv_qq)
    ImageView ivQq;
    @BindView(R2.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R2.id.iv_sina)
    ImageView ivSina;
    @BindView(R2.id.ll_login)
    LinearLayout llLogin;
    @BindView(R2.id.et_username)
    EditText etUsername;
    @BindView(R2.id.et_password)
    EditText etPassword;


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserPresenter getPresenter() {
        return new UserPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R2.id.tv_register, R2.id.tv_login, R2.id.iv_qq, R2.id.iv_wechat, R2.id.iv_sina})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_qq) {
            ThirdLoginUtil.thirdLogin(LoginActivity.this, SHARE_MEDIA.QQ);
        } else if (id == R.id.tv_login) {
            if (StringUtil.isEmpty(etUsername.getText().toString()) && StringUtil.isEmpty(etPassword.getText().toString())) {
                Toast.makeText(this, "输入不可为空！", Toast.LENGTH_SHORT).show();
            } else {
                String encode = Md5Util.encode(etPassword.getText().toString());
                Log.i(TAG, "onViewClicked: " + encode);
                mPresenter.userLogin(etUsername.getText().toString(), encode);
            }
        } else if (id == R.id.iv_wechat) {
            ThirdLoginUtil.thirdLogin(LoginActivity.this, SHARE_MEDIA.QZONE);
        } else if (id == R.id.iv_sina) {
            ThirdLoginUtil.thirdLogin(LoginActivity.this, SHARE_MEDIA.SINA);
        } else if (id == R.id.tv_register) {
            startActivityForResult(new Intent(LoginActivity.this, TelPhoneRegisterActivity.class), LOGIN_CODE);
        }
    }

    public static final int LOGIN_CODE = 1;

    @Override
    public void userLogin(UserGson userGson) {
        ARouter.getInstance().build(RouterUtil.HomePage).navigation();
        Map<String, Object> map = new HashMap<>();
        map.put("username", userGson.getUsername());
        map.put("avatar", userGson.getAvatar());
        map.put("telphone", userGson.getTelphone());
        map.put("uid", String.valueOf(userGson.getId()));
        map.put("islogin", true);
        SharePreferenceUtil.saveUser(map);
        finish();
    }

    @Override
    public void showError(String msg) {
        Log.i(TAG, "showError: " + msg);
        Toast.makeText(this, "登陆出错：" + msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showDialog(String msg) {
        createDialog("");
    }

    @Override
    public void hideDialog() {
        hideDlalog();
    }
}
