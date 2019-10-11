package com.example.module_vr;

import android.Manifest;
import android.app.VoiceInteractor;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.example.commonlib.util.RouterUtil;




@Route(path= RouterUtil.ARMAP)
public class ArMapActivity extends AppCompatActivity {

    private static final String TAG = "ARFragment";

    /**
     * Fragment 真正的容器
     */
    private FrameLayout mFragmentContainer;

    /**
     * Ar RootView
     */
    private View mRootView;

    /**
     * AR View
     */
    private GLSurfaceView mArGLSurfaceView;

    /**
     * Prompt View 提示层View
     */
    private VoiceInteractor.Prompt mPromptUi;

    /**
     * AR Renderer


    /**
     * 最长录制最长时间设置
     */
    private int mRecordMaxTime = 10 * 1000;
    /**
     * 需要手动申请的权限
     */
    private static final String[] ALL_PERMISSIONS = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};



    private int mCameraPriWidth = 1280;
    private int mCameraPriHeight = 720;

    // 接收 参数
    private String mArKey;
    private int mArTpye;
    private String mArFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_map);


//        DuMixSource duMixSource = new DuMixSource();
//        DuMixTarget duMixTarget = new DuMixTarget();
//
//        mARControler.setup(duMixSource,duMixTarget, new DuMixCallback() {
//            @Override
//            public void onStateChange(int i, Object o) {
//
//            }
//
//            @Override
//            public void onLuaMessage(HashMap<String, Object> hashMap) {
//
//            }
//
//            @Override
//            public void onStateError(int i, String s) {
//
//            }
//
//            @Override
//            public void onSetup(boolean b) {
//
//            }
//
//            @Override
//            public void onCaseChange(boolean b) {
//
//            }
//
//            @Override
//            public void onCaseCreated(ARResource arResource) {
//
//            }
//
//            @Override
//            public void onPause(boolean b) {
//
//            }
//
//            @Override
//            public void onResume(boolean b) {
//
//            }
//
//            @Override
//            public void onReset(boolean b) {
//
//            }
//
//            @Override
//            public void onRelease(boolean b) {
//
//            }
//        });
//        mARControler.onCameraPreviewFrame(data, width, height);
    }
}
