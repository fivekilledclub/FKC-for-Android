package com.example.charlene.fkc.temporarydatastorage;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

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

public class Person implements Serializable{
    private String name;
    private Bitmap avatar;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Bitmap getAvatar() {
        return avatar;
    }
    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}
