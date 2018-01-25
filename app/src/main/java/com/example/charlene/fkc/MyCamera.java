package com.example.charlene.fkc;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Charlene on 2017/7/3.
 */

public class MyCamera extends Activity {
    private Camera mCamera = null;
    private CameraPreview mPreview = null;
    private Handler handler = null;
    private Thread thread = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Window window = getWindow();
        //全屏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //高亮
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        handler = new Handler();
        mCamera = getCameraInstance();

        mPreview = new CameraPreview(this,mCamera);//The parent of CameraPreview is SurfaceView
        LinearLayout linearLayoutleft = (LinearLayout) findViewById(R.id.camrea_preview_left);
        LinearLayout linearLayoutright = (LinearLayout) findViewById(R.id.camrea_preview_rigth);
        linearLayoutleft.addView(mPreview);
        linearLayoutright.addView(mPreview);
    }

    public Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        }catch (Exception e){
            Log.i("Charlene","打开摄像头失败"+e.getMessage());
        }
        return camera;
    }
}
class MyRunnable implements Runnable {

    Thread thread = null;
    public MyRunnable(Thread thread){
        this.thread = thread;
    }
    @Override
    public void run() {

    }
}