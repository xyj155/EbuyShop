package com.xuyijie.ebuyshop.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.commonlib.rxpermissions2.Permission;
import com.example.commonlib.rxpermissions2.RxPermissions;
import com.example.commonlib.view.toast.ToastUtils;
import com.xuyijie.ebuyshop.R;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PermissionRequestActivity extends AppCompatActivity {
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_request);
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        rxPermissions.requestEachCombined(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Observer<Permission>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Permission value) {
                        if (value.granted) {
                            startActivity(new Intent(PermissionRequestActivity.this, SplashActivity.class));
                            finish();
                        } else {
                            ToastUtils.show("权限没有通过哦！");
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
