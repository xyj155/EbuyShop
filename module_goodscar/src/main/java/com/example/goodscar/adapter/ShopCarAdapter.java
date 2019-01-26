package com.example.goodscar.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.gson.ShopCarGson;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.goodscar.contract.GoodsOperationContract;
import com.example.goodscar.presenter.GoodsOperationPresenter;
import com.example.goodscar.presenter.ShopCarPresenter;
import com.example.goodscar.view.ShopCarFragment;
import com.xuyijie.goodscar.R;

import java.util.List;

public class ShopCarAdapter extends BaseQuickAdapter<ShopCarGson, BaseViewHolder> implements GoodsOperationContract.View {

    private GoodsOperationPresenter goodsOperationPresenter = new GoodsOperationPresenter(this);
    private ShopCarFragment goodsCarFragment;
    private List<ShopCarGson> carGsonList;
    private Context context;

    public ShopCarAdapter(@Nullable List<ShopCarGson> data, ShopCarFragment goodsCarFragment, Context context) {
        super(R.layout.abc_goods_shopcar_item_layout, data);
        this.context = context;
        this.goodsCarFragment = goodsCarFragment;
        this.carGsonList = data;
    }

    private CheckInterface checkInterface;

    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }

    private onGoodsItemCheckChangeListener onGoodsItemCheckChangeListener;

    public void setOnGoodsItemCheckChangeListener(ShopCarAdapter.onGoodsItemCheckChangeListener onGoodsItemCheckChangeListener) {
        this.onGoodsItemCheckChangeListener = onGoodsItemCheckChangeListener;
    }

    public interface onGoodsItemCheckChangeListener {
        void onGoodsItemCheckChangeListener(String goodsName, int goodsId, boolean isCheck);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ShopCarGson item) {
        helper.setText(R.id.mv_price, "￥" + item.getGoodsPrice())
                .setText(R.id.tv_goods_name, item.getGoodsShopName())
                .setText(R.id.tv_goods_style, item.getGoodsName())
                .setText(R.id.tv_count, item.getCount() + "")
                .setChecked(R.id.cb_goods, item.isCheck())
                .setOnCheckedChangeListener(R.id.cb_goods, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            item.setCheck(true);
                            checkInterface.checkGroup(helper.getPosition(), true);
                            onGoodsItemCheckChangeListener.onGoodsItemCheckChangeListener(item.getGoodsName(), item.getId(), true);
                        } else {
                            item.setCheck(false);
                            checkInterface.checkGroup(helper.getPosition(), false);
                            onGoodsItemCheckChangeListener.onGoodsItemCheckChangeListener(item.getGoodsName(), item.getId(), false);
                        }
                    }
                })
                .setOnClickListener(R.id.iv_minum, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goodsOperationPresenter.addGoodsInShopCarById("1", "1",String.valueOf(item.getId()), "0");
                        ShopCarPresenter shopCarPresenter = goodsCarFragment.initPresenter();
                        shopCarPresenter.queryUserShopCarByUid("1", false);

                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                                if (goodsCarFragment.shopCarAdapter!=null)
                                goodsCarFragment.shopCarAdapter.notifyDataSetChanged();
                            }
                        }, 100);
                    }
                })
                .setOnClickListener(R.id.iv_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goodsOperationPresenter.addGoodsInShopCarById("1","1", String.valueOf(item.getId()), "1");
                        ShopCarPresenter shopCarPresenter = goodsCarFragment.initPresenter();
                        shopCarPresenter.queryUserShopCarByUid((String) SharePreferenceUtil.getUser("uid","String"), false);
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                                if (goodsCarFragment.shopCarAdapter!=null)
                                    goodsCarFragment.shopCarAdapter.notifyDataSetChanged();
                            }
                        }, 100);
                    }
                });
        GlideUtil.loadRoundCornerAvatarImage(item.getGoodsPicUrl(), (ImageView) helper.getView(R.id.iv_goods_pic), 8);
    }


    @Override
    public void addGoodsInShopCar(boolean isSuccess) {
        if (isSuccess ) {
            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(String msg) {
        updateShopCar.finishUpload();
    }

    @Override
    public void showDialog(String msg) {

        updateShopCar.uploadShopCar();
    }

    @Override
    public void hideDialog() {
        updateShopCar.finishUpload();
    }

    private UpdateShopCarInterface updateShopCar;

    public void setUpdateShopCar(UpdateShopCarInterface updateShopCar) {
        this.updateShopCar = updateShopCar;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public interface UpdateShopCarInterface {
        void uploadShopCar();

        void finishUpload();
    }


}
