package com.example.commonlib.util;

import android.content.Context;

import com.payelves.sdk.EPay;
import com.payelves.sdk.enums.EPayResult;
import com.payelves.sdk.listener.PayResultListener;

public class PaymentUtil {


    public static void paymentByGoods(Context context, String goodsName, String goodsDescribe, int amount, final PaymentInterface paymentInterface) {
        EPay.getInstance(context).pay(goodsName, goodsDescribe, amount,
                "", "", "https://zhidao.baidu.com/question/1495498822853007219.html", new PayResultListener() {
                    @Override
                    public void onFinish(Context context, Long payId, String orderId, String payUserId,
                                         EPayResult payResult, int payType, Integer amount) {
                        EPay.getInstance(context).closePayView();//关闭快捷支付页面
                        if (payResult.getCode() == EPayResult.SUCCESS_CODE.getCode()) {
                            paymentInterface.paySuccess();
                        } else if (payResult.getCode() == EPayResult.FAIL_CODE.getCode()) {
                            paymentInterface.payFailed();
                        }
                    }
                });
    }

}
