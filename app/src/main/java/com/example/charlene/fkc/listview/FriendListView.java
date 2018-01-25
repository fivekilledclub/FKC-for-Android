package com.example.charlene.fkc.listview;

import android.graphics.Bitmap;

import com.example.charlene.fkc.Utils.Utils;

public class FriendListView  {
    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母
    private Bitmap bitmap;//显示头像数据
    private String email;//显示邮箱账号

    public FriendListView(Bitmap bitmap,String name,String email){
        this.bitmap = Utils.toRoundBitmap(bitmap);
        this.name = name;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
