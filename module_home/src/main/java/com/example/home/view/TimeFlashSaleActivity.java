package com.example.home.view;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commonlib.base.BaseActivity;
import com.example.home.contract.GoodsShareContract;
import com.example.home.presenter.GoodsSharePresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeFlashSaleActivity extends BaseActivity<GoodsShareContract.View, GoodsSharePresenter> {


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

    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    public GoodsSharePresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_time_flash_sale;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        String txt = "10：00" + "\n" + "正在开始";
        Spannable spannable = new SpannableString (txt);
        AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(55);
        AbsoluteSizeSpan span2 = new AbsoluteSizeSpan(32);
        spannable.setSpan(span1, 0, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(span2, 6, txt.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        rbTime1.setText(spannable);
        rbTime2.setText(spannable);
        rbTime3.setText(spannable);
        rbTime4.setText(spannable);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
