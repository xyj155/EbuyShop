package com.example.commonlib.comonactivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.AddressOfNewReceiptContract;
import com.example.commonlib.presenter.AddressOfNewReceiptPresenter;
import com.example.commonlib.view.CityPickerPopWindow;
import com.example.commonlib.view.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressOfNewReceiptActivity extends BaseActivity<AddressOfNewReceiptContract.View, AddressOfNewReceiptPresenter> implements AddressOfNewReceiptContract.View, CityPickerPopWindow.CityPickListener {


    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.et_username)
    EditText etUsername;
    @BindView(R2.id.et_tel)
    EditText etTel;
    @BindView(R2.id.tv_local)
    TextView tvLocal;
    @BindView(R2.id.et_detail)
    EditText etDetail;
    @BindView(R2.id.sw_default)
    SwitchButton swDefault;
    @BindView(R2.id.tv_add_news_address)
    TextView tvAddNewsAddress;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    private boolean isDefault = false;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public AddressOfNewReceiptPresenter getPresenter() {
        return new AddressOfNewReceiptPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_address_of_new_receipt;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        swDefault.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Log.i(TAG, "onCheckedChanged: "+isChecked);
                if (isChecked) {
                    isDefault = true;
                } else {
                    isDefault = false;
                }
            }
        });
    }

    @Override
    public void initData() {
        initToolBar().setToolBarTitle("添加地址");
    }

    @Override
    public void saveNewAddressSuccess(boolean success) {
        if (success) {
            finish();
        } else {
            Toast.makeText(this, "添加失败！", Toast.LENGTH_SHORT).show();
        }
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
        hideDlalog();
    }


    @OnClick({R2.id.tv_local, R2.id.tv_add_news_address})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_local) {
            CityPickerPopWindow mPopWindow = new CityPickerPopWindow(AddressOfNewReceiptActivity.this);
            final WindowManager.LayoutParams lp = getWindow()
                    .getAttributes();
            lp.alpha = 0.4f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(lp);
            mPopWindow.showPopupWindow(llRoot);
            mPopWindow.setCityPickListener(this);
            mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    lp.alpha = 1f;
                    getWindow().setAttributes(lp);
                }
            });
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else if (id == R.id.tv_add_news_address) {
            if (etUsername.getText().toString().isEmpty()||etDetail.getText().toString().isEmpty()||etTel.getText().toString().isEmpty()||tvLocal.getText().toString().isEmpty()){
                Toast.makeText(this, "不可为空哦！", Toast.LENGTH_SHORT).show();
            }else {
                mPresenter.saveNewAddress(etUsername.getText().toString(), etTel.getText().toString(), tvLocal.getText().toString(), etDetail.getText().toString(), isDefault, "1");
            }

        }
    }

    @Override
    public void pickValue(String value) {
        tvLocal.setText(value);
        Log.i(TAG, "pickValue: " + value);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
