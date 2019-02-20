package com.example.module_message.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.MessageExpressTraceGson;
import com.example.commonlib.gson.SystemMessageGson;

import java.util.List;

import rx.Observable;

public interface MessageContract {
    interface Model {
        Observable<BaseGson<SystemMessageGson>> querySystemPushMessage(String page);
        Observable<BaseGson<MessageExpressTraceGson>> queryUserGoodsTrace(String userId, String page);

    }

    interface View  extends BaseView {
      void   querySystemPushMessage(List<SystemMessageGson> systemMessageGsonList);
      void queryUserGoodsTrace(List<MessageExpressTraceGson> messageExpressTraceGsonList);
    }

    interface Presenter {
        void querySystemPushMessage(String page);
        void queryUserGoodsTrace(String userId, String page);

    }
}
