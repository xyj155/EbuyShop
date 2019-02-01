package com.example.user.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.util.RxPartMapUtils;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.FullyGridLayoutManager;
import com.example.commonlib.view.ListDialog;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.user.adapter.GridImageAdapter;
import com.example.user.contract.UserFeedBackContract;
import com.example.user.presenter.UserFeedBackPresenter;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.yuyh.library.imgsel.ui.ISListActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserFeedBackActivity extends BaseActivity<UserFeedBackContract.View, UserFeedBackPresenter> implements UserFeedBackContract.View {
    private static final int REQUEST_LIST_CODE = 0x1;
    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.ry_picture)
    RecyclerView ryPicture;
    @BindView(R2.id.tv_content)
    EditText tvContent;
    @BindView(R2.id.tv_submit)
    TextView tvSubmit;
    @BindView(R2.id.tv_choose)
    TextView tvChoose;
    private List<Object> pictureList = new ArrayList<>();

    private GridImageAdapter pictureAddedAdapter;
    ListDialog.Builder builder = new ListDialog.Builder(this);
    private ListDialog dialogSchool;
    private FeedBackChooseListAdapter feedBackChooseListAdapter = new FeedBackChooseListAdapter(null);

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserFeedBackPresenter getPresenter() {
        return new UserFeedBackPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_feed_back;
    }

    private ISListConfig config;

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("问题反馈");
        FullyGridLayoutManager layout = new FullyGridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        ryPicture.setLayoutManager(layout);
        pictureAddedAdapter = new GridImageAdapter(UserFeedBackActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                if (pictureList.size() < 9) {
                    Intent intent = new Intent(UserFeedBackActivity.this, ISListActivity.class);
                    intent.putExtra("config", config);
                    startActivityForResult(intent, REQUEST_LIST_CODE);
                }
            }
        });
        pictureAddedAdapter.setList(pictureList);
        ryPicture.setAdapter(pictureAddedAdapter);
        config = new ISListConfig.Builder()
                .multiSelect(true)
                .rememberSelected(false)
                .btnBgColor(Color.WHITE)
                .btnTextColor(Color.BLACK)
                .statusBarColor(Color.parseColor("#ffffff"))
                .backResId(R.mipmap.ic_back)
                .title("图片选择")
                .titleColor(Color.BLACK)
                .titleBgColor(Color.parseColor("#ffffff"))
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                .needCamera(true)
                .maxNum(9)
                .build();
        View inflate = View.inflate(UserFeedBackActivity.this, R.layout.dialog_feedback_list_layout, null);
        dialogSchool = builder.cancelTouchout(false)
                .view(inflate)
                .build();
        RecyclerView viewById = inflate.findViewById(R.id.ry_feedback);
        viewById.setLayoutManager(new LinearLayoutManager(UserFeedBackActivity.this));
        List<String> list = new ArrayList<>();
        list.add("商品/订单/物流/客服");
        list.add("商品评论");
        list.add("假货举报");
        list.add("功能建议");
        list.add("APP故障");
        list.add("其他");
        feedBackChooseListAdapter.replaceData(list);
        viewById.setAdapter(feedBackChooseListAdapter);
    }

    @Override
    public void initData() {
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
    }


    @OnClick({R2.id.tv_submit, R2.id.tv_choose})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_submit) {
            if (tvContent.getText().toString().isEmpty()) {
                ToastUtils.show("请填写反馈内容");
            } else if (!isChoose) {
                ToastUtils.show("请选择反馈类型");
            } else if (pictureList.size() < 1) {
                ToastUtils.show("请上传反馈图片");
            } else {
                final Map<String, RequestBody> partMap = new HashMap<>();
                partMap.put("userId", RxPartMapUtils.toRequestBodyOfText(String.valueOf(SharePreferenceUtil.getUser("uid", "String"))));
                partMap.put("type", RxPartMapUtils.toRequestBodyOfText("嘉兴学院"));
                partMap.put("content", RxPartMapUtils.toRequestBodyOfText(tvContent.getText().toString()));
                partMap.put("ime", RxPartMapUtils.toRequestBodyOfText("小米"));
                final List<MultipartBody.Part> Imagelist = new ArrayList<>();
                for (Object media : pictureList) {
                    if (!media.equals(R.drawable.ic_product_evaluating_add)) {
                        File file = new File(media.toString());
                        Log.i(TAG, "onViewClicked: " + file.getName());
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part formData = MultipartBody.Part.createFormData("image[]", file.getName(), requestBody);
                        Imagelist.add(formData);
                    }
                }
                mPresenter.userFeedBack(partMap, Imagelist);
            }
        } else if (id == R.id.tv_choose) {
            dialogSchool.show();
        }

    }

    @Override
    public void uploadSuccess(boolean isSuccess) {
        if (isSuccess) {
            ToastUtils.show("反馈成功");
            finish();
        } else {
            ToastUtils.show("上传失败");
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
        mhideDialog();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            pictureList.addAll(data.getStringArrayListExtra("result"));
            pictureAddedAdapter.setList(pictureList);
            pictureAddedAdapter.notifyDataSetChanged();
        }
    }

    private boolean isChoose = false;

    private class FeedBackChooseListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public FeedBackChooseListAdapter(@Nullable List<String> data) {
            super(R.layout.ry_feedback_choose_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final String item) {
            helper.setText(R.id.tv_kind, item)
                    .setOnClickListener(R.id.tv_kind, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isChoose = true;
                            tvChoose.setText(item);
                            dialogSchool.dismiss();
                        }
                    });
        }
    }
}
