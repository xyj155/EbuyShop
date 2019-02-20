package com.example.commonlib.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;

import rx.Observable;

public interface UserMemberDateContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> judgementMember(String userId);
    }

    interface View extends BaseView {
        void loadUserMember(int code);

    }

    interface Presenter {
        void judgementMember(String userId);
    }
}
