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
import android.widget.Toast;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.util.SharePreferenceUtil;
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

public class TelPhoneRegisterActivity extends BaseActivity<UserContract.View, UserPresenter> {


    EditText etTel;
    TextView tvRegister;
    @BindView(R2.id.iv_close)
    ImageView ivClose;

    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
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
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            Toast.makeText(TelPhoneRegisterActivity.this, "发送验证码成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            Toast.makeText(TelPhoneRegisterActivity.this, "发送验证码失败！", Toast.LENGTH_SHORT).show();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
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
        return null;
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
// 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
        SMSSDK.setAskPermisionOnReadContact(true);
// 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”


// 提交验证码，其中的code表示验证码，如“1357”
//        SMSSDK.submitVerificationCode(country, phone, code);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }


    @OnClick({R2.id.tv_register})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_register) {
            SMSSDK.getVerificationCode("86", etTel.getText().toString());
            Intent intent = new Intent(TelPhoneRegisterActivity.this, TelPhoneRegisterVerifyActivity.class);
            intent.putExtra("telphone", etTel.getText().toString());
            startActivity(intent);
            Map<String, Object> map = new HashMap<>();
            map.put("username", etTel.getText().toString());
            SharePreferenceUtil.saveUser(map);
        }
    }
}
