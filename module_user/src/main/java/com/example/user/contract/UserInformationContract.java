package com.example.user.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import okhttp3.MultipartBody;
import rx.Observable;

public interface UserInformationContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> updateUserAvatar(String userId, MultipartBody.Part avatar, String username);
    }

    interface View extends BaseView {
        void updateUserAvatar(boolean success,String vatar);
    }

    interface Presenter {
        void updateUserAvatar(String userId, MultipartBody.Part avatar, String username);
    }
}
