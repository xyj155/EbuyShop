package com.example.module_login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@Route(path = RouterUtil.MSGCODE)
public class TelPhoneRegisterVerifyActivity extends BaseActivity<UserContract.View, UserPresenter> {


    @BindView(R2.id.tv_register)
    TextView tvRegister;
    @BindView(R2.id.tv_send_msg)
    TextView tvSendMsg;
    @BindView(R2.id.et_code)
    EditText etCode;
    public static TelPhoneRegisterVerifyActivity telPhoneRegisterVerifyActivity;

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
        rxJava();
    }

    private void rxJava() {
        final long count = 120 * 1000 / 1000;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take((int) (count + 1))
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return count - aLong;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvSendMsg.setEnabled(false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        tvSendMsg.setText("点击重新发送验证码");
                        tvSendMsg.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) { //接收到一条就是会操作一次UI
                        String value = String.valueOf(aLong);
                        tvSendMsg.setText(value + "秒后点击重新发送");
                    }
                });
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
                            Toast.makeText(TelPhoneRegisterVerifyActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Intent intent = new Intent(TelPhoneRegisterVerifyActivity.this, RegisterActivity.class);
                            intent.putExtra("telphone", getIntent().getStringExtra("telphone"));
                            startActivity(intent);
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            if (etCode.getText().toString().isEmpty()) {
                                Toast.makeText(TelPhoneRegisterVerifyActivity.this, "请输入验证码！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TelPhoneRegisterVerifyActivity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
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
        telPhoneRegisterVerifyActivity=this;
    }

    @OnClick({R2.id.tb_common, R2.id.tv_register, R2.id.tv_send_msg})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_register) {
            if (etCode.getText().toString().isEmpty()){
                Toast.makeText(this, "验证码不可为空", Toast.LENGTH_SHORT).show();
            }else {
                SMSSDK.submitVerificationCode("86", getIntent().getStringExtra("telphone"), etCode.getText().toString());
            }

        } else if (id == R.id.tv_send_msg) {
            SMSSDK.getVerificationCode("86", getIntent().getStringExtra("telphone"));
        }
    }
}
