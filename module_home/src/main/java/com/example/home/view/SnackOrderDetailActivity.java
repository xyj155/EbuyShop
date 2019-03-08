package com.example.home.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.SnackPaymentActivity;
import com.example.commonlib.gson.SnackOrderDetailGson;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.home.contract.SnackOrderDetailContract;
import com.example.home.presenter.SnackOrderDetailPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnackOrderDetailActivity extends BaseActivity<SnackOrderDetailContract.View, SnackOrderDetailPresenter> implements SnackOrderDetailContract.View {


    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.tv_user_tel)
    TextView tvUserTel;
    @BindView(R2.id.tv_address)
    TextView tvAddress;
    @BindView(R2.id.fl_address)
    FrameLayout flAddress;
    @BindView(R2.id.ry_goods)
    RecyclerView ryGoods;
    @BindView(R2.id.tv_post)
    TextView tvPost;
    @BindView(R2.id.et_message)
    TextView etMessage;
    @BindView(R2.id.tv_count)
    TextView tvCount;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tv_post_trace)
    TextView tvPostTrace;
    LinearLayout ryPostTrace;
    private SnackPaymentActivity.SnackGoodsListAdapter snackGoodsListAdapter;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public SnackOrderDetailPresenter getPresenter() {
        return new SnackOrderDetailPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_snack_order_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("订单详情");
        snackGoodsListAdapter = new SnackPaymentActivity.SnackGoodsListAdapter(null);
        ryGoods.setLayoutManager(new LinearLayoutManager(SnackOrderDetailActivity.this));
        ryGoods.setNestedScrollingEnabled(false);
        ryGoods.setHasFixedSize(true);
        ryGoods.setAdapter(snackGoodsListAdapter);
        ryPostTrace = findViewById(R.id.logistics_InformationView);
    }

    @Override
    public void initData() {
        mPresenter.querySnackOrderDetail(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), getIntent().getStringExtra("orderNum"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    private int count = 0;
    private double money = 0.00;


    @Override
    public void loadSnackGoodsDetail(SnackOrderDetailGson snackOrderDetailGson) {
        snackGoodsListAdapter.replaceData(snackOrderDetailGson.getSnackList());
        tvAddress.setText("收货地址：" + snackOrderDetailGson.getUserAddress().getSaveLocal() + snackOrderDetailGson.getUserAddress().getSaveAddressDetail());
        tvUsername.setText("收件人：" + snackOrderDetailGson.getUserAddress().getSaveName());
        tvUserTel.setText("联系方式：" + snackOrderDetailGson.getUserAddress().getSaveTel());
        for (SnackOrderDetailGson.SnackListBean goodsBean : snackOrderDetailGson.getSnackList()) {
            money += Double.valueOf(goodsBean.getFoodsPrice()) * goodsBean.getCount();
            count += goodsBean.getCount();
        }
        if (snackOrderDetailGson.getSnackList().get(0).getMessage().isEmpty() || snackOrderDetailGson.getSnackList().get(0).getMessage() == null) {
            etMessage.setText("用户无需求");
        } else {
            etMessage.setText(snackOrderDetailGson.getSnackList().get(0).getMessage());
        }
        tvCount.setText("共 " + count + " 件商品  小计：");
        if (money >= 99) {
            tvPost.setText("配送方式       满 108 包邮");
            tvMoney.setText("￥" +String.format("%.2f", money));
        } else {
            tvPost.setText("配送方式       随机快递（快递费：15元）");
            money = money + 15;

            tvMoney.setText("￥" + String.format("%.2f", money));
        }
        Log.i(TAG, "loadSnackGoodsDetail: " + (snackOrderDetailGson.getExpress().size()==0));
        Log.i(TAG, "loadSnackGoodsDetail: " + (snackOrderDetailGson.getExpress()==null));
        Log.i(TAG, "loadSnackGoodsDetail: " + snackOrderDetailGson.getExpress().size());
        if (snackOrderDetailGson.getExpress().size()==0) {
            View v = LayoutInflater.from(this).inflate(R.layout.view_express_order_state_empty, ryPostTrace, false);
            ryPostTrace.addView(v);
        } else {
            for (int i = snackOrderDetailGson.getExpress().size() - 1; i > 0; i--) {
                ryPostTrace.addView(addItem(snackOrderDetailGson.getExpress().get(i).getAcceptStation(),
                        snackOrderDetailGson.getExpress().get(i).getAcceptTime(),
                        i == snackOrderDetailGson.getExpress().size() - 1));
            }
        }

    }

    private View addItem(String title, String time, boolean currentState) {
        View v = LayoutInflater.from(this).inflate(R.layout.view_express_order_state_row, ryPostTrace, false);
        TextView titleTv = (TextView) v.findViewById(R.id.orderState_title);
        TextView contentTv = (TextView) v.findViewById(R.id.orderState_content);
        ImageView icNormal = (ImageView) v.findViewById(R.id.ic_normal);
        ImageView icCurrent = (ImageView) v.findViewById(R.id.ic_current);
        titleTv.setText(title);
        contentTv.setText(time);
        if (currentState) {
            titleTv.setText(title);
            titleTv.setTextAppearance(this, R.style.BoldText);
            titleTv.setTextColor(getResources().getColor(R.color.black));
            icCurrent.setVisibility(View.VISIBLE);
            icNormal.setVisibility(View.GONE);
        } else {
            icCurrent.setVisibility(View.GONE);
            icNormal.setVisibility(View.VISIBLE);
        }
        return v;

    }

    @Override
    public void showError(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void showDialog(String msg) {
        createDialog(msg);
    }

    @Override
    public void hideDialog() {
        mhideDialog();
    }
}
