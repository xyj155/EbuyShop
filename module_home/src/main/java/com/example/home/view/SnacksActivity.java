package com.example.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.SnackPaymentActivity;
import com.example.commonlib.gson.SnackGson;
import com.example.commonlib.gson.SnackKindGson;
import com.example.commonlib.gson.SnackShopCarGson;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.BounceRecyclerView;
import com.example.commonlib.view.MyDialog;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.home.adapter.SnackFoodsAdapter;
import com.example.home.contract.SnackFoodsShopCarContract;
import com.example.home.contract.SnackGoodsContract;
import com.example.home.presenter.SnackFoodsShopCarPresenter;
import com.example.home.presenter.SnackGoodsPresenter;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SnacksActivity extends BaseActivity<SnackGoodsContract.View, SnackGoodsPresenter> implements SnackGoodsContract.View, SnackFoodsShopCarContract.View {

    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tv_shopcar_count)
    TextView tvShopcarCount;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.ry_item)
    BounceRecyclerView ryItem;
    @BindView(R2.id.ry_list)
    RecyclerView ryList;
    @BindView(R2.id.sl_sort)
    SmartRefreshLayout slSort;
    @BindView(R2.id.iv_shopcar)
    ImageView ivShopcar;
    @BindView(R2.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    @BindView(R2.id.tv_money)
    TextView tvMoney;
    @BindView(R2.id.tv_submit)
    TextView tvSubmit;
    private SnackFoodsAdapter snackFoodsAdapter;
    private SnackKindAdapter itemListAdapter;
    private Handler mHanlder;
    public SnackFoodsShopCarPresenter snackFoodsShopCarPresenter = new SnackFoodsShopCarPresenter(this);
    private ShopCarAdapter shopCarAdapter;
    private View sheetDialog;
    private RecyclerView ryShopCar;
    private TextView tvClean;


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public SnackGoodsPresenter getPresenter() {
        return new SnackGoodsPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_snacks;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("零食铺").setToolBarMenu("我的订单").setToolBarMenuClickListener(new onMenuClickListener() {
            @Override
            public void onMenuClickListener() {
                startActivity(SnackOrderListActivity.class);
            }
        });
        mHanlder = new Handler(getMainLooper());
        mPresenter.queryAllSnack();
        ryItem.setLayoutManager(new LinearLayoutManager(SnacksActivity.this));
        ryList.setLayoutManager(new LinearLayoutManager(SnacksActivity.this));
        ryList.setFocusableInTouchMode(false);
        ryList.setFocusable(false);
        ryList.setHasFixedSize(true);
        mPresenter.querySnackByKindId("1", String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
        sheetDialog = LayoutInflater.from(SnacksActivity.this).inflate(R.layout.snack_bottom_recycler_view, null, false);
        ryShopCar = sheetDialog.findViewById(R.id.ry_shopcar);
        tvClean = sheetDialog.findViewById(R.id.tv_clean);
        shopCarAdapter = new ShopCarAdapter(null);
        ryShopCar.setLayoutManager(new LinearLayoutManager(SnacksActivity.this));
        ryShopCar.setFocusableInTouchMode(false);
        ryShopCar.setFocusable(false);
        ryShopCar.setHasFixedSize(true);
        ryShopCar.setAdapter(shopCarAdapter);
        tvClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsgDialog("清空购物车", "是否清空全部商品", new OnItemClickListener() {
                    @Override
                    public void onConfirm(MyDialog dialog) {
                        snackFoodsShopCarPresenter.addSnackByUserId(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), "", "", "3");
                        if (shopCarAdapter != null) {
                            shopCarAdapter.notifyDataSetChanged();
                        }
                    }
                });

            }
        });
        slSort.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryUserShopCarAllSnack(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
                mPresenter.querySnackByKindId(String.valueOf(foodsParentId), String.valueOf(SharePreferenceUtil.getUser("uid", "String")));

            }
        });
        snackFoodsAdapter = new SnackFoodsAdapter(SnacksActivity.this, null);
        ryList.setAdapter(snackFoodsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.queryUserShopCarAllSnack(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
        mPresenter.querySnackByKindId(String.valueOf(foodsParentId), String.valueOf(SharePreferenceUtil.getUser("uid", "String")));

    }

    @Override
    public void initData() {
        mPresenter.queryUserShopCarAllSnack(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));

    }

    private int foodsParentId = 1;

    @Override
    public void queryAllSnack(List<SnackKindGson> snackKindGsons) {
        itemListAdapter = new SnackKindAdapter(snackKindGsons);
        Log.i(TAG, "queryAllSnack: " + snackKindGsons.toString());
        ryItem.setAdapter(itemListAdapter);
        itemListAdapter.setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onClickListener(int pid, String name) {
                foodsParentId = pid;
                mPresenter.querySnackByKindId(String.valueOf(pid), String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
            }
        });
        if (slSort != null)
            slSort.finishRefresh();
    }

    @Override
    public void querySnackByKindId(List<SnackGson> list) {
        snackFoodsAdapter.replaceData(list);
        if (slSort != null)
            slSort.finishRefresh();

    }

    @Override
    public void queryUserSnackShopCarAllSnack(List<SnackShopCarGson> list) {
        int shopCount = 0;
        double shopCarMoney = 0.00;
        if (list.size() > 0) {
            for (SnackShopCarGson snackShopCarGson : list) {
                shopCount += snackShopCarGson.getCount();
                shopCarMoney += Double.valueOf(snackShopCarGson.getFoodsPrice()) * snackShopCarGson.getCount();
            }
            tvShopcarCount.setText(String.valueOf(shopCount));
            tvMoney.setText("￥" +  String.format("%.2f", shopCarMoney));
            tvShopcarCount.setVisibility(View.VISIBLE);
            tvSubmit.setBackgroundResource(R.drawable.bg_btn_shopcar);
            tvSubmit.setTextColor(0xffffffff);
            tvSubmit.setClickable(true);
            tvSubmit.setText("去结算");
        } else {
            tvSubmit.setBackgroundColor(0xff535254);
            tvSubmit.setTextColor(0xffa2a2a2);
            tvSubmit.setClickable(false);
            tvShopcarCount.setText(String.valueOf(0));
            tvMoney.setText("￥ 0.00");
            tvSubmit.setText("无商品");
            tvShopcarCount.setVisibility(View.GONE);
        }
        if (shopCarAdapter != null) {
            shopCarAdapter.replaceData(list);
            shopCarAdapter.notifyDataSetChanged();
        }
        if (slSort != null)
            slSort.finishRefresh();
    }

    @Override
    public void submitUserOrder(boolean list, String orderNum) {
        if (list) {
            Intent intent = new Intent(SnacksActivity.this, SnackPaymentActivity.class);
            intent.putExtra("orderNum", orderNum);
            startActivity(intent);
        } else {
            ToastUtils.show("下单失败");
        }
    }

    //显示购物车列表
    private void showCart() {
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            bottomSheetLayout.showWithSheetView(sheetDialog);
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
        if (slSort != null)
            slSort.finishRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    //刷新布局 总价、购买数量等
    public void update(int position, String goodsId, String tastyId, boolean isDelete, boolean isInShopCar,int needCount) {
        Log.i(TAG, "update: " + tastyId);
        if (isDelete) {

        } else {
            for (int i = 0; i < needCount; i++) {
                snackFoodsShopCarPresenter.addSnackByUserId((String) SharePreferenceUtil.getUser("uid", "String"), goodsId, tastyId, "0");
            }
        }
        if (isInShopCar) {
            mPresenter.queryUserShopCarAllSnack(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
        } else {
            if (snackFoodsAdapter != null) {
                snackFoodsAdapter.notifyItemChanged(position);
            }
        }

    }

    public void playAnimation(int[] start_location) {
        ImageView img = new ImageView(this);
        img.setImageResource(R.mipmap.shop_add);
        setAnim(img, start_location);
    }

    private void setAnim(final View v, int[] start_location) {
        final ViewGroup flShopcar = findViewById(R.id.rl_container);
        addViewToAnimLayout(flShopcar, v, start_location);
        Animation set = createAnim(start_location[0], start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                mHanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flShopcar.removeView(v);
                    }
                }, 300);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    private Animation createAnim(int startX, int startY) {
        int[] des = new int[2];
        ivShopcar.getLocationInWindow(des);
        AnimationSet set = new AnimationSet(false);
        Animation translationX = new TranslateAnimation(0, des[0] - startX, 0, 0);
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1] - startY);
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1, 0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    private void addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y - loc[1]);
        vg.addView(view);
    }

    @Override
    public void addSnackShopCar(boolean success) {
        if (success) {
            mPresenter.queryUserShopCarAllSnack(String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
            mPresenter.querySnackByKindId(String.valueOf(foodsParentId), String.valueOf(SharePreferenceUtil.getUser("uid", "String")));

        } else {
            ToastUtils.show("失败了哦！");
        }
        if (slSort != null)
            slSort.finishRefresh();
    }

    @OnClick({R2.id.iv_shopcar, R2.id.tv_submit})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_shopcar) {
            showCart();
        } else if (id == R.id.tv_submit) {
            mPresenter.submitSnackFoodsOrder(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(SharePreferenceUtil.getUser("userToken", "String")));
        }

    }


    public class SnackKindAdapter extends RecyclerView.Adapter<SnackKindAdapter.ViewHolder> {

        private int index = 0;//标记当前选择的选项
        private List<SnackKindGson> data;

        public SnackKindAdapter(@Nullable List<SnackKindGson> data) {

            this.data = data;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ry_item_list_adapter, null));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.rbTitle.setText(data.get(position).getKindName());
            holder.rbTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        index = position;
                        onItemClickListener.onClickListener(data.get(position).getId(), data.get(position).getKindName());
                    }
                    notifyDataSetChanged();
                }
            });
            if (index == position) {
                holder.indicate.setVisibility(View.VISIBLE);
                holder.rbTitle.setTextColor(0xff000000);
            } else {
                holder.indicate.setVisibility(View.GONE);
                holder.rbTitle.setTextColor(0xff9E9E9E);
            }
        }


        @Override
        public int getItemCount() {
            return data.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            private RadioButton rbTitle;
            private View indicate;

            public ViewHolder(View itemView) {
                super(itemView);
                rbTitle = itemView.findViewById(R.id.rb_kind_name);
                indicate = itemView.findViewById(R.id.view_indicate);
            }
        }

        private onItemClickListener onItemClickListener;

        public void setOnItemClickListener(onItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

    }

    public interface onItemClickListener {
        void onClickListener(int pid, String name);
    }

    public class ShopCarAdapter extends BaseQuickAdapter<SnackShopCarGson, BaseViewHolder> {


        public ShopCarAdapter(List<SnackShopCarGson> dataList) {
            super(R.layout.pop_shopcar_list, dataList);

        }


        @Override
        protected void convert(final BaseViewHolder helper, final SnackShopCarGson item) {
            Double aDouble = Double.valueOf(item.getFoodsPrice());
            helper.setText(R.id.tv_name, item.getFoodName())
                    .setText(R.id.tv_price, String.format("%.2f", aDouble))
                    .setText(R.id.tv_count, String.valueOf(item.getCount()))
                    .setText(R.id.tv_tasty_name, item.getFoodsTaste())
                    .setOnClickListener(R.id.iv_add, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < item.getNeedCount(); i++) {
                                snackFoodsShopCarPresenter.addSnackByUserId(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(item.getFoodsId()), String.valueOf(item.getTasteId()), "0");
                            }

                            notifyDataSetChanged();

                        }
                    })
                    .setOnClickListener(R.id.iv_reduce, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < item.getNeedCount(); i++) {
                                snackFoodsShopCarPresenter.addSnackByUserId(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(item.getFoodsId()), String.valueOf(item.getTasteId()), "1");

                            }
                            TextView view = (TextView) helper.getView(R.id.tv_count);
                            if (Integer.valueOf(view.getText().toString()) < 2) {
                                remove(helper.getPosition());
                            }
                            notifyDataSetChanged();
                        }
                    });
        }
    }

}
