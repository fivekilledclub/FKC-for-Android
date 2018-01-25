package com.example.charlene.fkc;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;


public class Login2Activity extends Activity{
    private Intent intent;
    private String email;
    private EditText usernameEdit;
    private String password;
    private EditText passwordEdit;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        setStatusBarTransparent();
        intent = new Intent(this,MainActivity.class);
        loginButton = (Button) findViewById(R.id.login2_Login_button);
        usernameEdit = (EditText) findViewById(R.id.login2_username);
        passwordEdit = (EditText) findViewById(R.id.login2_password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = usernameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                if (email.equals("1605155603@qq.com")&&password.equals("1605155603")){
                    SharedPreferences sharedPreferences = getSharedPreferences("ActivityStatus", Context.MODE_APPEND);
                    SharedPreferences.Editor edit= sharedPreferences.edit();
                    edit.putString("ActivityStatus","已登录");
                    edit.commit();
                    startActivity(intent);
                }else {
                    Toast.makeText(Login2Activity.this,"username and password error!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
  * 设置状态栏透明
  * */
    private void setStatusBarTransparent(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
