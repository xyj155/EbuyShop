package com.example.module_login.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.view.CircleImageView;
import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.UserContract;
import com.example.module_login.presenter.UserPresenter;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<UserContract.View, UserPresenter> implements UserContract.View {


    private static final int REQUEST_LIST_CODE = 0x1;
    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R2.id.tv_username)
    EditText tvUsername;
    @BindView(R2.id.rb_boy)
    RadioButton rbBoy;
    @BindView(R2.id.rb_girl)
    RadioButton rbGirl;
    @BindView(R2.id.tv_age)
    TextView tvAge;
    @BindView(R2.id.tv_collage)
    TextView tvCollage;
    @BindView(R2.id.tv_login)
    TextView tvLogin;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserPresenter getPresenter() {
        return new UserPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void userLogin(UserGson userGson) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        // 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
    }

    @OnClick({R2.id.iv_avatar, R2.id.tv_age, R2.id.tv_collage, R2.id.tv_login})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R2.id.iv_avatar) {
// 自由配置选项
            ISListConfig config = new ISListConfig.Builder()
                    // 是否多选, 默认true
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
            ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
        } else if (id == R.id.tv_age) {

        } else if (id == R.id.tv_collage) {

        } else if (id == R.id.tv_login) {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            GlideUtil.loadRoundCornerImage(pathList.get(0), ivAvatar);


        }
    }

    @OnClick(R2.id.tv_login)
    public void onViewClicked() {
    }


}
