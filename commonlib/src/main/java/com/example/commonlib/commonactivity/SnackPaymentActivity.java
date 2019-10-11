package com.example.commonlib.commonactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.SnackOrderContract;
import com.example.commonlib.gson.SnackOrderDetailGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.http.WebUrl;
import com.example.commonlib.presenter.SnackOrderPresenter;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.MemberConfig;
import com.example.commonlib.util.PaymentInterface;
import com.example.commonlib.util.PaymentUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.toast.ToastUtils;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SnackPaymentActivity extends BaseActivity<SnackOrderContract.View, SnackOrderPresenter> implements SnackOrderContract.View {


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
    @BindView(R2.id.rl_empty_address)
    RelativeLayout rlEmptyAddress;
    @BindView(R2.id.ry_goods)
    RecyclerView ryGoods;
    @BindView(R2.id.tv_post)
    TextView tvPost;
    @BindView(R2.id.et_message)
    EditText etMessage;
    @BindView(R2.id.tv_count)
    TextView tvCount;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tv_pay)
    TextView tvPay;
    @BindView(R2.id.tv_money_submit)
    TextView tvMoneySubmit;
    @BindView(R2.id.tv_post_free)
    TextView tvPostFree;
    @BindView(R2.id.tv_vip)
    TextView tvVip;
    private SnackGoodsListAdapter snackGoodsListAdapter;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public SnackOrderPresenter getPresenter() {
        return new SnackOrderPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_snack_payment;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("确认订单");
        mPresenter.querySnackFoodsOrder(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), getIntent().getStringExtra("orderNum"));
        snackGoodsListAdapter = new SnackGoodsListAdapter(null);
        ryGoods.setLayoutManager(new LinearLayoutManager(SnackPaymentActivity.this));
        ryGoods.setNestedScrollingEnabled(false);
        ryGoods.setHasFixedSize(true);
        ryGoods.setAdapter(snackGoodsListAdapter);
    }

    @Override
    public void initData() {

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
        mhideDialog();
    }

    private int count = 0;
    private double money = 0.00;


    @Override
    public void loadUserSnackOrderList(SnackOrderDetailGson snackShopCarGsonList) {
        snackGoodsListAdapter.replaceData(snackShopCarGsonList.getSnackList());
        if (snackShopCarGsonList.getUserAddress() == null) {
            startActivityForResult(new Intent(SnackPaymentActivity.this, UserReceivingAddressActivity.class), 0x1);
            ToastUtils.show("请选择收货地址");
        } else {
            addressId = String.valueOf(snackShopCarGsonList.getUserAddress().getId());
        }

        if (snackShopCarGsonList.getUserAddress() == null) {
            rlEmptyAddress.setVisibility(View.VISIBLE);
            flAddress.setVisibility(View.GONE);
        } else {
            flAddress.setVisibility(View.VISIBLE);
            tvUsername.setText("收件人：" + snackShopCarGsonList.getUserAddress().getSaveName());
            tvAddress.setText("收货地址：" + snackShopCarGsonList.getUserAddress().getSaveLocal() + snackShopCarGsonList.getUserAddress().getSaveAddressDetail());
            tvUserTel.setText("联系方式：" + snackShopCarGsonList.getUserAddress().getSaveTel());
            rlEmptyAddress.setVisibility(View.GONE);
        }
        for (SnackOrderDetailGson.SnackListBean goodsBean : snackShopCarGsonList.getSnackList()) {
            money += Double.valueOf(goodsBean.getFoodsPrice()) * goodsBean.getCount();
            count += goodsBean.getCount();
        }
        tvCount.setText("共 " + count + " 件商品  小计：");
        int user = Integer.valueOf(String.valueOf(SharePreferenceUtil.getUser("member", "String")));
        if (user == 1) {
            tvVip.setVisibility(View.VISIBLE);
                money = money * MemberConfig.vipRank_1;
            tvVip.setText("(会员1 99折)");
        } else if (user == 2) {
            tvVip.setVisibility(View.VISIBLE);
            money = money * MemberConfig.vipRank_2;
            tvVip.setText("(会员2 97折)");
        } else if (user == 3) {
            tvVip.setVisibility(View.VISIBLE);
            tvVip.setText("(会员3 95折)");
            money = money * MemberConfig.vipRank_3;
        }
        if (money >= 108) {
            tvPost.setText("配送方式       满 108 包邮");
            BigDecimal bigDecimal = new BigDecimal(money);
            tvMoney.setText("￥" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN));
            tvMoneySubmit.setText("￥" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN));
            tvPostFree.setText("(包邮)");
        } else {
            tvPost.setText("配送方式       随机快递（快递15元）");
            BigDecimal bigDecimal = new BigDecimal(money);
            tvMoney.setText("￥" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN));
            money = money + 15;
            tvPostFree.setText("(内含15邮费)");
            BigDecimal bigDecimal1 = new BigDecimal(money);
            tvMoneySubmit.setText("￥" + bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }

    }

    @Override
    public void submitUserSnackOrderByUserId(boolean success) {
        if (success) {
            Intent intent = new Intent(SnackPaymentActivity.this, BrowserActivity.class);
            intent.putExtra("url", RetrofitUtils.BASE_URL + WebUrl.paySuccess);
            startActivity(intent);

        } else {
            Intent intent = new Intent(SnackPaymentActivity.this, BrowserActivity.class);
            intent.putExtra("url", RetrofitUtils.BASE_URL + WebUrl.submitFailed);
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R2.id.fl_address, R2.id.rl_empty_address, R2.id.tv_pay})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.fl_address) {
            startActivityForResult(new Intent(SnackPaymentActivity.this, UserReceivingAddressActivity.class), 0x1);
        } else if (i == R.id.rl_empty_address) {
            startActivityForResult(new Intent(SnackPaymentActivity.this, UserReceivingAddressActivity.class), 0x1);
        } else if (i == R.id.tv_pay) {
            Log.i(TAG, "onViewClicked: " + addressId);
            if (addressId == null) {
                ToastUtils.show("你还没有选择地址哦！");
            } else {
                ToastUtils.show("此APP仅供学习参考，嘻嘻，你提交订单成功");
//                PaymentUtil.paymentByGoods("商学院自营商品", "订单编号：" + getIntent().getStringExtra("orderNum"), (int) (money*100), new PaymentInterface() {
//                    @Override
//                    public void paySuccess() {
                        mPresenter.submitUserSnackOrderByUserId(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), getIntent().getStringExtra("orderNum"), String.valueOf(SharePreferenceUtil.getUser("userToken", "String")), etMessage.getText().toString(), addressId);
//                    }
//
//                    @Override
//                    public void payFailed() {
//                        ToastUtils.show("支付失败！");
//                    }
//                });
            }

        }
    }

    private String addressId;

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
            }
        }
    }

    public static class SnackGoodsListAdapter extends BaseQuickAdapter<SnackOrderDetailGson.SnackListBean, BaseViewHolder> {
        public SnackGoodsListAdapter(@Nullable List<SnackOrderDetailGson.SnackListBean> data) {
            super(R.layout.snack_payment_item_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SnackOrderDetailGson.SnackListBean item) {
            helper.setText(R.id.tv_goods_name, item.getFoodName())
                    .setText(R.id.tv_count, "x " + String.valueOf(item.getCount()))
                    .setText(R.id.tv_taste,  String.valueOf(item.getFoodsTaste()))
                    .setText(R.id.tv_money_total, "￥" + String.valueOf(item.getCount() * Double.valueOf(item.getFoodsPrice())));
            GlideUtil.loadRoundCornerAvatarImage(item.getFoodPicture(), (ImageView) helper.getView(R.id.iv_goods_pic), 10);
        }
    }

}
