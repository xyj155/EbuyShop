package com.example.commonlib.commonactivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.adapter.ShopServiceConversationAdapter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.entity.ConversationEntity;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.util.NoFastClickUtils;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.panel.util.KPSwitchConflictUtil;
import com.example.commonlib.view.panel.util.KeyboardUtil;
import com.example.commonlib.view.panel.widget.KPSwitchPanelLinearLayout;
import com.example.commonlib.view.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.ChatRoomMessageEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;


@Route(path = RouterUtil.SHOPSERVICE)
public class ShopServiceConversationActivity extends BaseActivity<GoodsDetailContract.View, GoodsDetailPresenter> {

    @BindView(R2.id.tv_send)
    TextView tvSend;
    @BindView(R2.id.et_input)
    EditText etInput;
    @BindView(R2.id.sml_conversation)
    SmartRefreshLayout smlConversation;

    @BindView(R2.id.msg_recycle_view)
    RecyclerView msgRecycleView;
    @BindView(R2.id.ll_takePhoto)
    LinearLayout llTakePhoto;
    @BindView(R2.id.ll_picture)
    LinearLayout llPicture;
    @BindView(R2.id.ll_video)
    LinearLayout llVideo;
    @BindView(R2.id.ll_goods)
    LinearLayout llGoods;
    private ShopServiceConversationAdapter shopServiceConversationAdapter ;
    private View mSubPanel1;
    @BindView(R2.id.voice_text_switch_iv)
    ImageView mPlusIv1;
    private int count;
    @BindView(R2.id.panel_root)
    KPSwitchPanelLinearLayout mPanelRoot;
    List<ConversationEntity> conversationEntities = new ArrayList<>();
    private Conversation singleConversation1;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsDetailPresenter getPresenter() {
        return null;
    }

    public void onEventMainThread(ChatRoomMessageEvent event) {
        List<Message> msgs = event.getMessages();
        for (Message msg : msgs) {
            Log.i(TAG, "onEvent111: " + msg);
            boolean username = msg.getFromUser().getUserName().equals(SharePreferenceUtil.getUser("username", "String"));
            if (username) {
                shopServiceConversationAdapter.addData(ConversationEntity.client(msg));
            } else {
                shopServiceConversationAdapter.addData(ConversationEntity.service(msg));
            }
        }
        Log.i(TAG, "onEventMainThread: " + msgs.size());
        shopServiceConversationAdapter.notifyDataSetChanged();
    }


    /**
     * 接收消息
     *
     * @param event
     */
    public void onEventMainThread(MessageEvent event) {
        Message newMessage = event.getMessage();//获取此次离线期间会话收到的新消息列表
        Log.i(TAG, "onEvent:2222 " + newMessage);
        boolean username = newMessage.getFromUser().getUserName().equals(SharePreferenceUtil.getUser("username", "String"));
        if (username) {
            conversationEntities.add(ConversationEntity.client(newMessage));
        } else {
            conversationEntities.add(ConversationEntity.service(newMessage));
        }
        newMessage.setHaveRead(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i(TAG, "gotResult: 已读");
            }
        });
        shopServiceConversationAdapter.replaceData(conversationEntities);
        shopServiceConversationAdapter.notifyDataSetChanged();
    }


    @Override
    public int intiLayout() {
        return R.layout.activity_shop_service;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    // 当屏幕分屏/多窗口变化时回调
    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        KPSwitchConflictUtil.onMultiWindowModeChanged(isInMultiWindowMode);
    }

    private Message message = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        createDialog("");
        ButterKnife.bind(this);
        shopServiceConversationAdapter = new ShopServiceConversationAdapter(null,ShopServiceConversationActivity.this);
        initToolBar().setToolBarTitle(getIntent().getStringExtra("shopName"));
        mPanelRoot.setIgnoreRecommendHeight(true);
        mSubPanel1 = mPanelRoot.findViewById(R.id.sub_panel_1);
        smlConversation.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (count > 0) {
                    List<Message> messagesFromOldest = new ArrayList<Message>();
                    List<ConversationEntity> list = new ArrayList<ConversationEntity>();
                    if (count > 12) {//这个是极光SDK 第一个参数是请求的位置  第二个是请求的条数 减一
                        count = count - 12;//每次获取12个
                        List<Message> messagesFromOldest1 = singleConversation1.getMessagesFromOldest(count, 12);
                        messagesFromOldest.addAll(messagesFromOldest1);//获取位置   获取条数
                    } else { // 第二次加载的数据比较多啊，不是 12 条了吧等我回家帮你看看吧，我路上想想我要下班了 好
                        List<Message> messagesFromOldest1 = singleConversation1.getMessagesFromOldest(0, count);
                        messagesFromOldest.addAll(messagesFromOldest1);//获取位置   获取条数
                        count = 0;
                    }
                    for (Message message1 : messagesFromOldest) {
                        message1.setHaveRead(new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                Log.i(TAG, "gotResult: 已读");
                            }
                        });
                        if (message1.getFromName().equals(String.valueOf(SharePreferenceUtil.getUser("telphone", "String")))) {
                            list.add(ConversationEntity.client(message1));
                        } else {
                            list.add(ConversationEntity.service(message1));
                        }
                    }
                    shopServiceConversationAdapter.addData(0, list);
                    list.clear();
                }
                smlConversation.finishRefresh(200);
            }
        });
        KeyboardUtil.attach(this, mPanelRoot,
                new KeyboardUtil.OnKeyboardShowingListener() {
                    @Override
                    public void onKeyboardShowing(boolean isShowing) {
                        Log.d(TAG, String.format("Keyboard is %s", isShowing
                                ? "showing" : "hiding"));
                    }
                });
        KPSwitchConflictUtil.attach(mPanelRoot, etInput,
                new KPSwitchConflictUtil.SwitchClickListener() {
                    @Override
                    public void onClickSwitch(View v, boolean switchToPanel) {
                        if (switchToPanel) {
                            etInput.clearFocus();
                        } else {
                            etInput.requestFocus();
                        }
                    }
                },
                new KPSwitchConflictUtil.SubPanelAndTrigger(mSubPanel1, mPlusIv1));
        msgRecycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot);
                }
                return false;
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShopServiceConversationActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        msgRecycleView.setLayoutManager(linearLayoutManager);
        msgRecycleView.setAdapter(shopServiceConversationAdapter);
        shopServiceConversationAdapter.bindToRecyclerView(msgRecycleView);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    tvSend.setBackgroundResource(R.drawable.abc_tv_conversation_send_normal_bg);
                    tvSend.setClickable(false);
                    mPlusIv1.setVisibility(View.VISIBLE);
                    tvSend.setVisibility(View.GONE);
                } else {
                    tvSend.setBackgroundResource(R.drawable.abc_tv_conversation_send_changed_bg);
                    tvSend.setClickable(true);
                    mPlusIv1.setVisibility(View.GONE);
                    tvSend.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.i(TAG, "initView: " + SharePreferenceUtil.getUser("telphone", "String"));
        JMessageClient.login(String.valueOf(SharePreferenceUtil.getUser("telphone", "String")), "xuyijie", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    mhideDialog();
                    Log.i(TAG, "gotResult: gotResult" + i + s);
//                    if (getIntent().getStringExtra("appkey")==null) {
//                        singleConversation1 = Conversation.createSingleConversation(getIntent().getStringExtra("username"));
//                    } else {
                    singleConversation1 = Conversation.createSingleConversation(getIntent().getStringExtra("username"), "3a23b8ad0545565bb6d71ecc");
//                    }

                    Log.i(TAG, "gotResult: 31233111" + singleConversation1);
                    if (singleConversation1 != null) {
                        count = singleConversation1.getAllMessage().size();
                        Log.i(TAG, "gotResult: 313113" + count);
                        List<Message> messagesFromOldest1 = singleConversation1.getMessagesFromOldest(count - 12, 12);
                        for (Message msg : messagesFromOldest1) {
                            Log.i(TAG, "onEvent111:312313 " + msg);
                            boolean username = msg.getFromUser().getUserName().equals(String.valueOf(SharePreferenceUtil.getUser("username", "String")));
                            if (username) {
                                shopServiceConversationAdapter.addData(ConversationEntity.client(msg));
                            } else {
                                shopServiceConversationAdapter.addData(ConversationEntity.service(msg));
                            }
                        }
                        tvSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i(TAG, "onClick: ");
                                if (!etInput.getText().toString().isEmpty()) {
                                    if (NoFastClickUtils.isFastClick()) {
                                        ToastUtils.show("不要发送太快哦！");
                                    } else {
                                        if (singleConversation1 != null) {
//                                            if (getIntent().getStringExtra("appkey").isEmpty()) {
//                                                message = singleConversation1.createSendMessage(new TextContent(etInput.getText().toString()));
//                                            } else {
                                            message = singleConversation1.createSendMessage(new TextContent(etInput.getText().toString()), "3a23b8ad0545565bb6d71ecc");
//                                            }
                                            if (message != null) {
                                                JMessageClient.sendMessage(message);
                                                Log.i(TAG, "onClick: ");
                                                message.setOnSendCompleteCallback(new BasicCallback() {
                                                    @Override
                                                    public void gotResult(int responseCode, String responseDesc) {
                                                        Log.i(TAG, "gotResult: " + responseDesc + responseCode);
                                                        if (responseCode == 0) {
                                                            shopServiceConversationAdapter.addData(ConversationEntity.client(message));
                                                            //消息发送成功
                                                            msgRecycleView.scrollToPosition(shopServiceConversationAdapter.getItemCount() - 1);
                                                            Log.i(TAG, "gotResult: 发送成功");
                                                            etInput.setText("");
                                                        } else {
                                                            Log.i(TAG, "gotResult: 消息发送失败");
                                                            //消息发送失败
                                                        }

                                                    }
                                                });
                                            }
                                        }

                                    }

                                }
                            }
                        });
                    }
                    tvSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (NoFastClickUtils.isFastClick()) {
                                ToastUtils.show("不要发送太快哦！");
                            } else {
                                if (singleConversation1 != null) {
                                    final Message message = singleConversation1.createSendMessage(new TextContent(etInput.getText().toString()), "3a23b8ad0545565bb6d71ecc");
                                    JMessageClient.sendMessage(message);
                                    Log.i(TAG, "onClick: ");
                                    message.setOnSendCompleteCallback(new BasicCallback() {
                                        @Override
                                        public void gotResult(int responseCode, String responseDesc) {
                                            Log.i(TAG, "gotResult: " + responseDesc + responseCode);
                                            if (responseCode == 0) {
                                                shopServiceConversationAdapter.addData(ConversationEntity.client(message));
                                                //消息发送成功
                                                msgRecycleView.scrollToPosition(shopServiceConversationAdapter.getItemCount() - 1);
                                                Log.i(TAG, "gotResult: 发送成功");
                                            } else {
                                                Log.i(TAG, "gotResult: 消息发送失败");
                                                //消息发送失败
                                            }
                                            etInput.setText("");
                                        }
                                    });

                                }

                            }
                        }
                    });
                } else {
                    mhideDialog();
                    ToastUtils.show("登陆失败");
                }
            }
        });

    }


    @Override
    public void initData() {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private static final int REQUEST_LIST_CODE = 0x1;
    private static final int REQUEST_CAMERA_CODE = 0x2;

    @OnClick({R2.id.ll_takePhoto, R2.id.ll_picture, R2.id.ll_video, R2.id.ll_goods})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.ll_takePhoto) {
            ISCameraConfig config = new ISCameraConfig.Builder()
                    .cropSize(0, 0, 120, 120)
                    .build();
            ISNav.getInstance().toCameraActivity(this, config, REQUEST_CAMERA_CODE);
        } else if (i == R.id.ll_picture) {
            ISListConfig config = new ISListConfig.Builder()
                    .multiSelect(false)
                    .rememberSelected(false)
                    .btnBgColor(Color.GRAY)
                    .btnTextColor(Color.BLUE)
                    .statusBarColor(Color.parseColor("#ffffff"))
                    .backResId(R.mipmap.ic_back)
                    .title("发送图片")
                    .titleColor(Color.BLACK)
                    .titleBgColor(Color.parseColor("#ffffff"))
                    .cropSize(1, 1, 200, 200)
                    .needCrop(true)
                    .needCamera(true)
                    .maxNum(1)
                    .build();
            ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
        } else if (i == R.id.ll_video) {
            ARouter.getInstance().build(RouterUtil.ALLORDER).navigation();
        } else if (i == R.id.ll_goods) {
            ARouter.getInstance().build(RouterUtil.KIIND).navigation();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
//            pathList.add(data.getStringArrayListExtra("result").get(0));
//            GlideUtil.loadRoundCornerImage(pathList.get(0), ivAvatar);
            try {
                Message singleImageMessage = JMessageClient.createSingleImageMessage(getIntent().getStringExtra("username"), "3a23b8ad0545565bb6d71ecc", new File(data.getStringArrayListExtra("result").get(0)));
                boolean username = singleImageMessage.getFromUser().getUserName().equals(String.valueOf(SharePreferenceUtil.getUser("username", "String")));
                if (username) {
                    shopServiceConversationAdapter.addData(ConversationEntity.client(singleImageMessage));
                } else {
                    shopServiceConversationAdapter.addData(ConversationEntity.service(singleImageMessage));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "onActivityResult: REQUEST_LIST_CODE"+data.getStringArrayListExtra("result").get(0));
        }else if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            Log.i(TAG, "onActivityResult: REQUEST_CAMERA_CODE"+data.getStringExtra("result"));
            try {
                Message singleImageMessage = JMessageClient.createSingleImageMessage(getIntent().getStringExtra("username"), "3a23b8ad0545565bb6d71ecc", new File(data.getStringExtra("result")));
                boolean username = singleImageMessage.getFromUser().getUserName().equals(String.valueOf(SharePreferenceUtil.getUser("username", "String")));
                if (username) {
                    shopServiceConversationAdapter.addData(ConversationEntity.client(singleImageMessage));
                } else {
                    shopServiceConversationAdapter.addData(ConversationEntity.service(singleImageMessage));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
