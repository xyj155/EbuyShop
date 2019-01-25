package com.example.commonlib.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.commonlib.R;

public class MyDialog extends Dialog implements View.OnClickListener {
    //在构造方法里提前加载了样式
    private Context context;//上下文
    private int[] listenedItem;//监听的控件id
    private TextView vMsg, tvTitle;

    public MyDialog(Context context, int[] listenedItem) {
        super(context, R.style.AppDialog);//加载dialog的样式
        this.context = context;
        this.listenedItem = listenedItem;
    }

    private String msg;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        setContentView(R.layout.common_dialog);

        WindowManager windowManager = ((Activity) context).getWindowManager();


        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 5;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        //遍历控件id添加点击注册
        tvTitle = findViewById(R.id.tv_title);
        vMsg = findViewById(R.id.tv_msg);
        for (int id : listenedItem) {
            findViewById(id).setOnClickListener(this);
        }
        tvTitle.setText(title);
        vMsg.setText(msg);
    }

    public void setTitle(String msg) {
        this.title = msg;
    }

    public void setContent(String msg) {
        this.msg = msg;
    }

    private OnCenterItemClickListener listener;

    public interface OnCenterItemClickListener {
        void onCenterItemClick(MyDialog dialog, View view);
    }

    //很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;

    }

    @Override
    public void onClick(View v) {
        dismiss();
        listener.onCenterItemClick(this, v);
    }
}
