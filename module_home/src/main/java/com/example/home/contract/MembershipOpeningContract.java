package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.MemberGson;

import java.util.List;

import rx.Observable;

public interface MembershipOpeningContract {
    interface Model {
        Observable<BaseGson<MemberGson>> queryMemberPrice();

        Observable<BaseGson<EmptyGson>> submitUserMemberShip(String userId, String memberRank);
    }

    interface View extends BaseView {
        void queryMemberPrice(List<MemberGson> memberGsons);

        void submitUserMember(boolean isSubmit);
    }

    interface Presenter {
        void queryMemberPrice();

        void submitUserMemberShip(String userId, String memberRank);
    }
}
