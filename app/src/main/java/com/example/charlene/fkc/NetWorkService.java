package com.example.charlene.fkc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Charlene on 2017/11/1.
 * 作者：刘洋标
 * QQ：1605155603
 * QQ公众号：五杀俱乐部
 * 邮箱：1605155603@qq.com
 * 微信：ruimengshen
 * 新浪微博：http://weibo.com/u/5686401277
 * Android API：https://developer.android.google.cn/guide/index.html
 */

public class NetWorkService  extends Service{


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 此方法会在服务创建的时候调用
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //此方法会在每次启动服务的时候调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        try {
//            NetWorkService.connect();
//            tRecv.start();
//        } catch (UnknownHostException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return super.onStartCommand(intent, flags, startId);
    }
    //此方法会在服务销毁的时候调用
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private static final ThreadLocal<Socket> threadConnect = new ThreadLocal<Socket>();

    private static final String HOST = "192.168.43.178";

    private static final int PORT = 1998;

    private static Socket client;

    private static OutputStream outStr = null;

    private static InputStream inStr = null;

    private static Thread tRecv = new Thread(new RecvThread());
    private static Thread tKeep = new Thread(new KeepThread());

    public static void connect() throws UnknownHostException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                client = threadConnect.get();
                if(client == null){
                    try {
                        client = new Socket(HOST, PORT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    threadConnect.set(client);
                    tKeep.start();
                }
                try {
                    outStr = client.getOutputStream();
                    inStr = client.getInputStream();
                } catch (IOException e) {
                    Log.i("charlene",e.getMessage());
                }

            }
        }).start();
    }

    public static void disconnect() {
        try {
            outStr.close();
            inStr.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static class KeepThread implements Runnable {
        public void run() {
            try {
                Log.i("charlene","==============开始发送心跳包===============");
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.i("charlene","发送心跳数据包");
                    outStr.write("send heart beat data package !".getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class RecvThread implements Runnable {
        public void run() {
            try {

                Log.i("charlene","==============开始接收数据===============");
                while (true) {
                    byte[] b = new byte[1024];
                    int r = inStr.read(b);
                    if(r>-1){
                        String str = new String(b);
                        Log.i("charlene",str);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
