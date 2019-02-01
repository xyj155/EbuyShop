package com.example.commonlib.view.toast;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/ToastUtils
 *    time   : 2018/11/12
 *    desc   : Toast 显示处理类
 */
final class ToastHandler extends Handler {

    static final int SHORT_DURATION_TIMEOUT = 2000; // 短吐司显示的时长
    static final int LONG_DURATION_TIMEOUT = 3500; // 长吐司显示的时长

    private static final int TYPE_SHOW = 1; // 显示吐司
    private static final int TYPE_CONTINUE = 2; // 继续显示
    private static final int TYPE_CANCEL = 3; // 取消显示

    // 最大吐司的容量
    private static final int MAX_TOAST_CAPACITY = 5;

    // 吐司队列
    private volatile Queue<CharSequence> mQueue = new ArrayBlockingQueue<>(MAX_TOAST_CAPACITY);

    // 当前是否正在执行显示操作
    private volatile boolean isShow;

    // 吐司对象
    private final Toast mToast;

    ToastHandler(Toast toast) {
        super(Looper.getMainLooper());
        mToast = toast;
    }

    void add(CharSequence s) {
        if (mQueue.isEmpty() || !mQueue.contains(s)) {
            // 添加一个元素并返回true，如果队列已满，则返回false
            if (!mQueue.offer(s)) {
                // 移除队列头部元素并添加一个新的元素
                mQueue.poll();
                mQueue.offer(s);
            }
        }
    }

    void show() {
        if (!isShow) {
            isShow = true;
            sendEmptyMessage(TYPE_SHOW);
        }
    }

    void cancel() {
        if (isShow) {
            isShow = false;
            sendEmptyMessage(TYPE_CANCEL);
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case TYPE_SHOW:
                // 返回队列头部的元素，如果队列为空，则返回null
                CharSequence text = mQueue.peek();
                if (text != null) {
                    mToast.setText(text);
                    mToast.show();
                    // 等这个 Toast 显示完后再继续显示
                    sendEmptyMessageDelayed(TYPE_CONTINUE, getToastDuration(text) + 1000);
                }else {
                    isShow = false;
                }
                break;
            case TYPE_CONTINUE:
                // 移除并返问队列头部的元素，如果队列为空，则返回null
                mQueue.poll();
                if (!mQueue.isEmpty()) {
                    sendEmptyMessage(TYPE_SHOW);
                }else {
                    isShow = false;
                }
                break;
            case TYPE_CANCEL:
                isShow = false;
                mQueue.clear();
                mToast.cancel();
                break;
            default:
                break;
        }
    }

    /**
     * 根据文本来获取吐司的显示时间
     */
    private static int getToastDuration (CharSequence text) {
        // 如果显示的文字超过了10个就显示长吐司，否则显示短吐司
        return text.length() > 20 ? LONG_DURATION_TIMEOUT : SHORT_DURATION_TIMEOUT;
    }
}