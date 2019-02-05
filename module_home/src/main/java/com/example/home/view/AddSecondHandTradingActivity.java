package com.example.home.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.adapter.GridImageAdapter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.SchoolGson;
import com.example.commonlib.gson.SecondHandsGoodsGson;
import com.example.commonlib.util.RxPartMapUtils;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.FullyGridLayoutManager;
import com.example.commonlib.view.ListDialog;
import com.example.commonlib.view.SideIndexBar;
import com.example.commonlib.view.toast.ToastUtils;
import com.example.home.contract.SecondHandTradingMarketContract;
import com.example.home.db.DBHelper;
import com.example.home.presenter.SecondHandTradingMarketPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.yuyh.library.imgsel.ui.ISListActivity;

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

public class AddSecondHandTradingActivity extends BaseActivity<SecondHandTradingMarketContract.View, SecondHandTradingMarketPresenter> implements SecondHandTradingMarketContract.View {

    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.et_title)
    EditText etTitle;
    @BindView(R2.id.tv_school)
    TextView tvSchool;
    @BindView(R2.id.et_content)
    EditText etContent;
    @BindView(R2.id.ry_second)
    RecyclerView ryPicture;
    @BindView(R2.id.et_price)
    EditText et_price;
    @BindView(R2.id.tv_post_free)
    TextView tvPostFree;
    @BindView(R2.id.et_post_free)
    EditText etPostFree;
    @BindView(R2.id.rb_free_shipping)
    RadioButton rbFreeShipping;
    @BindView(R2.id.rb_same_city)
    RadioButton rbSameCity;
    @BindView(R2.id.tv_submit)
    TextView tvSubmit;
    @BindView(R2.id.tv_kind)
    TextView tvKind;
    @BindView(R2.id.rg_post_method)
    RadioGroup rgPostMethod;
    private GridImageAdapter pictureAddedAdapter;
    private List<Object> pictureList = new ArrayList<>();
    private static final int REQUEST_LIST_CODE = 0x1;
    //    private List<SchoolGson.RECORDSBean> records = new ArrayList<>();
    private SchoolAdapter schoolAdapter = new SchoolAdapter(null);
    private ListDialog dialogSchool;
    ListDialog.Builder builder = new ListDialog.Builder(this);
    DBHelper dbHelper;
    ArrayList<SchoolGson.RECORDSBean> list = new ArrayList<>();

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
                            tvSchool.setText("学校：" + item.getName());
                            dialogSchool.dismiss();
                        }
                    });
        }
    }


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    private void queryData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    dbHelper.createDataBase();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("select * from school", null);
                    SchoolGson.RECORDSBean city;
                    while (cursor.moveToNext()) {
                        city = new SchoolGson.RECORDSBean();
                        city.setName(cursor.getString(0));
                        Log.i(TAG, "queryData: " + cursor.getString(0));
                        list.add(city);
                    }
                    cursor.close();
                    db.close();
                    Collections.sort(list, new Comparator<SchoolGson.RECORDSBean>() {
                        @Override
                        public int compare(SchoolGson.RECORDSBean o1, SchoolGson.RECORDSBean o2) {
                            Comparator<Object> com = Collator.getInstance(Locale.CHINA);
                            return com.compare(o1.getName(), o2.getName());

                        }
                    });
                    schoolAdapter.replaceData(list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1500);

    }

    @Override
    public SecondHandTradingMarketPresenter getPresenter() {
        return new SecondHandTradingMarketPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_add_second_hand_trading;
    }

    private EditText etSchool;

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("发布闲置");
        dbHelper = new DBHelper(AddSecondHandTradingActivity.this);
        queryData();
        final ISListConfig config = new ISListConfig.Builder()
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
        FullyGridLayoutManager layout = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        ryPicture.setLayoutManager(layout);
        pictureAddedAdapter = new GridImageAdapter(AddSecondHandTradingActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                if (pictureList.size() < 9) {
                    Intent intent = new Intent(AddSecondHandTradingActivity.this, ISListActivity.class);
                    intent.putExtra("config", config);
                    startActivityForResult(intent, REQUEST_LIST_CODE);
                }
            }
        });
        pictureAddedAdapter.setList(pictureList);
        ryPicture.setAdapter(pictureAddedAdapter);
        View inflate = View.inflate(AddSecondHandTradingActivity.this, R.layout.dialog_school_choose_layout, null);
        dialogSchool = builder.cancelTouchout(false)
                .view(inflate)
                .build();
        final RecyclerView viewById = inflate.findViewById(R.id.ry_list);
        etSchool = inflate.findViewById(R.id.et_school);
        viewById.setLayoutManager(new LinearLayoutManager(AddSecondHandTradingActivity.this));

        SideIndexBar indexBar = (SideIndexBar) inflate.findViewById(R.id.index_bar);
        indexBar.setLetters("ABCDEFHIJKLMOPQSTUVXYZ#");
        indexBar.setTextDialog((TextView) inflate.findViewById(R.id.text_dialog));

        etSchool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                search();
            }
        });
        indexBar.setOnLetterChangedListener(new SideIndexBar.OnLetterChangedListener() {
            @Override
            public void onChanged(String s, int position) {
                Log.e("SideIndexBar", s + " position:" + position);
            }
        });
        viewById.setAdapter(schoolAdapter);
        rgPostMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_free_shipping) {
                    method = "包邮";
                    etPostFree.setText("");
                } else if (checkedId == R.id.rb_same_city) {
                    method = "同校交易";
                    etPostFree.setText("");
                }
            }
        });
    }

    private String method = "不包邮";

    @Override
    public void initData() {

    }

    private void search() {
        String text = etSchool.getText().toString();
        if (!text.equals("")) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from school where name like '%" + text + "%'", null);
            list.clear();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                SchoolGson.RECORDSBean recordsBean = new SchoolGson.RECORDSBean();
                recordsBean.setName(name);
                list.add(recordsBean);
            }
            schoolAdapter.replaceData(list);
            schoolAdapter.notifyDataSetChanged();
            db.close();
            cursor.close();
        } else {
            queryData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            pictureList.addAll(data.getStringArrayListExtra("result"));
            pictureAddedAdapter.setList(pictureList);
            pictureAddedAdapter.notifyDataSetChanged();
        } else if (requestCode == SecondHandsKindActivity.CODE) {
            if (data != null)
                tvKind.setText(data.getStringExtra("name") + "    ");
        }
    }


    @Override
    public void uploadSuccess(boolean success) {
        if (success) {
            ToastUtils.show("发布成功，等待审核");
            finish();
        } else {
            ToastUtils.show("发布失败");
        }
    }

    @Override
    public void queryAllSecondGoods(List<SecondHandsGoodsGson> secondHandsGoodsGsons) {

    }

    @Override
    public void loadMoreData(List<SecondHandsGoodsGson> secondHandsGoodsGsons) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog("");
    }

    @Override
    public void hideDialog() {
        mhideDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.tv_submit, R2.id.tv_school, R2.id.tv_kind})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_school) {
            dialogSchool.show();
        } else if (id == R.id.tv_submit) {
            if (etTitle.getText().toString().isEmpty() ||
                    etContent.getText().toString().isEmpty() ||
                    et_price.getText().toString().isEmpty() ||
                    tvSchool.getText().toString().isEmpty()) {
                ToastUtils.show("输入不可为空哦！");
            } else if (pictureList.size() == 0) {
                ToastUtils.show("你是不是Show一下商品的样子啊！");
            } else {
                final Map<String, RequestBody> partMap = new HashMap<>();
                partMap.put("goodsName", RxPartMapUtils.toRequestBodyOfText(etTitle.getText().toString()));
                partMap.put("userId", RxPartMapUtils.toRequestBodyOfText(String.valueOf(SharePreferenceUtil.getUser("uid", "String"))));
                partMap.put("userSchool", RxPartMapUtils.toRequestBodyOfText(tvSchool.getText().toString()));
                partMap.put("goodsReason", RxPartMapUtils.toRequestBodyOfText(etContent.getText().toString()));
                partMap.put("goodsPrice", RxPartMapUtils.toRequestBodyOfText(et_price.getText().toString()));
                partMap.put("postMoney", RxPartMapUtils.toRequestBodyOfText(etPostFree.getText().toString().isEmpty() ? "0.00" : etPostFree.getText().toString()));
                partMap.put("postMethod", RxPartMapUtils.toRequestBodyOfText(method));
                partMap.put("postKind", RxPartMapUtils.toRequestBodyOfText(tvKind.getText().toString()));
                final List<MultipartBody.Part> Imagelist = new ArrayList<>();
                for (Object media : pictureList) {
                    if (!media.equals(R.drawable.ic_product_evaluating_add)) {
                        File file = new File(media.toString());
                        Log.i(TAG, "onViewClicked: " + file.getName());
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part formData = MultipartBody.Part.createFormData("goodsPic[]", file.getName(), requestBody);
                        Imagelist.add(formData);
                    }
                }
                mPresenter.addSecondHanding(partMap, Imagelist);
            }
        } else if (id == R.id.tv_kind) {
            startActivityForResult(new Intent(AddSecondHandTradingActivity.this, SecondHandsKindActivity.class), SecondHandsKindActivity.CODE);
        }

    }
}
