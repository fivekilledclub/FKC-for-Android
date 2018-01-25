package com.example.charlene.fkc.network.protocol;

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
* 此类定义协议的数据层
* */
public class Protocol_Data {

    private String message = "";


    public String getMessage(){
        return message;
    }
	/*
	 * 请求样式
	 * 问题：
	 * 	1、解耦性太低：每添加一个需求都需要添加对一个样式
	 * 		解决方案：创建一个XML文件，把需要的样式写入XML配置文件（未完成）
	 * 	2、Int到String之间转换复杂：解析请求样式时类容为String，提高了判断样式难度
	 * */
    /**
     *
     */
    public static final int REQUEST_TYPE_LOGIN = 0x001;//登录请求
    public static final int RESPONSE_TYPE_LOGIN = 0x001;//登录回应

    public static final int REQUEST_TYPE_SIGNUP = 0x002;//注册请求
    public static final int RESPONSE_TYPE_SIGNUP = 0x001;//注册回应

    public Protocol_Data(MessageHead mh,MainMessage mm){
        message += mh.messageHead;
        message += mm.mainMessage;
    }
}
