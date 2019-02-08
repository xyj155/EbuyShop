package com.example.module_login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.UserContract;
import com.example.module_login.presenter.UserPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class TelPhoneRegisterActivity extends BaseActivity<UserContract.View, UserPresenter> implements UserContract.View {

    @BindView(R2.id.tv_having)
    TextView tvHaving;
    private long lastClickTime = 0L;
    private static final int FAST_CLICK_DELAY_TIME = 500;  // 快速点击间隔

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        telPhoneRegisterActivity = this;
    }
//    }/

    EditText etTel;
    TextView tvRegister;
    @BindView(R2.id.iv_close)
    ImageView ivClose;
    public static TelPhoneRegisterActivity telPhoneRegisterActivity;
    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达]
                            ToastUtils.show("发送验证码成功！");
                            Map<String, Object> map = new HashMap<>();
                            map.put("username", etTel.getText().toString());
                            map.put("telphone", etTel.getText().toString());
                            SharePreferenceUtil.saveUser(map);
                            Intent intent = new Intent(TelPhoneRegisterActivity.this, TelPhoneRegisterVerifyActivity.class);
                            intent.putExtra("telphone", etTel.getText().toString());
                            startActivity(intent);
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            ToastUtils.show("发送验证码失败！错误信息"+data.toString());
                        }
                    }
                    return false;
                }
            }).sendMessage(msg);
        }
    };
    @BindView(R2.id.tv_title)
    TextView tvTitle;


    // 使用完EventHandler需注销，否则可能出现内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

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
        return R.layout.activity_tel_phone_register;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("发送验证码");
        etTel = findViewById(R.id.et_tel);
        tvRegister = findViewById(R.id.tv_register);
        SMSSDK.registerEventHandler(eventHandler);
        SMSSDK.setAskPermisionOnReadContact(true);
    }

    @Override
    public void initData() {

    }


    @OnClick({R2.id.tv_register})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_register) {
            mPresenter.queryUserAccount(etTel.getText().toString());
        }
    }

    @Override
    public void userLogin(UserGson userGson) {

    }

    @Override
    public void isHaving(boolean isHaving) {
        if (isHaving) {
            tvHaving.setText("此手机号码已注册！请直接登录");
        } else {
            if (System.currentTimeMillis() - lastClickTime < FAST_CLICK_DELAY_TIME) {
                return;
            }
            lastClickTime = System.currentTimeMillis();
            ToastUtils.show("电话号码："+etTel.getText().toString());
            SMSSDK.getVerificationCode("86", etTel.getText().toString());
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog("");
    }

    @Override
    public void hideDialog() {
        mhideDialog();
    }
}
