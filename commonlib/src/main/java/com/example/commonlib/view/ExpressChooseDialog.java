package com.example.commonlib.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.contract.ExpressContract;
import com.example.commonlib.gson.ExpressGson;
import com.example.commonlib.presenter.ExpressPresenter;

import java.util.ArrayList;
import java.util.List;

public class ExpressChooseDialog extends Dialog implements ExpressContract.View {

    ImageView ivClose;
    TextView tvSubmit;
    RecyclerView ryExpress;
    //在构造方法里提前加载了样式
    private Context context;//上下文
    private ExpressPresenter goodsStylePresenter = new ExpressPresenter(this);
    private ExpressChooseAdapter goodsStyleAdapter = new ExpressChooseAdapter(null);


    public ExpressChooseDialog(Context context) {
        super(context, R.style.BottomDialogStyle);//加载dialog的样式
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        setContentView(R.layout.abc_express_choose_list_layout);
        ivClose = findViewById(R.id.iv_close);
        tvSubmit = findViewById(R.id.tv_submit);
        ryExpress = findViewById(R.id.ry_express);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        //遍历控件id添加点击注册
        goodsStylePresenter.queryExpress();
        ryExpress.setLayoutManager(new LinearLayoutManager(context));
        ryExpress.setAdapter(goodsStyleAdapter);
        goodsStyleAdapter.setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onClickListener(String price, String expressname) {
                expressPrice = price;
                expressName = expressname;
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListenerByGId.onClickListener(expressPrice, expressName);
            }
        });
    }

    private String expressName;
    private String expressPrice;
    private static final String TAG = "ExpressChooseDialog";

    @Override
    public void loadExpress(List<ExpressGson> list) {
        goodsStyleAdapter.replaceData(list);

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


    private class ExpressChooseAdapter extends BaseQuickAdapter<ExpressGson, BaseViewHolder> {
        private List<Integer> integerList = new ArrayList<>();
        private ExpressChooseDialog.onItemClickListener onItemClickListener;

        public ExpressChooseAdapter(@Nullable List<ExpressGson> data) {
            super(R.layout.dialog_express_choose_item, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final ExpressGson item) {
            helper.setText(R.id.tv_express, item.getExpressName())
                    .setText(R.id.tv_express_price, "￥" + item.getExpressPrice())
                    .setOnClickListener(R.id.cb_express, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (integerList.contains(helper.getPosition())) {
                                helper.setChecked(R.id.cb_express, false);
                            } else {
                                integerList.clear();
                                integerList.add(helper.getPosition());
                                helper.setChecked(R.id.cb_express, true);
                                onItemClickListener.onClickListener(item.getExpressPrice(),item.getExpressName());
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                }
                            }, 40);
                        }
                    });
            if (integerList.contains(helper.getPosition())) {
                helper.setChecked(R.id.cb_express, true);
            } else {
                helper.setChecked(R.id.cb_express, false);
            }

        }

        public void setOnItemClickListener(ExpressChooseDialog.onItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }

    private onItemClickListener onItemClickListenerByGId;

    public void setOnItemClickListener(onItemClickListener onItemClickListenerByGId) {
        this.onItemClickListenerByGId = onItemClickListenerByGId;
    }

    public interface onItemClickListener {
        void onClickListener(String price, String expressName);
    }

}
