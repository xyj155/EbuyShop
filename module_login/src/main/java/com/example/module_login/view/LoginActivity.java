package com.example.module_login.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.BrowserActivity;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.interfaces.UserLoginInterface;
import com.example.commonlib.util.CustomVideoView;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.util.ThirdLoginUtil;
import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.UserContract;
import com.example.module_login.presenter.UserPresenter;
import com.qq.e.comm.util.Md5Util;
import com.qq.e.comm.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;


@Route(path = RouterUtil.LOGIN)
public class LoginActivity extends BaseActivity<UserContract.View, UserPresenter> implements UserContract.View {
    public static LoginActivity loginActivity;

    @BindView(R2.id.tv_login)
    TextView tvLogin;
    @BindView(R2.id.tv_register)
    TextView tvRegister;
    @BindView(R2.id.iv_qq)
    ImageView ivQq;
    @BindView(R2.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R2.id.iv_sina)
    ImageView ivSina;
    @BindView(R2.id.ll_login)
    LinearLayout llLogin;
    @BindView(R2.id.et_username)
    EditText etUsername;
    @BindView(R2.id.et_password)
    EditText etPassword;
    @BindView(R2.id.tv_forget)
    TextView tvForget;
    @BindView(R2.id.video_login)
    CustomVideoView videoLogin;

    private void initVideo() {
        //设置播放加载路径
        videoLogin.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login_video));
        //播放
        videoLogin.start();
        //循环播放
        videoLogin.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoLogin.start();
            }
        });
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        initVideo();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        videoLogin.stopPlayback();
        super.onStop();
    }

    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    public UserPresenter getPresenter() {
        return new UserPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initVideo();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        loginActivity = this;
    }

    @OnClick({R2.id.tv_forget, R2.id.tv_register, R2.id.tv_login, R2.id.iv_qq, R2.id.iv_wechat, R2.id.iv_sina})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_qq) {
            Log.i(TAG, "onViewClicked: ");
            ThirdLoginUtil.ThirdLogin(QQ.NAME, new UserLoginInterface() {
                @Override
                public void successWithUser(final PlatformDb platform) {
                    Log.i(TAG, "successWithUser: " + platform.getUserId());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Map<String, Object> map = new HashMap<>();
                            map.put("username", platform.getUserName());
                            map.put("password", platform.getUserId());
                            map.put("avatar", platform.getUserIcon());
                            SharePreferenceUtil.saveUser(map);
                            Log.i(TAG, "successWithUser: " + platform.getUserId());
                            mPresenter.userLogin(platform.getUserName(), platform.getUserId());
                        }
                    });

                }

                @Override
                public void failed() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "QQ登陆失败！", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        } else if (id == R.id.tv_login) {
            if (!StringUtil.isEmpty(etUsername.getText().toString()) && !StringUtil.isEmpty(etPassword.getText().toString())) {
                String encode = Md5Util.encode(etPassword.getText().toString());
                Log.i(TAG, "onViewClicked: " + encode);
                mPresenter.userLogin(etUsername.getText().toString(), encode);
            } else {
                Toast.makeText(this, "输入不可为空！", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.iv_wechat) {

        } else if (id == R.id.iv_sina) {
            ThirdLoginUtil.ThirdLogin(SinaWeibo.NAME, new UserLoginInterface() {
                @Override
                public void successWithUser(PlatformDb platform) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("username", platform.getUserName());
                    map.put("password", platform.getUserId());
                    map.put("avatar", platform.getUserIcon());
                    SharePreferenceUtil.saveUser(map);
                    Log.i(TAG, "successWithUser: " + platform.getUserId());
                    Log.i(TAG, "successWithUser: " + platform.getTokenSecret());
                    mPresenter.userLogin(platform.getUserId(), platform.getUserId());
                }

                @Override
                public void failed() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "微博登陆失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        } else if (id == R.id.tv_register) {
            startActivityForResult(new Intent(LoginActivity.this, TelPhoneRegisterActivity.class), LOGIN_CODE);
        } else if (id == R.id.tv_forget) {
            Intent intent = new Intent(LoginActivity.this, BrowserActivity.class);
            intent.putExtra("url", RetrofitUtils.BASE_URL + "/StuShop/public/index.php/index/index/confirmTel");
            startActivity(intent);
        }
    }

    public static final int LOGIN_CODE = 1;

    @Override
    public void userLogin(UserGson userGson) {
        ARouter.getInstance().build(RouterUtil.HomePage).navigation();
        Map<String, Object> map = new HashMap<>();
        map.put("username", userGson.getUsername());
        map.put("password", userGson.getPassword());
        map.put("avatar", userGson.getAvatar());
        map.put("telphone", userGson.getTelphone());
        map.put("uid", String.valueOf(userGson.getId()));
        map.put("userToken", String.valueOf(userGson.getUserToken()));
        map.put("islogin", true);
        map.put("isOpenSound", true);
        map.put("member", userGson.getVipRank());
        map.put("imToken", userGson.getImToken());
        SharePreferenceUtil.saveUser(map);
        finish();
    }

    @Override
    public void isHaving(boolean isHaving) {

    }


    @Override
    public void showError(String msg) {
        Log.i(TAG, "showError: " + msg);
        Toast.makeText(this, "登陆出错：" + msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showDialog(String msg) {
        createDialog("");
    }

    @Override
    public void hideDialog() {
        mhideDialog();
    }

}
