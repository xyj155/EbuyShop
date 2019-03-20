package com.example.home.adapter;

import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.gson.SnackGson;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.home.view.SnacksActivity;
import com.xuyijie.home.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


public class SnackFoodsAdapter extends BaseQuickAdapter<SnackGson, BaseViewHolder> {

        private SnacksActivity mContext;
    private NumberFormat nf;
    private LayoutInflater mInflater;

    public SnackFoodsAdapter(SnacksActivity mContext, @Nullable List<SnackGson> data) {
        super(R.layout.ry_goods_list_item, data);
        this.mContext = mContext;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final SnackGson item) {
        final TextView tvCount = (TextView) helper.getView(R.id.tv_count);
        final ImageView tvMinus = (ImageView) helper.getView(R.id.iv_reduce);
        helper.setText(R.id.tv_name, item.getFoodName());//商品名称
        GlideUtil.loadRoundCornerAvatarImage(item.getFoodPicture(), (ImageView) helper.getView(R.id.iv_cover), 10);
        Log.i(TAG, "bindData: " + item.getGoodsCount());
        if (item.getGoodsCount() < 1) {
            tvCount.setVisibility(View.GONE);
            tvMinus.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
            tvMinus.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "bindData: " + item.getFoodsPrice());
        helper.setText(R.id.tv_price, String.format("%.2f",item.getFoodsPrice()));//商品价格
        helper.setText(R.id.tv_tags, " 规格： " + item.getFoodsSize());
        tvCount.setText(String.valueOf(item.getGoodsCount() + ""));//商品数量
        helper.setOnClickListener(R.id.iv_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSheetDialog(helper.getView(R.id.iv_add),helper.getPosition(), item,tvMinus,tvCount,item.getNeedCount());
            }
        });
        tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getTasty().get(0).getFoodsTaste().equals("默认")) {
                    SnacksActivity activity = mContext;
                    int count = item.getGoodsCount();
                    for (int i = 0; i < item.getNeedCount(); i++) {
                        mContext.snackFoodsShopCarPresenter.addSnackByUserId(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(item.getId()), "", "1");
                    }
                    if (count < 2) {
                        tvMinus.setAnimation(getHiddenAnimation());
                        tvCount.setVisibility(View.GONE);
                    }
                    activity.update(helper.getPosition(), String.valueOf(item.getId()), String.valueOf(item.getTasty().get(0).getStyleId()), true, false,item.getNeedCount());
                    count--;
                    tvCount.setText(String.valueOf(count));
                } else {
                    ToastUtils.show("多规格商品必须在购物车删除");
                }

            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    private void showSheetDialog(final View ivAdd, final int position, final SnackGson snackGson, final ImageView tvMinus, final TextView tvCount, final int needCount) {
        final int[] mtastyId = {snackGson.getTasty().get(0).getStyleId()};
        View view = View.inflate(mContext, R.layout.dialog_goods_tasty_layout, null);
        ImageView ivGoodsPci = (ImageView) view.findViewById(R.id.iv_goods_pic);
        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);

        GlideUtil.loadRoundCornerAvatarImage(snackGson.getFoodPicture(), ivGoodsPci, 10);
        final TextView tvGoodsName = (TextView) view.findViewById(R.id.tv_goods_name);
        tvGoodsName.setText(snackGson.getFoodName());
        final TextView tvChooseTasty = (TextView) view.findViewById(R.id.tv_choose_tasty);
        tvChooseTasty.setText("已选：" + snackGson.getTasty().get(0).getFoodsTaste());
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        TextView tvSubmit = (TextView) view.findViewById(R.id.tv_submit);
        tvPrice.setText(snackGson.getFoodsPrice()+"");
        RecyclerView ryTasty = (RecyclerView) view.findViewById(R.id.ry_tasty);
        TastyAdapterSingleChooseAdapter bottomSheetAdapter = new TastyAdapterSingleChooseAdapter(snackGson.getTasty());
        ryTasty.setHasFixedSize(true);
        ryTasty.setLayoutManager(new GridLayoutManager(mContext, 3));
        ryTasty.setAdapter(bottomSheetAdapter);
        final Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        bottomDialog.setContentView(view);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomSheetAdapter.setOnSnackChooseListener(new onSnackChooseListener() {
            @Override
            public void onChoose(String goodsName, String tastyId) {
                tvChooseTasty.setText("已选：" + goodsName);
                mtastyId[0] = Integer.valueOf(tastyId);
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.update(position, String.valueOf(snackGson.getId()), String.valueOf(mtastyId[0]), false, false,needCount);
                int count = snackGson.getGoodsCount();
                if (count < 1) {
                    tvMinus.setAnimation(getShowAnimation());
                    tvCount.setVisibility(View.VISIBLE);
                }
                int[] loc = new int[2];
                ivAdd.getLocationInWindow(loc);
                mContext.playAnimation(loc);
                bottomDialog.dismiss();
            }
        });
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = mContext.getResources().getDisplayMetrics().heightPixels - 50;
        view.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    //口味适配器
    private class TastyAdapterSingleChooseAdapter extends RecyclerView.Adapter<TastyAdapterSingleChooseAdapter.TastyViewHolder> {
        public void setOnSnackChooseListener(SnackFoodsAdapter.onSnackChooseListener onSnackChooseListener) {
            this.onSnackChooseListener = onSnackChooseListener;
        }

        private onSnackChooseListener onSnackChooseListener;
        private List<SnackGson.TastyBean> tastyBeanList;
        private int index = 0;

        public TastyAdapterSingleChooseAdapter(List<SnackGson.TastyBean> tastyBeanList) {
            this.tastyBeanList = tastyBeanList;
        }


        @Override
        public TastyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ry_goods_tasty_item, parent, false);
            return new TastyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final TastyViewHolder holder, final int position) {

            holder.rbTasty.setText(tastyBeanList.get(position).getFoodsTaste());
            holder.rbTasty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = position;
                    if (onSnackChooseListener != null) {
                        onSnackChooseListener.onChoose(tastyBeanList.get(position).getFoodsTaste(), String.valueOf(tastyBeanList.get(position).getStyleId()));
                    }
                    notifyDataSetChanged();
                }
            });
            if (index == position) {
                holder.rbTasty.setChecked(true);
            } else {
                holder.rbTasty.setChecked(false);
            }
        }


        @Override
        public int getItemCount() {
            return tastyBeanList.size();
        }

        private class TastyViewHolder extends RecyclerView.ViewHolder {
            private RadioButton rbTasty;

            public TastyViewHolder(View itemView) {
                super(itemView);
                rbTasty = itemView.findViewById(R.id.rb_choose);
            }
        }
    }


    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(800);
        return set;
    }

    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(800);
        return set;
    }

    public interface onSnackChooseListener {
        void onChoose(String goodsName, String tastyId);
    }
}

