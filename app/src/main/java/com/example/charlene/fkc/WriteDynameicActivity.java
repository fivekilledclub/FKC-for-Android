package com.example.charlene.fkc;

import android.app.Activity;
import android.graphics.Rect;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.charlene.fkc.R;
import com.example.charlene.fkc.pickers.AddPhotoPicker;

/**
 * Created by Charlene on 2017/10/7.
 * 作者：刘洋标
 * QQ：1605155603
 * QQ公众号：五杀俱乐部
 * 邮箱：1605155603@qq.com
 * 微信：ruimengshen
 * 新浪微博：http://weibo.com/u/5686401277
 * Android API：https://developer.android.google.cn/guide/index.html
 */

public class WriteDynameicActivity extends FragmentActivity implements View.OnClickListener{

    LinearLayout mPhotoContent = null;
    FrameLayout mPhoto = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writedynamic);
        initViews();
        //automaticControlDownContainer();
    }

    private void initViews(){
        mPhotoContent = (LinearLayout) findViewById(R.id.writedynamic_photocentent_1);
        writedynamic_down_content = (LinearLayout) findViewById(R.id.writedynamic_down_content);
    }
    /*
    * 有软键盘弹出时控制LinearLayout的位置处于软键盘上边
    * */
    LinearLayout writedynamic_down_content;
    private void automaticControlDownContainer(){
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = getScreenHeight();
                int heightDifference = screenHeight - rect.bottom;//计算软键盘占用的高度 = 屏幕高度 - 视图可见高度
                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) writedynamic_down_content.getLayoutParams();
                layoutParams.setMargins(0,0,0,heightDifference);

            }
        });
    }

    private int getScreenHeight(){
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return  outMetrics.heightPixels;
    }

    /*
    * 透明状态栏
    * */
    private void setStatusBarTransparent(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /*
    * 获取器系统硬件按钮事件
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            /*
            * 返回按钮事件
            * */
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }



    public void onDeletePhotoClick(View v){
        mPhotoContent.removeView(mPhoto);
        mPhoto = null;
        Toast.makeText(this,"DeleteClick",Toast.LENGTH_SHORT).show();
    }

    public void onReturnClick(View v){
        this.finish();
    }

    public void onSendClick(View v){
        Toast.makeText(this,"后台暂未搭建",Toast.LENGTH_SHORT).show();
    }

    private FrameLayout createPhotoContent(){
        FrameLayout frameLayout = (FrameLayout) this.getLayoutInflater().inflate(R.layout.framelayout_photocontent,null);
        return  frameLayout;
    }

    public void onAddPhotoClick(View v){
        if(mPhoto==null) {
            mPhoto = createPhotoContent();
            mPhotoContent.addView(mPhoto);
        }else {
            DialogFragment dialogFragment = new AddPhotoPicker();
            dialogFragment.show(getSupportFragmentManager(),"AddPhotoPicker");
            Toast.makeText(this,"只能添加一张图片，请删除后再试",Toast.LENGTH_SHORT).show();
        }
    }

}
