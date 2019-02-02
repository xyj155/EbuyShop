package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public interface ToBeEvaluatedContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> updateGoodsEvaluate(Map<String, RequestBody> partMap,
                                                            List<MultipartBody.Part> file);
    }

    interface View extends BaseView {
        void uploadSuccess(boolean isSuccess);
    }

    interface Presenter {
        void updateGoodsEvaluate(Map<String, RequestBody> partMap,
                                 List<MultipartBody.Part> file);
    }
}
