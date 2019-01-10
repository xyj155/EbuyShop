package com.example.commonlib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.commonlib.MyApp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class UserFactory {
    public void getUserMessage(Context context, Class<?> tatger) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Map<String, ?> all = sharedPreferences.getAll();
        if (all.isEmpty()) {
            Toast.makeText(MyApp.getInstance(), "请先登录！", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context,tatger));
        }
    }

}
