package com.example.commonlib.base;

public interface Presenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
