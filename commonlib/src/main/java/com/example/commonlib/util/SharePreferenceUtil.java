package com.example.commonlib.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.commonlib.MyApp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SharePreferenceUtil {
    public static void saveUser(Map<String, Object> user) {
        SharedPreferences.Editor editor = MyApp.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        for (Map.Entry<String, Object> entry : user.entrySet()) {
            if (entry.getValue() instanceof Boolean) {
                editor.putBoolean(entry.getKey(), (Boolean) entry.getValue());
            } else if (entry.getValue() instanceof String) {
                editor.putString(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                editor.putInt(entry.getKey(), (Integer) entry.getValue());
            } else if (entry.getValue() instanceof Float) {
                editor.putFloat(entry.getKey(), (Float) entry.getValue());
            } else if (entry.getValue() instanceof Long) {
                editor.putLong(entry.getKey(), (Long) entry.getValue());
            }
        }
        editor.apply();
    }

    public static Object getUser(String key, String type) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE);
        switch (type) {
            case "boolean":
                return sp.getBoolean(key, false);
            case "String":
                return sp.getString(key, "");
            case "int":
                return sp.getInt(key, 0);
            case "long":
                return sp.getLong(key, 0L);
            case "float":
                return sp.getFloat(key, 0f);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("user",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp =  MyApp.getInstance().getSharedPreferences("user",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("user",
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("user",
                Context.MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}
