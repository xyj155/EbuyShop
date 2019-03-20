package com.example.module_vr;

import android.Manifest;
import android.app.VoiceInteractor;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.ar.ARController;
import com.baidu.ar.DuMixCallback;
import com.baidu.ar.DuMixSource;
import com.baidu.ar.DuMixTarget;
import com.baidu.ar.bean.ARResource;
import com.baidu.ar.recg.CornerPointController;
import com.baidu.ar.recg.ImgRecognitionClient;
import com.example.commonlib.util.RouterUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.baidu.ar.ARController;
import com.baidu.ar.DuMixSource;
import com.baidu.ar.DuMixTarget;
import com.baidu.ar.TakePictureCallback;
import com.baidu.ar.bean.ARConfig;

import com.baidu.ar.recg.CornerPoint;
import com.baidu.ar.recg.CornerPointController;
import com.baidu.ar.recg.ImgRecognitionClient;
import com.baidu.ar.recorder.MovieRecorderCallback;
import com.baidu.ar.util.SystemInfoUtil;
import com.baidu.ar.util.UiThreadUtil;
import com.example.module_vr.camera.ARCameraManager;
import com.example.module_vr.draw.ARRenderer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.HashMap;

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
     */
    private ARRenderer mARRenderer;

    /**
     * AR相机管理
     */
    private ARCameraManager mARCameraManager;

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

    /**
     * ar sdk 接口ARController
     */
    private ARController mARController;
    /**
     * AR输入参数类
     */
    private DuMixSource mDuMixSource;
    /**
     * 返回参数类
     */
    private DuMixTarget mDuMixTarget;

    /**
     * 本地识图 云端识图扫描
     */
    private ImgRecognitionClient mImgRecognitionClient;

    /**
     * 扫描云点控制器
     */
    private CornerPointController mCornerPointController;

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
        ARController mARControler = new ARController(this);
        mARCameraManager = new ARCameraManager();
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
