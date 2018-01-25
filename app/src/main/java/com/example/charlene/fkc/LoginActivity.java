package com.example.charlene.fkc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Charlene on 2017/7/5.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarTransparent();
        Button signup_btn = (Button) findViewById(R.id.login_signup_button);
        signup_btn.setOnClickListener(this);
        Button login_btn = (Button) findViewById(R.id.login_login_button);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.login_signup_button:
                intent = new Intent(this,SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.login_login_button:
                intent = new Intent(this,Login2Activity.class);
                startActivity(intent);
                break;
        }
    }

    /*
  * 设置状态栏透明
  * */
    private void setStatusBarTransparent(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
