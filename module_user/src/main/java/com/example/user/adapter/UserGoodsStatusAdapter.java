package com.example.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.MyApp;
import com.example.commonlib.commonactivity.GoodsPaymentActivity;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.util.GlideUtil;
import com.example.user.contract.UserOrderDeleteContract;
import com.google.gson.Gson;
import com.xuyijie.user.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserGoodsStatusAdapter extends BaseQuickAdapter<List<UserOrderStatusGson>, BaseViewHolder> implements UserOrderDeleteContract.View {

    private boolean isBind;
    private Context context;

    private OnReceiveListener onReceiveListener;

    public UserGoodsStatusAdapter(@Nullable List<List<UserOrderStatusGson>> data, Context context) {
        super(R.layout.abc_shopcar_user_order_status_item, data);
        this.context = context;
    }

    public void setOnClickListener(UserGoodsStatusAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private onClickListener onClickListener;

    @Override
    protected void convert(final BaseViewHolder helper, final List<UserOrderStatusGson> item) {
        Log.i(TAG, "convert: " + item.get(0).getOrderNum());

        final List<String> goodsList = new ArrayList<>();
        helper.setText(R.id.tv_order_num, "订单编号：" + item.get(0).getOrderNum())
                .setText(R.id.tv_status, item.get(0).getStatus().equals("1") ? "待付款" : item.get(0).getStatus().equals("2") ? "待发货" : item.get(0).getStatus().equals("3") ? "待收货" : item.get(0).getStatus().equals("4") ? "待评价" : "");
        RecyclerView view = helper.getView(R.id.ry_user_status);
        final UserGoodsStatusInnerAdapter userGoodsStatusInnerAdapter = new UserGoodsStatusInnerAdapter(item);
        view.setLayoutManager(new LinearLayoutManager(MyApp.getInstance()));
        view.setAdapter(userGoodsStatusInnerAdapter);
        double money = 0.00;
        int count = 0;
        String status = item.get(0).getStatus();
        for (int i = 0; i < item.size(); i++) {
            money += Double.valueOf(item.get(i).getStylePrice()) * item.get(i).getCount();
            count += item.get(i).getCount();
            goodsList.add(String.valueOf(item.get(i).getGid()));
        }
        if (status.equals("2")) {
            helper.setVisible(R.id.tv_pay, false)
                    .setVisible(R.id.tv_cancel, false)
                    .setVisible(R.id.tv_evaluate, false)
                    .setVisible(R.id.tv_receive, false)
                    .setVisible(R.id.tv_deliver, false);
            View view1 = helper.getView(R.id.rl_order);
            view1.setVisibility(View.GONE);
            if (item.get(0).getCouponReduce() != null) {
                money = money - Double.valueOf(item.get(0).getCouponReduce());
            }
            money = money + Double.valueOf(item.get(0).getExpressPrice());
        } else if (status.equals("3")) {
            helper.setVisible(R.id.tv_receive, true)
                    .setVisible(R.id.tv_pay, false)
                    .setVisible(R.id.tv_evaluate, false)
                    .setVisible(R.id.tv_deliver, true)
                    .setVisible(R.id.tv_cancel, false);
            if (item.get(0).getCouponReduce() != null) {
                money = money - Double.valueOf(item.get(0).getCouponReduce());
            }
            money = money + Double.valueOf(item.get(0).getExpressPrice());
        } else if (status.equals("4")) {
            helper.setVisible(R.id.tv_pay, false)
                    .setVisible(R.id.tv_deliver, false)
                    .setVisible(R.id.tv_receive, false)
                    .setVisible(R.id.tv_evaluate, true)
                    .setVisible(R.id.tv_cancel, false);
            if (item.get(0).getCouponReduce() != null) {
                money = money - Double.valueOf(item.get(0).getCouponReduce());
            }
            money = money + Double.valueOf(item.get(0).getExpressPrice());
        }

        isBind = true;
        helper.setOnClickListener(R.id.tv_receive, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onReceiveListener.onReceive(item.get(0).getOrderNum());
            }
        });
        helper.setText(R.id.tv_money, "合计：￥" + String.valueOf(new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_DOWN)));
        helper.setText(R.id.tv_count, "共" + String.valueOf(count) + "件商品")
                .setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClickListener(item.get(0).getOrderNum());
                    }
                })
                .setOnClickListener(R.id.tv_pay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, GoodsPaymentActivity.class);
                        Gson gson = new Gson();
                        HashSet<String> strings = new HashSet<>(goodsList);
                        goodsList.clear();
                        goodsList.addAll(strings);//去重
                        String gsonList = gson.toJson(goodsList);
                        intent.putExtra("goodsArray", gsonList);
                        intent.putExtra("orderNum", item.get(0).getOrderNum());
                        context.startActivity(intent);
                    }
                });
        isBind = false;
    }

    public interface onClickListener {
        void onClickListener(String orderNum);
    }


    public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
        this.onReceiveListener = onReceiveListener;
    }

    public interface OnReceiveListener {
        void onReceive(String orderNum);
    }

    @Override
    public void deleteSuccess(boolean isDelete) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void hideDialog() {

    }

    private class UserGoodsStatusInnerAdapter extends BaseQuickAdapter<UserOrderStatusGson, BaseViewHolder> {

        public UserGoodsStatusInnerAdapter(@Nullable List<UserOrderStatusGson> data) {
            super(R.layout.abc_shopcar_user_order_status_inner_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, UserOrderStatusGson item) {
            helper.setText(R.id.tv_goods_name, item.getGoodsName())
                    .setText(R.id.tv_time, item.getStyleName())
                    .setText(R.id.tv_price, "￥" + new BigDecimal(Double.valueOf(item.getStylePrice())).setScale(2, BigDecimal.ROUND_HALF_DOWN) + "")
                    .setText(R.id.tv_count, "x " + item.getCount());
            GlideUtil.loadRoundCornerAvatarImage(item.getGoodsPicUrl(), (ImageView) helper.getView(R.id.iv_cover), 8);
        }
    }
}
