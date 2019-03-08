package com.example.user.model;

import android.util.Log;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.RxPartMapUtils;
import com.example.user.contract.UserInformationContract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

import static com.tencent.smtt.sdk.TbsReaderView.TAG;

public class UserInformationModel implements UserInformationContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> updateUserAvatar(String userId, MultipartBody.Part avatar, String username) {
        Map<String, RequestBody> partMap = new HashMap<>();
        partMap.put("username", RxPartMapUtils.toRequestBodyOfText(username));
        partMap.put("userId", RxPartMapUtils.toRequestBodyOfText(userId));
        Log.i(TAG, "updateUserAvatar: "+userId+username);
        return RetrofitUtils.getInstance().create().updateUserAvatar(partMap,avatar);
    }
}
