package com.example.charlene.fkc.network.protocol;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

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

/*
* 此类定义协议的传输层
* */
public class Protocol_IO {
    /*
	 * @param inputStream:输出流
	 * @param protocol:数据包
	 * */
    public int IM_OutputStream(OutputStream outputStream, Protocol_Data protocol){
        int status=0;
        DataOutputStream dos = new DataOutputStream(outputStream);
        try {
            dos.writeBytes(protocol.getMessage());
        } catch (IOException e) {
            status = 1;
            e.printStackTrace();
        }finally {
            if(null != dos){
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    /*
     * @param inStream:输入流
     * @return protocol:数据包
     * */
    public Protocol_Data IM_InputStream(InputStream inStream){
        ArrayList<String> arr = new ArrayList<>();
        MessageHead mh = null;
        String msg = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        try {
            mh = new MessageHead(br.readLine(),br.readLine());
            while(true){
                msg = br.readLine();
                if(msg == null){
                    break;
                }
                arr.add(msg+"\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Protocol_Data(mh, new MainMessage(arr));
    }
}
