package com.example.module_login.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.SchoolGson;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.CircleImageView;
import com.example.commonlib.view.ListDialog;
import com.example.commonlib.view.SideIndexBar;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.UserRegisterContract;
import com.example.module_login.presenter.UserRegisterPresenter;
import com.google.gson.Gson;
import com.qq.e.comm.util.Md5Util;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterActivity extends BaseActivity<UserRegisterContract.View, UserRegisterPresenter> implements UserRegisterContract.View {


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
    ListDialog.Builder builder = new ListDialog.Builder(this);
    @BindView(R2.id.tv_password)
    EditText tvPassword;
    private ListDialog dialogSchool;
    private List<String> pathList = new ArrayList<>();
    private SchoolAdapter schoolAdapter = new SchoolAdapter(null);
    private List<SchoolGson.RECORDSBean> records = new ArrayList<>();

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserRegisterPresenter getPresenter() {
        return new UserRegisterPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {

        initToolBar().setToolBarTitle("用户信息");
        List<String> ageList = new ArrayList<>();
        for (int i = 18; i < 25; i++) {
            ageList.add(i + "岁");
        }
        ageAdapter.replaceData(ageList);
    }

    private void initSchoolList() {
        String json = getJson("school.json", RegisterActivity.this);
        Gson gson = new Gson();
        SchoolGson schoolGson = gson.fromJson(json, SchoolGson.class);
        records = schoolGson.getRECORDS();
        Collections.sort(records, new Comparator<SchoolGson.RECORDSBean>() {
            @Override
            public int compare(SchoolGson.RECORDSBean o1, SchoolGson.RECORDSBean o2) {
                Comparator<Object> com = Collator.getInstance(Locale.CHINA);
                return com.compare(o1.getName(), o2.getName());

            }
        });
        schoolAdapter.replaceData(records);
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        initSchoolList();
        View inflate = View.inflate(RegisterActivity.this, R.layout.dialog_school_choose_layout, null);
        dialogSchool = builder.cancelTouchout(false)
                .view(inflate)
                .build();
        final RecyclerView viewById = inflate.findViewById(R.id.ry_list);
        EditText etSchool = inflate.findViewById(R.id.et_school);
        viewById.setLayoutManager(new LinearLayoutManager(RegisterActivity.this));

        SideIndexBar indexBar = (SideIndexBar) inflate.findViewById(R.id.index_bar);
        indexBar.setLetters("ABCDEFHIJKLMOPQSTUVXYZ#");
        indexBar.setTextDialog((TextView) inflate.findViewById(R.id.text_dialog));
        etSchool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                initSchoolList();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    initSchoolList();
                } else {
                    for (int i = 0; i < schoolAdapter.getData().size(); i++) {
                        if (s.toString().equals(schoolAdapter.getData().get(i).getName())) {
                            records.clear();
                            Log.i(TAG, "onTextChanged: " + schoolAdapter.getData().get(i).getName());
                            SchoolGson.RECORDSBean recordsBean = new SchoolGson.RECORDSBean();
                            recordsBean.setName(s.toString());
                            records.add(recordsBean);
                            schoolAdapter.replaceData(records);
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        indexBar.setOnLetterChangedListener(new SideIndexBar.OnLetterChangedListener() {
            @Override
            public void onChanged(String s, int position) {
                Log.e("SideIndexBar", s + " position:" + position);
            }
        });
        viewById.setAdapter(schoolAdapter);


        String user = (String) SharePreferenceUtil.getUser("username", "String");
        tvUsername.setText(user);
    }


    private ListDialog dialogAge;

    @OnClick({R2.id.iv_avatar, R2.id.tv_age, R2.id.tv_collage, R2.id.tv_login})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.iv_avatar) {
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
        } else if (id == R.id.tv_age) {
            View inflate = View.inflate(RegisterActivity.this, R.layout.dialog_age_list_layout, null);
            dialogAge = builder.cancelTouchout(false)
                    .view(inflate)
                    .build();
            RecyclerView viewById = inflate.findViewById(R.id.ry_list);
            viewById.setLayoutManager(new LinearLayoutManager(RegisterActivity.this));
            viewById.setAdapter(ageAdapter);
            dialogAge.show();
        } else if (id == R.id.tv_collage) {
            dialogSchool.show();
        } else if (id == R.id.tv_login) {
            if (tvPassword.getText().toString().isEmpty()) {
                ToastUtils.show("请输入密码");
            } else if (tvPassword.getText().toString().length() < 10) {
                ToastUtils.show("密码不可少于六位");
            } else {

                    int size = pathList.size();
                    if (rbBoy.isChecked() || rbGirl.isChecked()) {
                        if (size > 0) {
                            if (!isFastClick()){
                                String s = pathList.get(0);
                                if (!s.isEmpty()) {
                                    File file = new File(s);
                                    RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
                                    MultipartBody.Part avatar = MultipartBody.Part.createFormData("avatar", file.getName(), fileRQ);
                                    mPresenter.userRegister(tvUsername.getText().toString(), Md5Util.encode(tvPassword.getText().toString()), String.valueOf(SharePreferenceUtil.getUser("telphone", "String")), tvAge.getText().toString(), rbBoy.isChecked() ? "男" : "女", tvCollage.getText().toString(), avatar);
                                }
                            }
                        } else {
                            ToastUtils.show("你还没有选择头像");
                        }
                    } else {
                        ToastUtils.show("请选择你的性别");
                    }

            }
        }


    }
    private static final int MIN_DELAY_TIME= 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private AgeAdapter ageAdapter = new AgeAdapter(null);

    public class AgeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public AgeAdapter(@Nullable List<String> data) {
            super(R.layout.dialog_age_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final String item) {
            helper.setText(R.id.tv_age, item)
                    .setOnClickListener(R.id.tv_age, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvAge.setText(item);
                            dialogAge.dismiss();
                        }
                    });
        }
    }

    public class SchoolAdapter extends BaseQuickAdapter<SchoolGson.RECORDSBean, BaseViewHolder> {

        public SchoolAdapter(@Nullable List<SchoolGson.RECORDSBean> data) {
            super(R.layout.dialog_age_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final SchoolGson.RECORDSBean item) {
            helper.setText(R.id.tv_age, item.getName())
                    .setOnClickListener(R.id.tv_age, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvCollage.setText(item.getName());
                            dialogSchool.dismiss();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            pathList.add(data.getStringArrayListExtra("result").get(0));
            GlideUtil.loadRoundCornerImage(pathList.get(0), ivAvatar);
        }
    }


    @Override
    public void registerSuccess(final UserGson emptyGson) {
        createDialog("");
        Log.i(TAG, "registerSuccess: " + emptyGson.toString());
        Map<String, Object> map = new HashMap<>();
        map.put("username", emptyGson.getUsername());
        map.put("password", emptyGson.getPassword());
        map.put("uid", String.valueOf(emptyGson.getId()));
        map.put("avatar", emptyGson.getAvatar());
        map.put("sex", emptyGson.getSex());
        map.put("userToken", String.valueOf(emptyGson.getUserToken()));
        map.put("age", emptyGson.getAge());
        map.put("level", emptyGson.getUserLevel());
        map.put("trueName", emptyGson.getTrueName());
        map.put("imToken", emptyGson.getImToken());
        map.put("islogin", true);
        SharePreferenceUtil.saveUser(map);
        ToastUtils.show("注册成功！");
        ARouter.getInstance().build(RouterUtil.HomePage).navigation();
        TelPhoneRegisterVerifyActivity.telPhoneRegisterVerifyActivity.finish();
        TelPhoneRegisterActivity.telPhoneRegisterActivity.finish();
        LoginActivity.loginActivity.finish();
        finish();

    }

    @Override
    public void registerFailed() {
        ToastUtils.show("注册失败");
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
