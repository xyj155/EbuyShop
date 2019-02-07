package com.example.module_message.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.SystemMessageGson;

import java.util.List;

import rx.Observable;

public interface MessageContract {
    interface Model {
        Observable<BaseGson<SystemMessageGson>> querySystemPushMessage(String page);

    }

    interface View  extends BaseView {
      void   querySystemPushMessage(List<SystemMessageGson> systemMessageGsonList);
    }

    interface Presenter {
        void querySystemPushMessage(String page);

    }
}
