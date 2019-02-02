package com.example.user.presenter;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.BaseObserver;
import com.example.user.contract.ToBeEvaluatedContract;
import com.example.user.model.ToBeEvaluatedModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ToBeEvaluatedPresenter extends BasePresenter<ToBeEvaluatedContract.View> implements ToBeEvaluatedContract.Presenter {

    public ToBeEvaluatedPresenter(ToBeEvaluatedContract.View mMvpView) {
        super(mMvpView);
    }

    private ToBeEvaluatedModel toBeEvaluatedModel = new ToBeEvaluatedModel();

    @Override
    public void updateGoodsEvaluate(Map<String, RequestBody> partMap, List<MultipartBody.Part> file) {
        mMvpView.showDialog("");
        toBeEvaluatedModel.updateGoodsEvaluate(partMap, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.uploadSuccess(emptyGsonBaseGson.isStatus());
                        mMvpView.hideDialog();
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
