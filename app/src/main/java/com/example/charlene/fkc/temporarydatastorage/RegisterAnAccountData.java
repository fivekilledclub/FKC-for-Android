package com.example.charlene.fkc.temporarydatastorage;

import android.graphics.Bitmap;

import java.sql.Blob;

/**
 * Created by Charlene on 2017/9/18.
 * 此类提供静态方法用于存储用户注册账号时填写在各个Activity中的临时数据
 */

public class RegisterAnAccountData {
    /*
    * 以下成员变量分别用于存储用户注册时填写的数据：
    * 邮箱、密码、用户名、性别、生日、国家、市（区）
    * */
    public static String EMAIL = null;//邮箱
    public static String PASSWORD = null;
    public static String USERNAME = null;
    public static String GENDER = null;
    public static String BRITHDAY= null;
    public static String COUNTRY= null;
    public static String CITY= null;

    //存储用户头像二进制数据
    private static Bitmap AVATAE = null;

    /*
    * 判断注册数据是否填写完整
    * */
    public boolean isFillIn(){
        if(EMAIL!=null&&PASSWORD==null&&USERNAME!=null&&GENDER!=null&&BRITHDAY!=null&&COUNTRY!=null&&CITY!=null&&AVATAE!=null){
            return true;
        }else{
            return false;
        }
    }

}
