package com.example.charlene.fkc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.charlene.fkc.temporarydatastorage.Person;

import java.util.zip.Inflater;

/**
 * Created by Charlene on 2017/9/26.
 * 作者：刘洋标
 * QQ：1605155603
 * QQ公众号：五杀俱乐部
 * 邮箱：1605155603@qq.com
 * 微信：ruimengshen
 * 新浪微博：http://weibo.com/u/5686401277
 * Android API：https://developer.android.google.cn/guide/index.html
 */

public class ChatActivity extends FragmentActivity implements View.OnClickListener{

    private ImageView sendBtn  = null;

    private EditText sendEdit = null;

    private LinearLayout messageContainer = null;

    private LinearLayout chat_sendsuperbox  = null;

    private ScrollView scrollViewContainer = null;

    boolean isEditClick = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setStatusBarTransparent();
        instantiationView();
        resetSendMsgRl();
        setScrollViewFocusDown();
        sendBtn.setOnClickListener(this);
        sendEdit.setOnClickListener(this);
        init();
    }

    private void init(){
        Person person = (Person) getIntent().getSerializableExtra("data");
        TextView name = (TextView) findViewById(R.id.char_name);
        ImageView avatar = (ImageView) findViewById(R.id.char_avatar);
        Intent intent=getIntent();
        if(intent !=null)
        {
            byte [] bis=intent.getByteArrayExtra("avatar");
            Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
            avatar.setImageBitmap(bitmap);
            name.setText(intent.getStringExtra("name"));
        }
    }

    /*
    * 实例化视图控件
    * */
    private void instantiationView(){
        chat_sendsuperbox = (LinearLayout) findViewById(R.id.chat_sendsuperbox);
        sendBtn = (ImageView) findViewById(R.id.chat_send_btn);
        sendEdit = (EditText) findViewById(R.id.chat_send_edit);
        messageContainer = (LinearLayout) findViewById(R.id.chat_message_container);
        scrollViewContainer = (ScrollView) findViewById(R.id.chat_scrolview_container);
    }

    /*
    * 设置Scroll View焦点滚动到底部
    * */
    private void setScrollViewFocusDown(){
        scrollViewContainer.fullScroll(ScrollView.FOCUS_DOWN);
    }

    /*
    * 调用软键盘时自动调整Edit高度
    * */
    private void resetSendMsgRl(){
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = getScreenHeight();
                int heightDifference = screenHeight - rect.bottom;//计算软键盘占用的高度 = 屏幕高度 - 视图可见高度
                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) chat_sendsuperbox.getLayoutParams();
                layoutParams.setMargins(0,0,0,heightDifference);
                if(isEditClick)setScrollViewFocusDown();
                chat_sendsuperbox.requestLayout();
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
   * 设置状态栏透明
   * */
    private void setStatusBarTransparent(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat_send_btn:
                if(sendEdit.getText().toString().equals("")) {
                    sendEdit.setHint("Please input message...");
                }else {
                    sendMessage(sendEdit.getText().toString());
                    setScrollViewFocusDown();
                    sendEdit.setText("");
                }
                break;
            case R.id.chat_send_edit:
                isEditClick = true;
                break;
        }
    }

    private void sendMessage(String text){
        View sendMessage = FrameLayout.inflate(this,R.layout.chat_sendmessage,null);
        TextView textView = (TextView) sendMessage.findViewById(R.id.sendmessage);
        textView.setText(text);
        messageContainer.addView(sendMessage);
    }
}
