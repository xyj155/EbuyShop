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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.presenter.EmptyPresenter;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.ObservableScrollView;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.module_message.R;
import com.example.module_message.R2;
import com.example.module_message.adapter.ConversationAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.api.BasicCallback;

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
    private ConversationAdapter conversationAdapter;

    @Override
    public void onResume() {
        super.onResume();
        JMessageClient.login(String.valueOf(SharePreferenceUtil.getUser("username", "String")), "xuyijie", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i(TAG, "gotResult: " + s);
                if (i == 0) {
                    List<Conversation> conversationList = JMessageClient.getConversationList();
                    if (conversationList.size()!=0||conversationList!=null){
                        Log.i(TAG, "gotResult: " + conversationList.size());
                        conversationAdapter.replaceData(conversationList);
                    }
                } else {
                    ToastUtils.show("消息列表获取失败，错误代码：" + i);
                    Log.i(TAG, "gotResult: 消息列表获取失败，错误代码");
                    smlContact.finishRefresh();
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    public void onEventMainThread(MessageEvent event) {
        //do your own business
        Log.i(TAG, "onEventMainThread: ");
        List<Conversation> conversationList = JMessageClient.getConversationList();
        if (conversationList != null) {
            Log.i(TAG, "gotResult:conversationList " + conversationList.size());
            if (conversationAdapter != null) {
                conversationAdapter.replaceData(conversationList);
                conversationAdapter.notifyDataSetChanged();
            }

        }


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
        conversationAdapter.setEmptyView(inflate);
        JMessageClient.registerEventReceiver(this);
        JMessageClient.login(String.valueOf(SharePreferenceUtil.getUser("username", "String")), "xuyijie", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i(TAG, "gotResult: " + s);
                if (i == 0) {
                    List<Conversation> conversationList = JMessageClient.getConversationList();
                    Log.i(TAG, "gotResult: " + conversationList.size());
                    conversationAdapter.replaceData(conversationList);
                    smlContact.finishRefresh();
                } else {
                    ToastUtils.show("消息列表获取失败，错误代码：" + i);
                    Log.i(TAG, "gotResult: 消息列表获取失败，错误代码");
                    smlContact.finishRefresh();
                }
            }
        });
        smlContact.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000);
                JMessageClient.login(String.valueOf(SharePreferenceUtil.getUser("username", "String")), "xuyijie", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        Log.i(TAG, "gotResult: " + s);
                        if (i == 0) {
                            List<Conversation> conversationList = JMessageClient.getConversationList();
                            Log.i(TAG, "gotResult: " + conversationList.size());
                            conversationAdapter.replaceData(conversationList);
                            smlContact.finishRefresh();
                        } else {
                            ToastUtils.show("消息列表获取失败，错误代码：" + i);
                            Log.i(TAG, "gotResult: 消息列表获取失败，错误代码");
                            smlContact.finishRefresh();
                        }
                    }
                });
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
