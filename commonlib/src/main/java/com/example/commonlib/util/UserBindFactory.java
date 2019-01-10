package com.example.commonlib.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.example.commonlib.annotation.UserAnnotation;
import com.example.commonlib.annotation.UserType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.OnClick;


public class UserBindFactory {
    private static final String TAG = "UserBindFactory";





    public static void bind(final Activity activity) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                OnClick bindView = field.getAnnotation(OnClick.class);
                if (bindView!=null){
                    int[] value = bindView.value();
                    Log.i(TAG, "bind: "+value);
                }

//                View view = activity.findViewById(value);
//                try {
//                    field.set(activity, view);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
            }
        }

//        Class<? extends Activity> klass = activity.getClass();
//        Method[] fields = klass.getMethods();
//        for (Method field : fields) {
//            Log.i(TAG, "bind: "+1);
////            UserAnnotation viewInject = field.getAnnotation(UserAnnotation.class);
//            Annotation[] annotations = field.getDeclaredAnnotations();
//
//            for(Annotation annotation : annotations){
//                if(annotation instanceof UserAnnotation){
//                    UserAnnotation myAnnotation = (UserAnnotation) annotation;
////                    System.out.println("name: " + myAnnotation.type());
//                    Log.i(TAG, "bind: "+myAnnotation.type());
////                    System.out.println("value: " + myAnnotation.value());
//                }
//            }
//            if (viewInject!=null){
//                Log.i(TAG, "bind: ");
//                UserType type = viewInject.type();
//                switch (type) {
//                    case NOTPERMITED:
//                        Log.i(TAG, "bind:2 ");
//                        break;
//                    case ISPERMITED:
//                        Log.i(TAG, "bind:1 ");
//                        break;
//                }
//            }

//            if(viewInject != null){
//                try {
//                    int id = viewInject.id();
//                    field.setAccessible(true);
//                    field.set(activity,activity.findViewById(id));
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
    }




}
