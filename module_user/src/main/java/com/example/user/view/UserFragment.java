package com.example.user.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.commonlib.MyApp;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.commonactivity.BrowserActivity;
import com.example.commonlib.contract.UserMemberDateContract;
import com.example.commonlib.gson.UserPaymentGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.presenter.UserMemberDatePresenter;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.MyDialog;
import com.example.commonlib.view.ObservableScrollView;
import com.example.commonlib.view.WaveView;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.user.contract.UserInformationContract;
import com.example.user.contract.UserPaymentContract;
import com.example.user.presenter.UserInformationPresenter;
import com.example.user.presenter.UserPaymentPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

@Route(path = RouterUtil.Me_Fragment_Main)
public class UserFragment extends BaseFragment<UserPaymentPresenter> implements UserPaymentContract.View, UserMemberDateContract.View, UserInformationContract.View {
    @BindView(R2.id.tv_waitpay)
    TextView tvWaitpay;
    @BindView(R2.id.tv_my_invite)
    TextView tv_my_invite;
    @BindView(R2.id.iv_setting)
    ImageView ivSetting;
    @BindView(R2.id.tv_my_response)
    TextView tvMyResponse;
    @BindView(R2.id.tv_waitsend)
    TextView tvWaitsend;
    @BindView(R2.id.tv_wait_receiver)
    TextView tvWaitReceiver;
    @BindView(R2.id.tv_wait_evaluate)
    TextView tvWaitEvaluate;
    Unbinder unbinder1;
    @BindView(R2.id.tv_substitute_pay)
    TextView tvSubstitutePay;
    @BindView(R2.id.tv_substitute_ship)
    TextView tvSubstituteShip;
    @BindView(R2.id.tv_substitute_collection)
    TextView tvSubstituteCollection;
    @BindView(R2.id.tv_substitute_evluate)
    TextView tvSubstituteEvluate;
    @BindView(R2.id.tv_my_service)
    TextView tv_my_service;
    @BindView(R2.id.tv_my_collection)
    TextView tvMyCollection;
    @BindView(R2.id.tv_my_recruit)
    TextView tvMyRecruit;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.rl_orders)
    RelativeLayout rlOrder;
    @BindView(R2.id.sv_mine)
    ObservableScrollView svMine;
    @BindView(R2.id.rl_toolbar)
    RelativeLayout rlToolbar;
    @BindView(R2.id.ll_member_goods)
    LinearLayout llMemberGoods;
    @BindView(R2.id.sml_user)
    SmartRefreshLayout sml_user;
    private WaveView waveView;
    private ImageView ivHead;
    @BindView(R2.id.iv_vip)
    ImageView iv_vip;
    TextView tvCoupon;
    Unbinder unbinder;
    private int mHeight;
    private MyDialog myDialog1;
    private UserMemberDatePresenter userMemberDatePresenter = new UserMemberDatePresenter(this);
    private UserInformationPresenter userInformationPresenter = new UserInformationPresenter(this);

    @Override
    public void initData() {
        sml_user.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryUserOrderCount((String) SharePreferenceUtil.getUser("uid", "String"));
            }
        });
    }


    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        tvUsername.setText(String.valueOf(SharePreferenceUtil.getUser("username", "String")));
        waveView = view.findViewById(R.id.wave_view);
        ivHead = view.findViewById(R.id.ivHead);

        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0, 0, 0, (int) y + 18);
                ivHead.setLayoutParams(lp);
            }
        });
        Log.i(TAG, "initView: " + SharePreferenceUtil.getUser("avatar", "String"));
        Log.i(TAG, "initView: " + SharePreferenceUtil.getUser("avatar", "String"));
//        GlideUtil.loadGeneralImage(SharePreferenceUtil.getUser("avatar", "String"),ivHead);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(MyApp.getInstance()).asBitmap().apply(requestOptions).load(SharePreferenceUtil.getUser("avatar", "String")).into(ivHead);
//        Glide.with(getContext()).asBitmap().apply(new RequestOptions().error(R.mipmap.ic_user_avatar_bg)).load(SharePreferenceUtil.getUser("avatar", "String")).into(ivHead);
        ViewTreeObserver viewTreeObserver = rlToolbar.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlToolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = rlToolbar.getHeight();//这里取的高度应该为图片的高度-标题栏
                svMine.setOnObservableScrollViewListener(new ObservableScrollView.OnObservableScrollViewListener() {
                    @Override
                    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {

                        if (t <= 0) {
                            //顶部图处于最顶部，标题栏透明
                            rlToolbar.setBackgroundColor(Color.argb(0, 255, 255, 255));
                        } else if (t > 0 && t < mHeight) {
                            float scale = (float) t / mHeight;//算出滑动距离比例
                            float alpha = (255 * scale);//得到透明度
                            rlToolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        } else {
                            rlToolbar.setBackgroundColor(Color.argb(255, 255, 255, 255));
                        }

                    }
                });
            }
        });
        String user = String.valueOf(SharePreferenceUtil.getUser("member", "String"));
        if (user.equals("1")) {
            GlideUtil.loadGeneralImage(R.mipmap.vip_rank1, iv_vip);
            iv_vip.setVisibility(View.VISIBLE);
        } else if (user.equals("2")) {
            GlideUtil.loadGeneralImage(R.mipmap.vip_rank2, iv_vip);
            iv_vip.setVisibility(View.VISIBLE);
        } else if (user.equals("3")) {
            iv_vip.setVisibility(View.VISIBLE);
            GlideUtil.loadGeneralImage(R.mipmap.vip_rank3, iv_vip);
        } else {
            iv_vip.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        userMemberDatePresenter.judgementMember((String) SharePreferenceUtil.getUser("uid", "String"));
        mPresenter.queryUserOrderCount((String) SharePreferenceUtil.getUser("uid", "String"));
    }

    private static final String TAG = "UserFragment";

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public UserPaymentPresenter initPresenter() {
        return new UserPaymentPresenter(this);
    }


    @OnClick({R2.id.tv_my_invite, R2.id.ivHead, R2.id.ll_member_goods, R2.id.tv_my_service, R2.id.iv_setting, R2.id.tv_my_recruit, R2.id.tv_my_collection, R2.id.tv_my_response, R2.id.rl_orders, R2.id.tv_my_vip, R2.id.tv_waitpay, R2.id.tv_waitsend, R2.id.tv_wait_receiver, R2.id.tv_wait_evaluate})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_my_vip) {
            Intent intent = new Intent(getContext(), UserCouponActivity.class);
            intent.putExtra("isUse", true);
            startActivity(intent);
        } else if (id == R.id.tv_waitpay) {
            Intent intent = new Intent(getContext(), UserFormStatusActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
        } else if (id == R.id.tv_waitsend) {
            Intent intent = new Intent(getContext(), UserFormStatusActivity.class);
            intent.putExtra("index", 2);
            startActivity(intent);
        } else if (id == R.id.ivHead) {
            ISListConfig config = new ISListConfig.Builder()
                    .multiSelect(false)
                    .rememberSelected(false)
                    .btnBgColor(Color.GRAY)
                    .btnTextColor(Color.BLUE)
                    .statusBarColor(Color.parseColor("#ffffff"))
                    .backResId(R.mipmap.ic_back)
                    .title("头像选择")
                    .titleColor(Color.BLACK)
                    .titleBgColor(Color.parseColor("#ffffff"))
                    .cropSize(1, 1, 200, 200)
                    .needCrop(true)
                    .needCamera(true)
                    .maxNum(1)
                    .build();
            ISNav.getInstance().toListActivity(this, config, 20);
        } else if (id == R.id.tv_wait_receiver) {
            Intent intent = new Intent(getContext(), UserFormStatusActivity.class);
            intent.putExtra("index", 3);
            startActivity(intent);
        } else if (id == R.id.tv_wait_evaluate) {
            Intent intent = new Intent(getContext(), UserFormStatusActivity.class);
            intent.putExtra("index", 4);
            startActivity(intent);
        } else if (id == R.id.ll_member_goods) {
            Intent intent = new Intent(getContext(), BrowserActivity.class);
            intent.putExtra("url", RetrofitUtils.BASE_URL + "/StuShop/public/index.php/index/Index/memberGoods");
            startActivity(intent);
        } else if (id == R.id.rl_orders) {
            startActivity(new Intent(getContext(), UserAllOrdersActivity.class));
        } else if (id == R.id.tv_my_response) {
            startActivity(new Intent(getContext(), UserFeedBackActivity.class));
        } else if (id == R.id.tv_my_collection) {
            startActivity(new Intent(getContext(), UserCollectionActivity.class));
        } else if (id == R.id.tv_my_recruit) {
            Intent intent = new Intent(getContext(), BrowserActivity.class);
            intent.putExtra("url", RetrofitUtils.BASE_URL + "/StuShop/public/index.php/index/Index/recruitView");
            startActivity(intent);
        } else if (id == R.id.iv_setting) {
            startActivity(new Intent(getContext(), UserSettingActivity.class));
        } else if (id == R.id.tv_my_service) {
            myDialog1 = new MyDialog(getContext(), new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
            myDialog1.setTitle("是否拨打客服热线");
            myDialog1.setContent("服务时间：9:00~21:00");
            myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
                @Override
                public void onCenterItemClick(MyDialog dialog, View view) {
                    int i = view.getId();
                    if (i == R.id.dialog_btn_close) {
                        dialog.dismiss();

                    } else if (i == R.id.dialog_btn_cancel) {
                        callPhone("17374131273");

                    }
                }
            });
            myDialog1.show();
        } else if (id == R.id.tv_my_invite) {
            startActivity(new Intent(getContext(), UserInvitedActivity.class));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 20 && resultCode == RESULT_OK && data != null) {
            Log.i(TAG, "onActivityResult: " + data.getStringArrayListExtra("result").get(0));
            File file = new File(data.getStringArrayListExtra("result").get(0));
            RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part avatar = MultipartBody.Part.createFormData("avatar", file.getName(), fileRQ);
            userInformationPresenter.updateUserAvatar(String.valueOf(SharePreferenceUtil.getUser("uid", "String")),
                    avatar, String.valueOf(SharePreferenceUtil.getUser("username", "String")));
        }
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void setUserOrderCount(UserPaymentGson userOrderCount) {
        Log.i(TAG, "setUserOrderCount: " + userOrderCount.toString());
        if (sml_user != null) {
            sml_user.finishRefresh();
        }
        if (userOrderCount.getCollectionGoods() > 0) {
            tvSubstituteCollection.setText(userOrderCount.getCollectionGoods() + "");
            tvSubstituteCollection.setVisibility(View.VISIBLE);
        } else {
            tvSubstituteCollection.setVisibility(View.GONE);
        }
        if (userOrderCount.getSubstitutePayment() > 0) {
            tvSubstitutePay.setText(userOrderCount.getSubstitutePayment() + "");
            tvSubstitutePay.setVisibility(View.VISIBLE);
        } else {
            tvSubstitutePay.setVisibility(View.GONE);
        }
        if (userOrderCount.getSubstitutesEvaluated() > 0) {
            tvSubstituteEvluate.setText(userOrderCount.getSubstitutesEvaluated() + "");
            tvSubstituteEvluate.setVisibility(View.VISIBLE);
        } else {
            tvSubstituteEvluate.setVisibility(View.GONE);
        }
        if (userOrderCount.getSubstituteShipment() > 0) {
            tvSubstituteShip.setText(userOrderCount.getSubstituteShipment() + "");
            tvSubstituteShip.setVisibility(View.VISIBLE);
        } else {
            tvSubstituteShip.setVisibility(View.GONE);
        }
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
        if (sml_user != null) {
            sml_user.finishRefresh();
        }
        dialogCancel();
    }

    @Override
    public void loadUserMember(int code) {
        Log.i(TAG, "loadUserMember: " + code);
        if (code != 200) {
            Map<String, Object> map = new HashMap<>();
            map.put("islogin", true);
            map.put("member", "0");
            SharePreferenceUtil.saveUser(map);
            iv_vip.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateUserAvatar(boolean success, String vatar) {
        if (success) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(MyApp.getInstance()).asBitmap().apply(requestOptions).load(vatar).into(ivHead);
            Map<String, Object> map = new HashMap<>();
            map.put("avatar", vatar);
            Log.i(TAG, "updateUserAvatar: " + vatar);
            SharePreferenceUtil.saveUser(map);
        } else {
            ToastUtils.show("头像更换失败");
        }
    }
}
