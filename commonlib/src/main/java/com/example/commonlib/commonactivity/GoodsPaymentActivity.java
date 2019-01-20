package com.example.commonlib.commonactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
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
import com.example.commonlib.gson.OrderDetailGson;
import com.example.commonlib.presenter.OrderDetailPresenter;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.view.ExpressChooseDialog;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = RouterUtil.PAYMENT_PAGE)
public class GoodsPaymentActivity extends BaseActivity<OrderDetailContract.View, OrderDetailPresenter> implements OrderDetailContract.View {

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
    @BindView(R2.id.tv_deliver_way)
    TextView tvDeliverWay;
    @BindView(R2.id.tv_post)
    TextView tvPost;
    @BindView(R2.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R2.id.tv_count)
    TextView tvCount;
    @BindView(R2.id.tv_money)
    TextView tvMoney;

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
        initToolBar().setToolBarTitle("订单详情");
        ryGoods.setLayoutManager(new LinearLayoutManager(GoodsPaymentActivity.this));
        mPresenter.confirmationOrderByUserId("1", getIntent().getStringExtra("goodsArray"));
        ryGoods.setAdapter(orderDetailGoodsAdapter);
        ryGoods.setNestedScrollingEnabled(false);
        expressChooseDialog = new ExpressChooseDialog(this);
    }

    @Override
    public void initData() {
        Log.i(TAG, "initData: " + getIntent().getStringExtra("goodsArray"));
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog(msg);
    }

    @Override
    public void hideDialog() {
        hideDlalog();
    }

    @Override
    public void loadOrderDetil(OrderDetailGson orderDetailGson) {
        orderDetailGoodsAdapter.replaceData(orderDetailGson.getGoods());
        if (orderDetailGson.getUserAddress() == null) {
            rlEmptyAddress.setVisibility(View.VISIBLE);
            flAddress.setVisibility(View.GONE);
        } else {
            flAddress.setVisibility(View.VISIBLE);
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

        for (OrderDetailGson.GoodsBean goodsBean : orderDetailGson.getGoods()) {
            money += Double.valueOf(goodsBean.getGoodsPrice()) * goodsBean.getGoodsCount();
            count += goodsBean.getGoodsCount();
        }
        tvCount.setText("共 " + count + " 件商品  小计：");
        BigDecimal bigDecimal = new BigDecimal(money);
        tvMoney.setText("￥" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        expressChooseDialog.setOnItemClickListener(new ExpressChooseDialog.onItemClickListener() {
            @Override
            public void onClickListener(String price, String expressName) {
                Log.i(TAG, "onClickListener: ");
                tvPost.setText("配送方式       " + expressName + "     ￥" + price);
                expressChooseDialog.dismiss();
            }
        });
    }

    private int count = 0;
    private double money = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R2.id.rl_empty_address, R2.id.fl_address, R2.id.tv_post, R2.id.tv_pay_type})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.rl_empty_address) {
            startActivityForResult(new Intent(GoodsPaymentActivity.this, UserReceivingAddressActivity.class), 0x1);
        } else if (id == R.id.fl_address) {
            startActivityForResult(new Intent(GoodsPaymentActivity.this, UserReceivingAddressActivity.class), 0x1);
        } else if (id == R.id.tv_post) {
            expressChooseDialog.show();
        } else if (id == R.id.tv_pay_type) {
            ARouter.getInstance().build(RouterUtil.USERCOUPON).withDouble("money",money).navigation(GoodsPaymentActivity.this, 0x11);
        }
    }

    private ExpressChooseDialog expressChooseDialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 0x1) {
                Log.i(TAG, "onActivityResult: " + data.getStringExtra("username"));
                flAddress.setVisibility(View.VISIBLE);
                rlEmptyAddress.setVisibility(View.GONE);
                tvUsername.setText("收件人：" + data.getStringExtra("username"));
                tvUserTel.setText(data.getStringExtra("tel"));
                tvAddress.setText("收货地址：" + data.getStringExtra("local") + data.getStringExtra("detail"));
            } else if (requestCode == 0x11) {
                String endIndex = data.getStringExtra("endIndex");
                String startIndex = data.getStringExtra("startIndex");
                tvPayType.setText("优惠方式        满 " + startIndex + " 元减 " + endIndex + " 元");
            }
        }

    }
}
