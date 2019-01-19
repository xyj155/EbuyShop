package com.example.commonlib.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.comonactivity.GoodsPaymentActivity;
import com.example.commonlib.contract.GoodsStyleContract;
import com.example.commonlib.gson.GoodsStyleGson;
import com.example.commonlib.presenter.GoodsStylePresenter;
import com.example.commonlib.util.SharePreferenceUtil;

import java.util.List;

public class ShopChooseDialog extends Dialog implements GoodsStyleContract.View {
    ImageView ivAvatar;
    ImageView ivClose;
    MoneyView tvPrice;
    TextView tvChoose;
    TextView tvSubmit;
    RecyclerView ryStyleList;
    //在构造方法里提前加载了样式
    private Context context;//上下文
    private String goodsId;
    private GoodsStylePresenter goodsStylePresenter = new GoodsStylePresenter(this);
    private GoodsStyleAdapter goodsStyleAdapter = new GoodsStyleAdapter(null);
    private boolean isBuy;
    private Dialog progressDialog;

    public ShopChooseDialog(Context context, String goodsId, boolean isBuy) {
        super(context, R.style.BottomDialogStyle);//加载dialog的样式
        this.context = context;
        this.goodsId = goodsId;
        this.isBuy = isBuy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        setContentView(R.layout.abc_goods_detail_choose_list_layout);
        ivAvatar = findViewById(R.id.iv_avatar);
        tvPrice = findViewById(R.id.tv_price);
        tvPrice.setMoneyText("0.00");
        ivClose = findViewById(R.id.iv_close);
        tvSubmit = findViewById(R.id.tv_submit);
        tvChoose = findViewById(R.id.tv_choose);
        ryStyleList = findViewById(R.id.ry_style_list);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        //遍历控件id添加点击注册
        goodsStylePresenter.queryGoodsStyle(((Activity) context).getIntent().getStringExtra("goodsId"));
        ryStyleList = findViewById(R.id.ry_style_list);
        ryStyleList.setLayoutManager(new LinearLayoutManager(context));
        ryStyleList.setAdapter(goodsStyleAdapter);
        View inflate = View.inflate(context, R.layout.common_empty, null);
        TextView viewById = inflate.findViewById(R.id.tv_empty);
        viewById.setText("这个商品还没有存货哦！");
        goodsStyleAdapter.setEmptyView(inflate);
        goodsStyleAdapter.openLoadAnimation();
        goodsStyleAdapter.setOnItemClickListener(new onItemClickListenerByGId() {
            @Override
            public void onClickListener(int position, String price, String imageUrl, String styleName) {
                RoundedCorners roundedCorners = new RoundedCorners(10);
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
                Glide.with(context).asBitmap().load(imageUrl).apply(options).into(ivAvatar);
                tvPrice.setMoneyText(price);
                tvChoose.setText("已选择：" + styleName);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goodsStyleAdapter.notifyDataSetChanged();
                    }
                }, 100);

            }
        });
        goodsStyleAdapter.isFirstOnly(false);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBuy) {
                    goodsStylePresenter.addGoodsInShopCarById((String) SharePreferenceUtil.getUser("uid", "String"), chooseGoodsId, "1");
                } else {
                    context.startActivity(new Intent(context, GoodsPaymentActivity.class));
                }

            }
        });
    }

    private static final String TAG = "ShopChooseDialog";
    private String chooseGoodsId;

    @Override
    public void loadGoodsStyle(List<GoodsStyleGson> goodsGson) {
        Log.i(TAG, "loadGoodsStyle: " + goodsGson.size());
        goodsStyleAdapter.replaceData(goodsGson);
        if (!(goodsGson.size() > 0)) {
            tvChoose.setText("该商品没有存货了！");
            tvPrice.setMoneyText("0.00");
            Glide.with(context).asBitmap().load(R.mipmap.ic_empty_style).into(ivAvatar);
        } else {
            tvChoose.setText("已选择：" + goodsGson.get(0).getGoodsName());
            tvPrice.setMoneyText(goodsGson.get(0).getGoodsPrice());
            Log.i(TAG, "loadGoodsStyle: " + goodsGson.get(0).getGoodsPrice());
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            Glide.with(context).asBitmap().load(goodsGson.get(0).getGoodsPicUrl()).apply(options).into(ivAvatar);
        }

    }

    @Override
    public void addGoodsInShopCar(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        progressDialog = new Dialog(context, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.base_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private class GoodsStyleAdapter extends BaseQuickAdapter<GoodsStyleGson, BaseViewHolder> {
        private int index = 0;//标记当前选择的选项
        private onItemClickListenerByGId onItemClickListener;

        public GoodsStyleAdapter(@Nullable List<GoodsStyleGson> data) {
            super(R.layout.dialog_goods_style_item, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final GoodsStyleGson item) {
            helper.setText(R.id.tv_style_name, item.getGoodsName())
                    .setOnCheckedChangeListener(R.id.tv_style_name, new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                index = helper.getPosition();
                                chooseGoodsId = String.valueOf(item.getId());
                                onItemClickListener.onClickListener(helper.getPosition(), item.getGoodsPrice(), item.getGoodsPicUrl(), item.getGoodsName());
                            }
                        }
                    });
            if (index == helper.getPosition()) {
                helper.setChecked(R.id.tv_style_name, true);
            } else {
                helper.setChecked(R.id.tv_style_name, false);
            }

        }

        public void setOnItemClickListener(onItemClickListenerByGId onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }

    private interface onItemClickListenerByGId {
        void onClickListener(int position, String price, String imageUrl, String styleName);
    }

}
