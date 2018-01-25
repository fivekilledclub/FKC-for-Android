package com.example.charlene.fkc.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charlene on 2017/10/28.
 * 作者：刘洋标
 * QQ：1605155603
 * QQ公众号：五杀俱乐部
 * 邮箱：1605155603@qq.com
 * 微信：ruimengshen
 * 新浪微博：http://weibo.com/u/5686401277
 * Android API：https://developer.android.google.cn/guide/index.html
 */

public class RegisteredClient {

    private String ip;
    private int port;
    private Socket socket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    public RegisteredClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    /*
    * 连接服务器
    * */
    public void connectServer() throws IOException {
        socket = new Socket(ip,port);
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
    }

    public String run(ArrayList<String> arr) throws IOException {
        String RESPONSE_TYPE_LOGIN;
        PrintWriter pw = new PrintWriter(outputStream);
        pw.println("REQUEST_TYPE_LOGIN");
        for(int i=0;i<arr.size();i++){
            pw.println(arr.get(i));
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while (true){
            if((RESPONSE_TYPE_LOGIN=br.readLine())!=null)break;
        }
        return RESPONSE_TYPE_LOGIN;
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
