package com.example.home.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.gson.TimeGoodsGson;
import com.example.home.contract.TimeFlashContract;
import com.example.home.presenter.TimeFlashPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeFlashSaleActivity extends BaseActivity<TimeFlashContract.View, TimeFlashPresenter> implements TimeFlashContract.View {


    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.rb_time1)
    RadioButton rbTime1;
    @BindView(R2.id.rb_time2)
    RadioButton rbTime2;
    @BindView(R2.id.rb_time3)
    RadioButton rbTime3;
    @BindView(R2.id.rb_time4)
    RadioButton rbTime4;
    @BindView(R2.id.ry_time_sell)
    RecyclerView ryTimeSell;
    @BindView(R2.id.rg_time)
    RadioGroup rgTime;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    public TimeFlashPresenter getPresenter() {
        return new TimeFlashPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_time_flash_sale;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rgTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = v.getId();
                if (checkedId == R.id.rb_time1) {
                    mPresenter.queryGoodsTime("1");
                } else if (checkedId == R.id.rb_time2) {
                    mPresenter.queryGoodsTime("2");
                } else if (checkedId == R.id.rb_time3) {
                    mPresenter.queryGoodsTime("3");
                } else if (checkedId == R.id.rb_time4) {
                    mPresenter.queryGoodsTime("4");
                }
            }
        });
    }

    @Override
    public void initData() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        ryTimeSell.setLayoutManager(staggeredGridLayoutManager);
        ryTimeSell.setItemAnimator(new DefaultItemAnimator());
        ryTimeSell.setNestedScrollingEnabled(false);
        ryTimeSell.setFocusable(false);
        purseGoodsAdapter = new TimerGoodsAdapter(null, TimeFlashSaleActivity.this);
        ryTimeSell.setAdapter(purseGoodsAdapter);
        mPresenter.queryGoodsTime("1");
    }

    private TimerGoodsAdapter purseGoodsAdapter;
    private String endTime;

    @Override
    public void queryTimeGoods(TimeGoodsGson goodsGsons) {
        if (purseGoodsAdapter != null) {
            if (isInDate(goodsGsons.getTime().get(0).getStartTime(), goodsGsons.getTime().get(0).getEndTime())) {
                rbTime1.setText(setTimerTitle(goodsGsons.getTime().get(0).getStartTime().substring(5, 10) + "\n" + "正在开始"));
                endTime = goodsGsons.getTime().get(0).getEndTime();
                rbTime1.setChecked(true);
            } else {
                rbTime1.setClickable(false);
                rbTime1.setChecked(false);
                rbTime1.setText(setTimerTitle(goodsGsons.getTime().get(0).getStartTime().substring(5, 10) + "\n" + "等待开始"));
            }
            if (isInDate(goodsGsons.getTime().get(1).getStartTime(), goodsGsons.getTime().get(1).getEndTime())) {
                rbTime2.setText(setTimerTitle(goodsGsons.getTime().get(1).getStartTime().substring(5, 10) + "\n" + "正在开始"));
                endTime = goodsGsons.getTime().get(1).getEndTime();
                rbTime2.setChecked(true);
            } else {
                rbTime2.setClickable(false);
                rbTime2.setChecked(false);
                rbTime2.setText(setTimerTitle(goodsGsons.getTime().get(1).getStartTime().substring(5, 10) + "\n" + "等待开始"));
            }
            if (isInDate(goodsGsons.getTime().get(2).getStartTime(), goodsGsons.getTime().get(2).getEndTime())) {
                rbTime3.setText(setTimerTitle(goodsGsons.getTime().get(2).getStartTime().substring(5, 10) + "\n" + "正在开始"));
                endTime = goodsGsons.getTime().get(2).getEndTime();
                rbTime3.setChecked(true);
            } else {
                rbTime3.setClickable(false);
                rbTime3.setChecked(false);
                rbTime3.setText(setTimerTitle(goodsGsons.getTime().get(2).getStartTime().substring(5, 10) + "\n" + "等待开始"));
            }
            if (isInDate(goodsGsons.getTime().get(3).getStartTime(), goodsGsons.getTime().get(3).getEndTime())) {
                rbTime4.setText(setTimerTitle(goodsGsons.getTime().get(3).getStartTime().substring(5, 10) + "\n" + "正在开始"));
                endTime = goodsGsons.getTime().get(3).getEndTime();
                rbTime4.setChecked(true);
            } else {
                rbTime4.setClickable(false);
                rbTime4.setChecked(false);
                rbTime4.setText(setTimerTitle(goodsGsons.getTime().get(3).getStartTime().substring(5, 10) + "\n" + "等待开始"));
            }
            if (rbTime2.isChecked()) {
                rbTime1.setText(setTimerTitle(goodsGsons.getTime().get(0).getStartTime().substring(5, 10) + "\n" + "活动结束"));
            }
            if (rbTime3.isChecked()) {
                rbTime2.setText(setTimerTitle(goodsGsons.getTime().get(2).getStartTime().substring(5, 10) + "\n" + "活动结束"));
                rbTime1.setText(setTimerTitle(goodsGsons.getTime().get(0).getStartTime().substring(5, 10) + "\n" + "活动结束"));
            }
            purseGoodsAdapter.replaceData(goodsGsons.getGoods());
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog("");
    }

    @Override
    public void hideDialog() {
        mhideDialog();
    }

    private static final String TAG = "TimeFlashSaleActivity";

    public static boolean isInDate(String strDateBegin, String strDateEnd) {
        String substring = strDateBegin.substring(0, 10);
        String substring1 = strDateEnd.substring(0, 10);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        String strDate = sdf.format(date);
        Log.i(TAG, "substring: " + strDate);
        Log.i(TAG, "substring1: " + strDate);
        // 截取当前时间年月日 转成整型
        int tempDate = Integer.parseInt(strDate.split("-")[0] + strDate.split("-")[1] + strDate.split("-")[2]);
        // 截取开始时间年月日 转成整型
        int tempDateBegin = Integer.parseInt(substring.split("-")[0] + substring.split("-")[1] + substring.split("-")[2]);
        // 截取结束时间年月日   转成整型
        int tempDateEnd = Integer.parseInt(substring1.split("-")[0] + substring1.split("-")[1] + substring1.split("-")[2]);
        Log.i(TAG, "isInDate: tempDateBegin" + tempDateBegin);
        Log.i(TAG, "isInDate: tempDateEnd" + tempDateEnd);
        Log.i(TAG, "isInDate: tempDate" + tempDate);
        if ((tempDate >= tempDateBegin && tempDate <= tempDateEnd)) {
            return true;
        } else {
            return false;
        }
    }

    private Spannable setTimerTitle(String txt) {
        Spannable spannable = new SpannableString(txt);
        AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(55);
        AbsoluteSizeSpan span2 = new AbsoluteSizeSpan(32);
        spannable.setSpan(span1, 0, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(span2, 6, txt.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class TimerGoodsAdapter extends BaseQuickAdapter<TimeGoodsGson.GoodsBean, BaseViewHolder> {
        private Context context;

        public TimerGoodsAdapter(@Nullable List<TimeGoodsGson.GoodsBean> data, Context context) {
            super(R.layout.ry_time_goods_item, data);
            this.context = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, final TimeGoodsGson.GoodsBean item) {
            TextView view = helper.getView(R.id.tvTotal);
            view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            view.setText("￥" + item.getPreviousPrice());
            helper.setText(R.id.tvPrice, "￥" + item.getGoodsPrice())
                    .setText(R.id.tvGoodsName, item.getGoodsName())
                    .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent view = new Intent(context, GoodsDetailActivity.class);
                            view.putExtra("goodsId", String.valueOf(item.getId()));
                            view.putExtra("isTimer", true);
                            view.putExtra("time", item.getTime());
                            view.putExtra("endTime", endTime);
                            context.startActivity(view);
                        }
                    });
            ;
            Glide.with(context).asBitmap().load(item.getGoodsPic()).into((ImageView) helper.getView(R.id.ivCover));
        }
    }
}
