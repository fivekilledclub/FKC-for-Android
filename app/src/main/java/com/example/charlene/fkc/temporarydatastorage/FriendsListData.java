package com.example.charlene.fkc.temporarydatastorage;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Charlene on 2017/10/6.
 * 作者：刘洋标
 * QQ：1605155603
 * QQ公众号：五杀俱乐部
 * 邮箱：1605155603@qq.com
 * 微信：ruimengshen
 * 新浪微博：http://weibo.com/u/5686401277
 * Android API：https://developer.android.google.cn/guide/index.html
 *
 * 存储好友列表数据
 *
 *
 * 1-123
 * 2-456
 * 3-789
 */

public class FriendsListData {
    public int length;
    private  ArrayList<Bitmap> avatar = new ArrayList<Bitmap>();
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> email = new ArrayList<String>();
    private volatile static FriendsListData friendsListData;

    public static FriendsListData createFriendsListDate(){
        if(friendsListData == null){
            synchronized (FriendsListData.class){
                if(friendsListData ==null){
                    friendsListData = new FriendsListData();
                }
            }
        }
        return friendsListData;
    }
    private FriendsListData(){
        super();
    }
    public void add(Bitmap bitmap,String name,String email){
        this.avatar.add(bitmap);
        this.name.add(name);
        this.email.add(email);
        length = avatar.size();
    }

    public Bitmap getAvatar(int index){
        return avatar.get(index);
    }
    public String getName(int index){
        return name.get(index);
    }
    public String getEmail(int index){
        return email.get(index);
    }


}


