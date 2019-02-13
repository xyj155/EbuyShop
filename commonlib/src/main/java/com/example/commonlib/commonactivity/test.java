package com.example.commonlib.commonactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.view.toast.ToastUtils;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class test extends AppCompatActivity {

    @BindView(R2.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R2.id.btn_register)
    Button btnRegister;
    private static final String TAG = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }
    private static final int REQUEST_LIST_CODE = 0x1;
    @OnClick({R2.id.iv_avatar, R2.id.btn_register})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_avatar) {
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
            ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);

        } else if (i == R.id.btn_register) {
            JMessageClient.login("123456", "123456", new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        JMessageClient.updateUserAvatar(new File(pathList.get(0)), new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                Log.i(TAG, "gotResult: "+i+s);
                                if (i == 0) {
                                    ToastUtils.show("更新成功");
                                }else {
                                    ToastUtils.show("更新失败");
                                }
                            }
                        });
                    }
                }
            });
        }
    }
    private List<String> pathList = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            pathList.add(data.getStringArrayListExtra("result").get(0));
            GlideUtil.loadRoundCornerImage(pathList.get(0), ivAvatar);
        }
    }

}
