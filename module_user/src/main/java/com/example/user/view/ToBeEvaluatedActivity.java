package com.example.user.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.util.RxPartMapUtils;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.FullyGridLayoutManager;
import com.example.commonlib.view.MyDialog;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.user.adapter.GridImageAdapter;
import com.example.user.contract.ToBeEvaluatedContract;
import com.example.user.presenter.ToBeEvaluatedPresenter;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ToBeEvaluatedActivity extends BaseActivity<ToBeEvaluatedContract.View, ToBeEvaluatedPresenter> implements ToBeEvaluatedContract.View {

    @BindView(R2.id.ry_picture)
    RecyclerView ryPicture;
    @BindView(R2.id.et_content)
    EditText etContent;
    @BindView(R2.id.rb_goods)
    RadioButton rbGoods;
    @BindView(R2.id.rb_middle)
    RadioButton rbMiddle;
    @BindView(R2.id.rb_low)
    RadioButton rbLow;
    @BindView(R2.id.rg_evaluate)
    RadioGroup rgEvaluate;
    private List<Object> pictureList = new ArrayList<>();
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    private GridImageAdapter gridImageAdapter;
    private ISListConfig config;
    private static final int REQUEST_LIST_CODE = 0x1;
    private String evaluate;
    @BindView(R2.id.iv_close)
    ImageView ivClose;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public ToBeEvaluatedPresenter getPresenter() {
        return new ToBeEvaluatedPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_to_be_evaluated;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        rgEvaluate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_goods) {
                    evaluate = "好评";
                } else if (checkedId == R.id.rb_middle) {
                    evaluate = "中评";
                } else {
                    evaluate = "差评";
                }
            }
        });
        ButterKnife.bind(this);
        ivClose = findViewById(R.id.iv_close);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvMmenu = findViewById(R.id.tv_menu);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog1 = new MyDialog(ToBeEvaluatedActivity.this, new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
                myDialog1.setContent("你的评价还没有完成哦！");
                myDialog1.setTitle("是否退出评价");
                myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
                    @Override
                    public void onCenterItemClick(MyDialog dialog, View view) {
                        int i = view.getId();
                        if (i == R.id.dialog_btn_close) {
                            dialog.dismiss();
                        } else if (i == R.id.dialog_btn_cancel) {
                            finish();
                        }
                    }
                });
                myDialog1.show();
            }
        });
        tvTitle.setText("发表评价");
        tvMmenu.setText("发布");
        tvMmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onMenuClickListener: ");
                if (etContent.getText().toString().isEmpty()) {
                    ToastUtils.show("评价内容你还没有填写哦！");
                } else if (evaluate.isEmpty()) {
                    ToastUtils.show("你还没有选择评价级别哦！");
                } else {
                    final Map<String, RequestBody> partMap = new HashMap<>();
                    partMap.put("userId", RxPartMapUtils.toRequestBodyOfText(String.valueOf(SharePreferenceUtil.getUser("uid", "String"))));
                    partMap.put("goodsId", RxPartMapUtils.toRequestBodyOfText(getIntent().getStringExtra("goodsId")));
                    partMap.put("userComment", RxPartMapUtils.toRequestBodyOfText(etContent.getText().toString()));
                    partMap.put("orderNum", RxPartMapUtils.toRequestBodyOfText(getIntent().getStringExtra("orderNum")));
                    partMap.put("evaluateRank", RxPartMapUtils.toRequestBodyOfText("小米"));
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
                    mPresenter.updateGoodsEvaluate(partMap, Imagelist);
                }

            }
        });
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
                .maxNum(6)
                .build();
        gridImageAdapter = new GridImageAdapter(ToBeEvaluatedActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                if (pictureList.size() < 9) {
                    Intent intent = new Intent(ToBeEvaluatedActivity.this, ISListActivity.class);
                    intent.putExtra("config", config);
                    startActivityForResult(intent, REQUEST_LIST_CODE);
                }
            }
        });
        FullyGridLayoutManager layout = new FullyGridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        ryPicture.setLayoutManager(layout);
        gridImageAdapter.setList(pictureList);
        ryPicture.setAdapter(gridImageAdapter);
    }

    private MyDialog myDialog1;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            myDialog1 = new MyDialog(this, new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
            myDialog1.setContent("你的评价还没有完成哦！");
            myDialog1.setTitle("是否退出评价");
            myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
                @Override
                public void onCenterItemClick(MyDialog dialog, View view) {
                    int i = view.getId();
                    if (i == R.id.dialog_btn_close) {
                        dialog.dismiss();
                    } else if (i == R.id.dialog_btn_cancel) {
                        finish();
                    }
                }
            });
            myDialog1.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            pictureList.addAll(data.getStringArrayListExtra("result"));
            gridImageAdapter.setList(pictureList);
            gridImageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void uploadSuccess(boolean isSuccess) {
        if (isSuccess) {
            ToastUtils.show("评价成功！");
            finish();
        } else {
            ToastUtils.show("评价失败！");
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
}
