package com.example.commonlib.util;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RxPartMapUtils {
    public static RequestBody toRequestBodyOfText (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }

    public static RequestBody toRequestBodyOfImage(File pFile){

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), pFile);
        return fileBody;
    }
}
