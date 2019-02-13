package com.example.user.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.MyApp;
import com.example.commonlib.commonactivity.GoodsPaymentActivity;
import com.example.commonlib.gson.ExpressTraceGson;
import com.example.commonlib.gson.UserOrderStatusGson;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.view.expressTrace.CallPhoneDialog;
import com.example.commonlib.view.expressTrace.LogisticsData;
import com.example.commonlib.view.expressTrace.LogisticsInformationView;
import com.example.user.contract.ExpressTraceContract;
import com.example.user.contract.UserOrderDeleteContract;
import com.example.user.presenter.ExpressTracePresenter;
import com.example.user.view.ToBeEvaluatedActivity;
import com.google.gson.Gson;
import com.xuyijie.user.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserGoodsStatusAdapter extends BaseQuickAdapter<List<UserOrderStatusGson>, BaseViewHolder> implements UserOrderDeleteContract.View, ExpressTraceContract.View {

    private boolean isBind;
    private Context context;

    private OnReceiveListener onReceiveListener;
    CallPhoneDialog callPhoneDialog;

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
        Log.i(TAG, "convert: "+item.toString());
        if (item.size()>0&&item.get(0)!=null){
            final List<String> goodsList = new ArrayList<>();
            helper.setText(R.id.tv_order_num, "订单编号：" + item.get(0).getOrderNum())
                    .setText(R.id.tv_status, item.get(0).getStatus().equals("1") ? "待付款" : item.get(0).getStatus().equals("2") ? "待发货" : item.get(0).getStatus().equals("3") ? "待收货" : item.get(0).getStatus().equals("4") ? "待评价" : item.get(0).getStatus().equals("5") ? "订单完成" : "");
            RecyclerView view = helper.getView(R.id.ry_user_status);
            final UserGoodsStatusInnerAdapter userGoodsStatusInnerAdapter = new UserGoodsStatusInnerAdapter(item, item.get(0).getStatus().equals("4"));
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
                        .setVisible(R.id.tv_receive, false)
                        .setVisible(R.id.tv_deliver, false);
                View view1 = helper.getView(R.id.rl_order);
                view1.setVisibility(View.GONE);
                if (item.get(0).getCouponReduce() != null) {
                    money = money - Double.valueOf(item.get(0).getCouponReduce());
                }
                money = money + Double.valueOf(item.get(0).getExpressPrice());
                Log.i(TAG, "convert: "+item.get(0).getOrderNum());
            } else if (status.equals("3")) {
                helper.setVisible(R.id.tv_receive, true)
                        .setVisible(R.id.tv_pay, false)
                        .setVisible(R.id.tv_deliver, true)
                        .setVisible(R.id.tv_cancel, false);
                if (item.get(0).getCouponReduce() != null) {
                    money = money - Double.valueOf(item.get(0).getCouponReduce());
                }
                money = money + Double.valueOf(item.get(0).getExpressPrice());
            } else if (status.equals("4")) {
                View view1 = helper.getView(R.id.rl_order);
                view1.setVisibility(View.GONE);
                helper.setVisible(R.id.tv_pay, false)
                        .setVisible(R.id.tv_deliver, false)
                        .setVisible(R.id.tv_receive, false)
                        .setVisible(R.id.tv_cancel, false);
                if (item.get(0).getCouponReduce() != null) {
                    money = money - Double.valueOf(item.get(0).getCouponReduce());
                }
                money = money + Double.valueOf(item.get(0).getExpressPrice());
            } else if (status.equals("5")) {
                View view1 = helper.getView(R.id.rl_order);
                view1.setVisibility(View.GONE);
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
            final ExpressTracePresenter expressTracePresenter = new ExpressTracePresenter(this);
            helper.setOnClickListener(R.id.tv_deliver, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: getExpressNum" + item.get(0).toString());
                    expressTracePresenter.queryExpressByNum(item.get(0).getExpressNum());
                }
            });
        }

    }

    private CallPhoneDialog dialogCreateCall(String phoneNumber) {
        if (callPhoneDialog == null) {
            callPhoneDialog = new CallPhoneDialog(context, phoneNumber);
        } else {
            callPhoneDialog.setPhoneNumber(phoneNumber);
        }
        return callPhoneDialog;
    }

    @Override
    public void loadTrace(List<ExpressTraceGson> expressTraceGsons) {
        List<LogisticsData> logisticsDataList = new ArrayList<>();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.dialog_goods_express_layout);
        LogisticsInformationView logistics_InformationView = (LogisticsInformationView) bottomSheetDialog.findViewById(R.id.logistics_InformationView);
        for (int i = expressTraceGsons.size() - 1; i > 0; i--) {
            logisticsDataList.add(new LogisticsData().setTime(expressTraceGsons.get(i).getAcceptTime()).setContext(expressTraceGsons.get(i).getAcceptStation()));
        }
        logistics_InformationView.setLogisticsDataList(logisticsDataList);
        bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        bottomSheetDialog.show();
        logistics_InformationView.setOnPhoneClickListener(new LogisticsInformationView.OnPhoneClickListener() {
            @Override
            public void onPhoneClick(String phoneNumber) {
                dialogCreateCall(phoneNumber).show();
            }
        });
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

    private Dialog progressDialog;

    @Override
    public void showDialog(String msg) {
        progressDialog = new Dialog(context, com.example.commonlib.R.style.progress_dialog);
        progressDialog.setContentView(com.example.commonlib.R.layout.base_dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private class UserGoodsStatusInnerAdapter extends BaseQuickAdapter<UserOrderStatusGson, BaseViewHolder> {
        private boolean status;

        public UserGoodsStatusInnerAdapter(@Nullable List<UserOrderStatusGson> data, boolean status) {
            super(R.layout.abc_shopcar_user_order_status_inner_item, data);
            this.status = status;
        }

        @Override
        protected void convert(BaseViewHolder helper, final UserOrderStatusGson item) {
            if (!status) {
                helper.setVisible(R.id.tv_evaluate, false);
            } else {
                helper.setVisible(R.id.tv_evaluate, true)
                        .setOnClickListener(R.id.tv_evaluate, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ToBeEvaluatedActivity.class);
                                intent.putExtra("goodsId", String.valueOf(item.getGid()));
                                intent.putExtra("orderNum", String.valueOf(item.getOrderNum()));
                                context.startActivity(intent);
                            }
                        });
            }
            helper.setText(R.id.tv_goods_name, item.getGoodsName())
                    .setText(R.id.tv_time, item.getStyleName())
                    .setText(R.id.tv_price, "￥" + new BigDecimal(Double.valueOf(item.getStylePrice())).setScale(2, BigDecimal.ROUND_HALF_DOWN) + "")
                    .setText(R.id.tv_count, "x " + item.getCount())
            ;
            GlideUtil.loadRoundCornerAvatarImage(item.getGoodsPicUrl(), (ImageView) helper.getView(R.id.iv_cover), 8);
        }
    }
}
