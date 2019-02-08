package com.example.commonlib.interfaces;


import cn.sharesdk.framework.PlatformDb;

public interface UserLoginInterface {
    void successWithUser(PlatformDb platform);

    void failed();
}
