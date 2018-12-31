package com.example.commonlib.http;

import android.util.Log;

import com.example.commonlib.base.BaseGson;

import java.util.List;

import rx.Observer;

public abstract class BaseObserver<T> implements Observer<BaseGson<T>> {

    private static final String TAG = "BaseObserver";




    @Override
    public void onNext(BaseGson<T> value) {
        if (value.isStatus()) {
            List<T> t = value.getData();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "error:" + e.toString());
    }

//    @Override
//    public void onComplete() {
//        Log.d(TAG, "onComplete");
//    }

    @Override
    public void onCompleted() {

    }

    protected abstract void onHandleSuccess(List<T> t);

    protected abstract void onHandleError(String msg);

}
