package com.example.module_message.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.view.ObservableScrollView;
import com.example.module_message.R;
import com.example.module_message.R2;
import com.example.module_message.adapter.ConversationAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route(path = RouterUtil.MESSAGE_Fragment_Main)
public class MessageFragment extends BaseFragment<GoodsDetailPresenter> {
    @BindView(R2.id.sml_contact)
    SmartRefreshLayout smlContact;
    Unbinder unbinder;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    Unbinder unbinder1;
    @BindView(R2.id.sl_message)
    ObservableScrollView slMessage;
    @BindView(R2.id.tv_express)
    TextView tvExpress;
    @BindView(R2.id.tv_notice)
    TextView tvNotice;
    @BindView(R2.id.tv_other)
    TextView tvOther;
    private RecyclerView ryRecent;
    private ConversationAdapter conversationAdapter;


    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        ryRecent = view.findViewById(R.id.ry_recent);
        ryRecent.setLayoutManager(new LinearLayoutManager(getContext()));
        conversationAdapter = new ConversationAdapter(null);
        ryRecent.setAdapter(conversationAdapter);
        conversationAdapter.bindToRecyclerView(ryRecent);
        View inflate = View.inflate(getContext(), R.layout.common_empty_message, null);
        TextView viewById = inflate.findViewById(R.id.tv_empty);
        conversationAdapter.setEmptyView(inflate);
        smlContact.autoRefresh();
        smlContact.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {//b20a65a13a3b129cbbdc228b21a34880
                LoginInfo info = new LoginInfo("123456", "b20a65a13a3b129cbbdc228b21a34880");
                RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        NIMClient.getService(MsgService.class).queryRecentContacts()
                                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                                    @Override
                                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                                        Log.i(TAG, "onResult: " + code);
                                        if (code == 200) {
                                            conversationAdapter.replaceData(recents);
                                        } else {
                                            Log.i(TAG, "onResult: " + e);
                                        }
                                        smlContact.finishRefresh();
                                    }
                                });
                    }

                    @Override
                    public void onFailed(int code) {
                        Toast.makeText(getContext(), "获取会话出错，错误代码：" + code, Toast.LENGTH_SHORT).show();
                        smlContact.finishRefresh();
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                };
                NIMClient.getService(AuthService.class).login(info)
                        .setCallback(callback);
            }
        });
        smlContact.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(3000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                super.onHeaderPulling(header, percent, offset, headerHeight, extendHeight);
                tvTitle.setAlpha(1 - Math.min(percent, 1));
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int footerHeight, int extendHeight) {
                super.onHeaderReleasing(header, percent, offset, footerHeight, extendHeight);
                tvTitle.setAlpha(1 - Math.min(percent, 1));
            }
        });

        ViewTreeObserver viewTreeObserver = tvTitle.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = tvTitle.getHeight();//这里取的高度应该为图片的高度-标题栏
                slMessage.setOnObservableScrollViewListener(new ObservableScrollView.OnObservableScrollViewListener() {
                    @Override
                    public void onObservableScrollViewListener(int l, int scrollY, int oldl, int oldt) {
                        if (scrollY <= 0) {
                            tvTitle.setTextColor(0x000000000);
                            tvTitle.setBackgroundColor(Color.argb(0, 255, 255, 255));
                        } else if (scrollY > 0 && scrollY < mHeight) {
                            float scale = (float) scrollY / mHeight;//算出滑动距离比例
                            float alpha = (255 * scale);//得到透明度
                            tvTitle.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                            tvTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        } else {
                            tvTitle.setTextColor(Color.argb(255, 0, 0, 0));
                            tvTitle.setBackgroundColor(Color.argb(255, 255, 255, 255));
                        }
                    }
                });
            }
        });
    }

    private int mHeight;
    private static final String TAG = "MessageFragment";

    @Override
    public int initLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public GoodsDetailPresenter initPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R2.id.tv_express, R2.id.tv_notice, R2.id.tv_other})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_express) {
            startActivity(new Intent(getContext(), MessageExpressActivity.class));
        } else if (i == R.id.tv_notice) {
            startActivity(new Intent(getContext(), MessageNoticeActivity.class));
        } else if (i == R.id.tv_other) {
            startActivity(new Intent(getContext(), MessageOtherActivity.class));
        }
    }
}
