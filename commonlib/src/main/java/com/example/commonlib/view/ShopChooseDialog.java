package com.example.commonlib.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.commonlib.contract.GoodsStyleContract;
import com.example.commonlib.gson.GoodsStyleGson;
import com.example.commonlib.presenter.GoodsStylePresenter;
import com.example.commonlib.util.SharePreferenceUtil;

import java.util.List;

public class ShopChooseDialog extends Dialog implements GoodsStyleContract.View, View.OnClickListener{
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
    private TextView tvCount;
    private ImageView ivMinum, tvAdd;

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
        ivMinum = findViewById(R.id.iv_minum);
        tvAdd = findViewById(R.id.iv_add);
        ivAvatar = findViewById(R.id.iv_avatar);
        tvPrice = findViewById(R.id.tv_price);
        tvCount = findViewById(R.id.tv_count);
        tvPrice.setMoneyText("0.00");
        ivClose = findViewById(R.id.iv_close);
        tvSubmit = findViewById(R.id.tv_submit);
        tvChoose = findViewById(R.id.tv_choose);
        ryStyleList = findViewById(R.id.ry_style_list);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        goodsStylePresenter.queryGoodsStyle(((Activity) context).getIntent().getStringExtra("goodsId"));
        ryStyleList = findViewById(R.id.ry_style_list);
        ryStyleList.setLayoutManager(new LinearLayoutManager(context));
        ryStyleList.setAdapter(goodsStyleAdapter);
        goodsStyleAdapter.setOnItemClickListener(new onItemClickListenerByGId() {
            @Override
            public void onClickListener(int position, String price, String imageUrl, String styleName) {
                RoundedCorners roundedCorners = new RoundedCorners(10);
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
                Glide.with(context).asBitmap().load(imageUrl).apply(options).into(ivAvatar);
                tvPrice.setMoneyText(price);
                tvChoose.setText("已选择：" + styleName);
                chooseGoodsId = String.valueOf(position);
                Log.i(TAG, "onClickListener: "+chooseGoodsId);
            }
        });
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
                    goodsStylePresenter.addGoodsInShopCarById(
                            (String) SharePreferenceUtil.getUser("uid", "String"),
                            Integer.toString(goodsCount), chooseGoodsId, "1");
                } else {
                    onSubmitOrderListener.onSubmitListener(chooseGoodsId,Integer.toString(goodsCount));
                    dismiss();
                }

            }
        });
        ivMinum.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
    }
    public interface onSubmitOrderListener {
        void onSubmitListener(String goodsId,String count);
    }

    private onSubmitOrderListener onSubmitOrderListener;

    public void setOnSubmitOrderListener(ShopChooseDialog.onSubmitOrderListener onSubmitOrderListener) {
        this.onSubmitOrderListener = onSubmitOrderListener;
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

    int goodsCount = 1;

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_minum) {
            if (goodsCount > 1) {
                goodsCount -= 1;
            }
            tvCount.setText(Integer.toString(goodsCount));
        } else if (id == R.id.iv_add) {
            if (goodsCount < 999) {
                goodsCount += 1;
            }
            tvCount.setText(Integer.toString(goodsCount));
        }
    }


    private class GoodsStyleAdapter extends BaseQuickAdapter<GoodsStyleGson, BaseViewHolder> {
        private int index = 0;//标记当前选择的选项
        private onItemClickListenerByGId onItemClickListener;
        private boolean onBind;

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

                                Log.i(TAG, "onCheckedChanged-------: "+goodsId);
                                onItemClickListener.onClickListener(item.getId(), item.getGoodsPrice(), item.getGoodsPicUrl(), item.getGoodsName());
                                if (!onBind) {
                                    notifyDataSetChanged();
                                }
                            }
                        }
                    });
            onBind = true;
            if (index == helper.getPosition()) {

                helper.setChecked(R.id.tv_style_name, true);
            } else {
                helper.setChecked(R.id.tv_style_name, false);
            }
            onBind = false;
        }

        public void setOnItemClickListener(onItemClickListenerByGId onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }

    private interface onItemClickListenerByGId {
        void onClickListener(int position, String price, String imageUrl, String styleName);
    }

}
