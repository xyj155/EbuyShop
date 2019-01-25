package com.example.module_login.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.util.RouterUtil;
import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.UserContract;
import com.example.module_login.presenter.UserPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@Route(path = RouterUtil.MSGCODE)
public class TelPhoneRegisterVerifyActivity extends BaseActivity<UserContract.View, UserPresenter> {


    @BindView(R2.id.tv_register)
    TextView tvRegister;
    @BindView(R2.id.tv_send_msg)
    TextView tvSendMsg;
    @BindView(R2.id.et_code)
    EditText etCode;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserPresenter getPresenter() {
        return null;//
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_tel_phone_register_verify;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        SMSSDK.registerEventHandler(eventHandler);
        initToolBar().setToolBarTitle("验证");
    }

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
                            startActivity(RegisterActivity.class);
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();

                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.i(TAG, "handleMessage: 验证成功");
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            Toast.makeText(TelPhoneRegisterVerifyActivity.this, "发送验证码失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R2.id.tb_common, R2.id.tv_register, R2.id.tv_send_msg})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_register) {
            SMSSDK.submitVerificationCode("86", getIntent().getStringExtra("telphone"), etCode.getText().toString());
        } else if (id == R.id.tv_send_msg) {
            SMSSDK.getVerificationCode("86", getIntent().getStringExtra("telphone"));
        }
    }
}
