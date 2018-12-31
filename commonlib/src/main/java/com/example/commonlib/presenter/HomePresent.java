package com.example.commonlib.presenter;

import android.util.Log;

import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.HomeModel;

import java.util.List;

public class HomePresent extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private HomeModel model = new HomeModel();
    private HomeContract.View view;

    public HomePresent(HomeContract.View view) {
        this.view = view;
    }

    private static final String TAG = "HomePresent";
    @Override
    public void userLogin() {
        model.userLogin(new BaseObserver<UserGson>() {


            @Override
            protected void onHandleSuccess(List<UserGson> t) {
                Log.i(TAG, "onHandleSuccess: "+t);
                view.loadUser(t);
            }

            @Override
            protected void onHandleError(String msg) {
                view.showError(msg);
                Log.i(TAG, "onHandleError: "+msg);
            }


        });
    }
}
