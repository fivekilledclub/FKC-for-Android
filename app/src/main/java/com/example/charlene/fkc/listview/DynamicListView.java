package com.example.charlene.fkc.listview;

import android.graphics.Bitmap;

import com.example.charlene.fkc.Utils.Utils;

/**
 * Created by Charlene on 2017/7/5.
 */

public class DynamicListView {
    private String name;
    private Bitmap avatar;
    private String timePoint;
    private String info;
    private String comment;
    private Bitmap photh1;

    public DynamicListView(String name, Bitmap avatar, String timePoint, String info, String comment){
        this.name = name;
        this.avatar =avatar;
        this.timePoint = timePoint;
        this.info = info;
        this.comment = comment;
    }

    public DynamicListView(String name, Bitmap imageID, String timePoint, String info, String comment,Bitmap photh1ID){
        this.name = name;
        this.avatar = Utils.toRoundBitmap(imageID);
        this.timePoint = timePoint;
        this.info = info;
        this.comment = comment;
        this.photh1 = photh1ID;
    }


    public String getName() {
        return name;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getTimePoint() {
        return timePoint;
    }

    public String getInfo() {
        return info;
    }

    public String getComment() {
        return comment;
    }

    public Bitmap getPhoth1() {
        return photh1;
    }
}
