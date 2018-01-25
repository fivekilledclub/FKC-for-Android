package com.example.charlene.fkc.customizeview;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Charlene on 2017/9/22.
 * 作者：刘洋标
 * QQ：1605155603
 * QQ公众号：五杀俱乐部
 * 邮箱：1605155603@qq.com
 * 微信：ruimengshen
 * 新浪微博：http://weibo.com/u/5686401277
 * Android API：https://developer.android.google.cn/guide/index.html
 *
 * 自定义市(区)选择器 TextView
 */

public class CustomizeCityPickerTextView extends android.support.v7.widget.AppCompatTextView{

    private float textsize = 18;

    public CustomizeCityPickerTextView(Context context) {
        super(context);
        setTextSize(textsize);
        setTextColor(Color.BLUE);
        setGravity(Gravity.CENTER);
    }
}
