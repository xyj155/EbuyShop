package com.example.commonlib.commonactivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.adapter.OrderDetailGoodsAdapter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.OrderDetailContract;
import com.example.commonlib.contract.UserSubmitOrderContract;
import com.example.commonlib.gson.OrderDetailGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.http.WebUrl;
import com.example.commonlib.presenter.OrderDetailPresenter;
import com.example.commonlib.presenter.UserSubmitOrderPresenter;
import com.example.commonlib.util.MemberConfig;
import com.example.commonlib.util.PaymentInterface;
import com.example.commonlib.util.PaymentUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.CommonDialog;
import com.example.commonlib.view.ExpressChooseDialog;
import com.example.commonlib.view.toast.ToastUtils;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = RouterUtil.PAYMENT_PAGE)
public class GoodsPaymentActivity extends BaseActivity<OrderDetailContract.View, OrderDetailPresenter> implements OrderDetailContract.View, UserSubmitOrderContract.View {

    @BindView(R2.id.ry_goods)
    RecyclerView ryGoods;
    @BindView(R2.id.rl_empty_address)
    RelativeLayout rlEmptyAddress;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.tv_user_tel)
    TextView tvUserTel;
    @BindView(R2.id.tv_address)
    TextView tvAddress;
    @BindView(R2.id.fl_address)
    FrameLayout flAddress;
    @BindView(R2.id.tv_post)
    TextView tvPost;
    @BindView(R2.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R2.id.tv_count)
    TextView tvCount;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tv_cancel)
    TextView tvCancel;
    @BindView(R2.id.tv_pay)
    TextView tvPay;
    @BindView(R2.id.tv_vip)
    TextView tvVip;
    @BindView(R2.id.et_message)
    EditText etMessage;


    private OrderDetailGoodsAdapter orderDetailGoodsAdapter = new OrderDetailGoodsAdapter(null);

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public OrderDetailPresenter getPresenter() {
        return new OrderDetailPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_payment;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        mPresenter.confirmationOrderByUserId((String) SharePreferenceUtil.getUser("uid", "String"), getIntent().getStringExtra("goodsArray"), getIntent().getStringExtra("orderNum"));
        initToolBar().setToolBarTitle("订单详情");
        ryGoods.setLayoutManager(new LinearLayoutManager(GoodsPaymentActivity.this));

        ryGoods.setAdapter(orderDetailGoodsAdapter);
        ryGoods.setNestedScrollingEnabled(false);
//        expressChooseDialog = new CommonDialog(this);
    }


    @Override
    public void initData() {
        Log.i(TAG, "initData: " + getIntent().getStringExtra("goodsArray"));
    }

    @Override
    public void showError(String msg) {

    }

    private CommonDialog expressFreeDialog;

    @Override
    public void showDialog(String msg) {
        createDialog(msg);
    }

    @Override
    public void hideDialog() {
        mhideDialog();
    }

    private String orderNum;
    private List<String> goodsIdList = new ArrayList<>();
    private boolean isAddressFind = false;


    @Override
    public void loadOrderDetail(OrderDetailGson orderDetailGson) {
        Log.i(TAG, "loadOrderDetail: " + orderDetailGson.getGoods().size());
        orderDetailGoodsAdapter.replaceData(orderDetailGson.getGoods());
        if (orderDetailGson.getUserAddress()==null){
            ToastUtils.show("请填写地址");
            startActivityForResult(new Intent(GoodsPaymentActivity.this, UserReceivingAddressActivity.class), 0x1);
        }else {
            addressId = String.valueOf(orderDetailGson.getUserAddress().getId());
        }

        if (orderDetailGson.getUserAddress() == null) {
            rlEmptyAddress.setVisibility(View.VISIBLE);
            flAddress.setVisibility(View.GONE);
        } else {
            flAddress.setVisibility(View.VISIBLE);
            tvUsername.setText("收件人：" + orderDetailGson.getUserAddress().getSaveName());
            tvAddress.setText("收货地址：" + orderDetailGson.getUserAddress().getSaveLocal() + orderDetailGson.getUserAddress().getSaveAddressDetail());
            tvUserTel.setText("联系方式：" + orderDetailGson.getUserAddress().getSaveTel());
            rlEmptyAddress.setVisibility(View.GONE);
        }
        if (orderDetailGson.getUserCoupon().size() == 0) {
            tvPayType.setText("优惠方式       无优惠券");
        } else {
            String s = "优惠方式        你有 " + orderDetailGson.getUserCoupon().size() + " 张优惠券";
            SpannableString spannableString = new SpannableString(s);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 12, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvPayType.setText(spannableString);
        }
        int user = Integer.valueOf(String.valueOf(SharePreferenceUtil.getUser("member", "String")));
        for (OrderDetailGson.GoodsBean goodsBean : orderDetailGson.getGoods()) {
            if (goodsBean.getIsDiscount().equals("1")) {
                if (user == 1) {
                    money += Double.valueOf(goodsBean.getGoodsPrice()) * goodsBean.getGoodsCount() * MemberConfig.vipRank_1;
                } else if (user == 2) {
                    money += Double.valueOf(goodsBean.getGoodsPrice()) * goodsBean.getGoodsCount() * MemberConfig.vipRank_2;
                } else if (user == 3) {
                    money += Double.valueOf(goodsBean.getGoodsPrice()) * goodsBean.getGoodsCount() * MemberConfig.vipRank_3;
                } else {
                    money += Double.valueOf(goodsBean.getGoodsPrice()) * goodsBean.getGoodsCount();
                }
            } else {
                money += Double.valueOf(goodsBean.getGoodsPrice()) * goodsBean.getGoodsCount();
            }

            count += goodsBean.getGoodsCount();
            goodsIdList.add(String.valueOf(goodsBean.getGoodsId()));
        }

        if (user == 1) {
            tvVip.setVisibility(View.VISIBLE);
            tvVip.setText("(会员1 99折)");
        } else if (user == 2) {
            tvVip.setVisibility(View.VISIBLE);
            tvVip.setText("(会员2 97折)");
        } else if (user == 3) {
            tvVip.setVisibility(View.VISIBLE);
            tvVip.setText("(会员3 95折)");
        }

        if (money > 188) {
            tvPost.setText("配送方式       满 188 包邮");
        } else {
            money = money + 12;
            tvPost.setText("配送方式       12 元邮费（快递公司随机）");
        }
        tvCount.setText("共 " + count + " 件商品  小计：");
        BigDecimal bigDecimal = new BigDecimal(money);
        tvMoney.setText("￥" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        Log.i(TAG, "loadOrderDetail: " + money);
        totalMoney = money;
        orderNum = orderDetailGson.getGoods().get(0).getOrderNum();
        expressChooseDialog = builder.cancelTouchout(false)
                .view(R.layout.common_dialog_layout)
                .setMsg("快递费用 ")
                .setContent("用户未购满188，包含12元邮费")
                .addViewOnclick(R.id.tv_submit, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expressChooseDialog.dismiss();
                    }
                }).build();
        expressFreeDialog = builder.cancelTouchout(false)
                .view(R.layout.common_dialog_layout)
                .setMsg("满 188 包邮")
                .setContent("用户购满188即可享受包邮服务")
                .addViewOnclick(R.id.tv_submit, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expressFreeDialog.dismiss();
                    }
                }).build();


    }


    CommonDialog.Builder builder = new CommonDialog.Builder(this);
    private int count = 0;
    private double money = 0.00;


    @OnClick({R2.id.rl_empty_address, R2.id.fl_address, R2.id.tv_post, R2.id.tv_pay_type, R2.id.tv_cancel, R2.id.tv_pay})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.rl_empty_address) {
            startActivityForResult(new Intent(GoodsPaymentActivity.this, UserReceivingAddressActivity.class), 0x1);
        } else if (id == R.id.fl_address) {
            startActivityForResult(new Intent(GoodsPaymentActivity.this, UserReceivingAddressActivity.class), 0x1);
        } else if (id == R.id.tv_post) {
            if (money > 188) {
                if (expressFreeDialog!=null){
                    expressFreeDialog.show();
                }
            } else {
                if (expressChooseDialog!=null){
                    expressChooseDialog.show();
                }
            }
        } else if (id == R.id.tv_pay_type) {
            ARouter.getInstance().build(RouterUtil.USERCOUPON).withDouble("money", money).navigation(GoodsPaymentActivity.this, 0x11);
        } else if (id == R.id.tv_pay) {
            if (addressId == null) {
                ToastUtils.show("你还没有选择地址哦！");
            } else {
                Log.i(TAG, "onViewClicked: xlmgsf ");
                Log.i(TAG, "onViewClicked: " + totalMoney);
                boolean b = money > 188;
                if (b) {
                    Log.i(TAG, "onViewClicked:totalMoney " + (int) (totalMoney * 100));
                    PaymentUtil.paymentByGoods("商学院自营商品", "订单号：" + getIntent().getStringExtra("orderNum"), (int) (totalMoney * 100), new PaymentInterface() {
//                    PaymentUtil.paymentByGoods("商学院自营商品", "订单号：" + getIntent().getStringExtra("orderNum"), 1, new PaymentInterface() {
                        @Override
                        public void paySuccess() {
                            userSubmitOrderPresenter.submitOrderByUserId((String) SharePreferenceUtil.getUser("uid", "String"), addressId, new Gson().toJson(goodsIdList), couponId == null ? "5" : couponId, orderNum, (String) SharePreferenceUtil.getUser("userToken", "String"), etMessage.getText().toString(), "36");
                        }

                        @Override
                        public void payFailed() {
                            ToastUtils.show("支付失败！");
                        }
                    });
                } else {
//                    if (expressName.isEmpty()) {
//                        ToastUtils.show("你还没有选择配送方式！");
//                    } else {
                    PaymentUtil.paymentByGoods("商学院自营商品", "订单号：" + getIntent().getStringExtra("orderNum"), (int) ((totalMoney + 12) * 100), new PaymentInterface() {
//                    PaymentUtil.paymentByGoods("商学院自营商品", "订单号：" + getIntent().getStringExtra("orderNum"), 1, new PaymentInterface() {
                        @Override
                        public void paySuccess() {
                            userSubmitOrderPresenter.submitOrderByUserId((String) SharePreferenceUtil.getUser("uid", "String"), addressId, new Gson().toJson(goodsIdList), "5", orderNum, (String) SharePreferenceUtil.getUser("userToken", "String"), etMessage.getText().toString(), "33");
                        }

                        @Override
                        public void payFailed() {
                            ToastUtils.show("支付失败！");
                        }
                    });
//                    }
                }
            }
        } else if (id == R.id.tv_cancel) {
            finish();
        }
    }

    private UserSubmitOrderPresenter userSubmitOrderPresenter = new UserSubmitOrderPresenter(this);
    private CommonDialog expressChooseDialog;
    private String addressId, couponId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 0x1) {
                flAddress.setVisibility(View.VISIBLE);
                rlEmptyAddress.setVisibility(View.GONE);
                addressId = data.getStringExtra("adId");
                tvUsername.setText("收件人：" + data.getStringExtra("username"));
                tvUserTel.setText("联系方式：" + data.getStringExtra("tel"));
                tvAddress.setText("收货地址：" + data.getStringExtra("local") + data.getStringExtra("detail"));
            } else if (requestCode == 0x11) {
                totalMoney = money;
                String endIndex = data.getStringExtra("endIndex");
                String startIndex = data.getStringExtra("startIndex");
                couponId = data.getStringExtra("couponId");
                tvPayType.setText("优惠方式        满 " + startIndex + " 元减 " + endIndex + " 元");
                totalMoney = money - Double.valueOf(endIndex);
                BigDecimal bigDecimal = new BigDecimal(totalMoney);
                tvMoney.setText("￥" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN));
                Log.i(TAG, "onActivityResult: " + totalMoney);
            }
        }
    }


    private double totalMoney = 0;


    @Override
    public void submitStatus(boolean success) {
        if (success) {
            Intent intent = new Intent(GoodsPaymentActivity.this, BrowserActivity.class);
            intent.putExtra("url", RetrofitUtils.BASE_URL + WebUrl.paySuccess);
            startActivity(intent);
        } else {
            Intent intent = new Intent(GoodsPaymentActivity.this, BrowserActivity.class);
            intent.putExtra("url", RetrofitUtils.BASE_URL + WebUrl.submitFailed);
            startActivity(intent);
        }
        finish();
    }

}
