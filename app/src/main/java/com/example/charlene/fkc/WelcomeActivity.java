package com.example.charlene.fkc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.charlene.fkc.network.RegisteredClient;

public class WelcomeActivity extends Activity {

    public static Intent netWorkService = null;
    Intent intentSignIn;
    Intent intentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(netWorkService==null){
            netWorkService = new Intent(this,NetWorkService.class);
            startService(netWorkService);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("ActivityStatus", Context.MODE_APPEND);
        String status = sharedPreferences.getString("ActivityStatus","0");
        if(status.equals("已登录")){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button login = (Button) findViewById(R.id.welcome_login);
        intentSignIn = new Intent(this,Login2Activity.class);
        intentAccount = new Intent(this,SignupActivity.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentSignIn);
            }
        });
        TextView cAccount = (TextView) findViewById(R.id.welcome_createandaccount);
        cAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentAccount);
            }
        });
    }
}
