package com.example.module_message.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.adapter.ConversationListAdapterEx;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.presenter.EmptyPresenter;
import com.example.commonlib.util.InRongIMConnect;
import com.example.commonlib.util.RongUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.ObservableScrollView;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.module_message.R;
import com.example.module_message.R2;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;

@Route(path = RouterUtil.MESSAGE_Fragment_Main)
public class MessageFragment extends BaseFragment<EmptyPresenter> {
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


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }




    private void initRongFragment() {
        RongUtil.connect((String) SharePreferenceUtil.getUser("imToken", "String"), new InRongIMConnect() {
            @Override
            public void onConnectSuccess() {
                ConversationListFragment listFragment = (ConversationListFragment) ConversationListFragment.instantiate(getContext(), ConversationListFragment.class.getName());
                Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(RongPushClient.ConversationType.PRIVATE.getName(), "false")
                        .appendQueryParameter(RongPushClient.ConversationType.GROUP.getName(), "false")
                        .appendQueryParameter(RongPushClient.ConversationType.DISCUSSION.getName(), "false")
                        .appendQueryParameter(RongPushClient.ConversationType.PUBLIC_SERVICE.getName(), "false")
                        .appendQueryParameter(RongPushClient.ConversationType.SYSTEM.getName(), "false")
                        .build();
                listFragment.setUri(uri);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //将融云的Fragment界面加入到我们的页面。
                transaction.add(R.id.conversationlist, listFragment);
                transaction.commitAllowingStateLoss();
            }

            @Override
            public void onConnectFailed() {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        ryRecent = view.findViewById(R.id.ry_recent);
        ryRecent.setLayoutManager(new LinearLayoutManager(getContext()));

        smlContact.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000);

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
        initRongFragment();
    }

    private int mHeight;
    private static final String TAG = "MessageFragment";

    @Override
    public int initLayout() {
        return R.layout.fragment_message;
    }


    @Override
    public EmptyPresenter initPresenter() {
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
