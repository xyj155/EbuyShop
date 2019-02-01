package com.example.commonlib.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.model.HomeModel;

public class LoginPresent extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private HomeModel model = new HomeModel();
    private HomeContract.View view;



    private static final String TAG = "LoginPresent";


    public LoginPresent(HomeContract.View mMvpView) {
        super(mMvpView);
    }

    @Override
    public void userLogin() {
        view.showDialog("加载中...");
        model.userLogin(new BaseObserver<BaseGson<UserGson>>() {


            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(BaseGson<UserGson> userGsonBaseGson) {
                view.loadUser(userGsonBaseGson.getData());
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
