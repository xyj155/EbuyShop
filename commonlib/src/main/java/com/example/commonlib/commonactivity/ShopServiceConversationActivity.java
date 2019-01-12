package com.example.commonlib.commonactivity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.adapter.ShopServiceConversationAdapter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.entity.ConversationEntity;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.util.RouterUtil;
import com.netease.nimlib.sdk.InvocationFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = RouterUtil.SHOPSERVICE)
public class ShopServiceConversationActivity extends BaseActivity<GoodsDetailContract.View, GoodsDetailPresenter> {

    @BindView(R2.id.tv_send)
    TextView tvSend;
    @BindView(R2.id.et_input)
    EditText etInput;

    RecyclerView msgRecycleView;
    private ShopServiceConversationAdapter shopServiceConversationAdapter;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsDetailPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_shop_service;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        doLogin();
        msgRecycleView = findViewById(R.id.msg_recycle_view);
        shopServiceConversationAdapter = new ShopServiceConversationAdapter(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShopServiceConversationActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        msgRecycleView.setLayoutManager(linearLayoutManager);
        msgRecycleView.setAdapter(shopServiceConversationAdapter);
        shopServiceConversationAdapter.bindToRecyclerView(msgRecycleView);
        ConversationEntity conversationEntity = new ConversationEntity(ConversationEntity.TYPE_EMPTY_MESSAGE, null);
        shopServiceConversationAdapter.addData(conversationEntity);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    tvSend.setBackgroundResource(R.drawable.abc_tv_conversation_send_normal_bg);
                    tvSend.setClickable(false);
                } else {
                    tvSend.setBackgroundResource(R.drawable.abc_tv_conversation_send_changed_bg);
                    tvSend.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                    Log.i(TAG, "onEvent: " + messages.get(0).getContent());
                    IMMessage imMessage = messages.get(0);
                    shopServiceConversationAdapter.addData(ConversationEntity.service(imMessage));
                    msgRecycleView.scrollToPosition(shopServiceConversationAdapter.getItemCount() - 1);
                }
            };

    @Override
    public void initData() {
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);

    }

    public void doLogin() {//c4d6bafe22f548340d10e84ee95f459a  12345678   3769a6257704ff46478b891977534290  123456789
        createDialog("聊天记录加载中...");
        LoginInfo info = new LoginInfo("12345678", "c4d6bafe22f548340d10e84ee95f459a"); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        IMMessage anchor = MessageBuilder.createEmptyMessage("123456789", SessionTypeEnum.P2P, System.currentTimeMillis());
                        InvocationFuture<List<IMMessage>> listInvocationFuture = NIMClient.getService(MsgService.class).pullMessageHistory(anchor, 100, false);
                        listInvocationFuture.setCallback(new RequestCallback<List<IMMessage>>() {
                            @Override
                            public void onSuccess(List<IMMessage> param) {
                                hideDlalog();
                                Collections.reverse(param);
                                for (IMMessage imMessage : param) {
                                    if (imMessage.getDirect() == MsgDirectionEnum.In) {
                                        ConversationEntity conversationEntity = new ConversationEntity(ConversationEntity.TYPE_SERVICES_MESSAGE, imMessage);
                                        shopServiceConversationAdapter.addData(conversationEntity);
                                    } else {
                                        ConversationEntity conversationEntity = new ConversationEntity(ConversationEntity.TYPE_CLIENT_MESSAGE, imMessage);
                                        shopServiceConversationAdapter.addData(conversationEntity);
                                    }
                                }
                                msgRecycleView.scrollToPosition(shopServiceConversationAdapter.getItemCount() - 1);

                            }

                            @Override
                            public void onFailed(int code) {
                                hideDlalog();
                                Toast.makeText(ShopServiceConversationActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onException(Throwable exception) {

                            }
                        });
//                        NIMClient.getService(MsgService.class).queryMessageListEx(anchor, QueryDirectionEnum.QUERY_OLD,
//                                100, true).setCallback(new RequestCallbackWrapper<List<IMMessage>>() {
//                            @Override
//                            public void onResult(int code, List<IMMessage> result, Throwable exception) {
//                                for (IMMessage imMessage : result) {
//                                    if (imMessage.getDirect() == MsgDirectionEnum.In) {
//                                        ConversationEntity conversationEntity = new ConversationEntity(ConversationEntity.TYPE_SERVICES_MESSAGE, imMessage);
//                                        shopServiceConversationAdapter.addData(conversationEntity);
//                                    } else {
//                                        ConversationEntity conversationEntity = new ConversationEntity(ConversationEntity.TYPE_CLIENT_MESSAGE, imMessage);
//                                        shopServiceConversationAdapter.addData(conversationEntity);
//                                    }
//                                }
//                                msgRecycleView.scrollToPosition(shopServiceConversationAdapter.getItemCount() - 1);
//                            }
//                        });
//
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.i(TAG, "onFailed: ");
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.i(TAG, "onException: " + exception.getMessage());
                    }

                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
    }

    @OnClick({R2.id.tv_send})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_send) {
            if (!etInput.getText().toString().isEmpty()) {
                SessionTypeEnum sessionType = SessionTypeEnum.P2P;
                String text = etInput.getText().toString();
                IMMessage textMessage = MessageBuilder.createTextMessage("123456789", sessionType, text);
                NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
                etInput.setText("");
                shopServiceConversationAdapter.addData(ConversationEntity.client(textMessage));
                msgRecycleView.scrollToPosition(shopServiceConversationAdapter.getItemCount() - 1);
            }

        }
    }
}
