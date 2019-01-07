package com.example.module_login;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.presenter.LoginPresent;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.ThirdLoginUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = RouterUtil.LOGIN)
public class LoginActivity extends BaseActivity<HomeContract.View, LoginPresent> {


    @BindView(R2.id.tv_login)
    TextView tvLogin;
    @BindView(R2.id.iv_qq)
    ImageView ivQq;
    @BindView(R2.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R2.id.iv_sina)
    ImageView ivSina;
    @BindView(R2.id.ll_login)
    LinearLayout llLogin;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public LoginPresent getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
//        findViewById(R2.id.tv_login).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                EPay.getInstance(LoginActivity.this).pay("商品", "购物车", 1,
////                        "wAwS4BHkB", "", "", new PayResultListener() {
////                            /**
////                             * @param context
////                             * @param payId   支付精灵支付id
////                             * @param orderId   商户系统订单id
////                             * @param payUserId 商户系统用户ID
////                             * @param payResult
////                             * @param payType   支付类型:1 支付宝，2 微信 3 银联
////                             * @param amount    支付金额
////                             * @see EPayResult#FAIL_CODE
////                             * @see EPayResult#SUCCESS_CODE
////                             * 1支付成功，2支付失败
////                             */
////                            @Override
////                            public void onFinish(Context context, Long payId, String orderId, String payUserId,
////                                                 EPayResult payResult, int payType, Integer amount) {
////                                EPay.getInstance(context).closePayView();//关闭快捷支付页面
////                                if (payResult.getCode() == EPayResult.SUCCESS_CODE.getCode()) {
////                                    //支付成功逻辑处理
////                                    Toast.makeText(LoginActivity.this, payResult.getMsg(), Toast.LENGTH_LONG).show();
////                                } else if (payResult.getCode() == EPayResult.FAIL_CODE.getCode()) {
////                                    //支付失败逻辑处理
////                                    Toast.makeText(LoginActivity.this, payResult.getMsg(), Toast.LENGTH_LONG).show();
////                                }
////                            }
////                        });
//            }
//        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R2.id.tv_login, R2.id.iv_qq, R2.id.iv_wechat, R2.id.iv_sina})
    public void onViewClicked(View view) {
        int id=view.getId();
        if(id==R.id.iv_qq){
            ThirdLoginUtil.thirdLogin(LoginActivity.this, SHARE_MEDIA.QQ);
        }else if (id==R.id.tv_login){
            ARouter.getInstance().build(RouterUtil.HomePage).navigation();
        }else if (id==R.id.iv_wechat){
            ThirdLoginUtil.thirdLogin(LoginActivity.this, SHARE_MEDIA.QZONE);
        }else if (id==R.id.iv_sina){
            ThirdLoginUtil.thirdLogin(LoginActivity.this, SHARE_MEDIA.SINA);
        }
    }
}
