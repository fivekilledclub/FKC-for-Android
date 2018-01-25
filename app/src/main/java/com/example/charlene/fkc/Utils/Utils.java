package com.example.charlene.fkc.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.EditText;

/**
 * Created by Charlene on 2017/9/20.
 * 此类为工具类，提供Android 中对一些特殊数据的处理
 */

public class Utils {

    /**
     * 实现Bitmap图片转换为圆角图片
     */
    public static Bitmap toRoundBitmap(Bitmap photo){
        int width = photo.getWidth();
        int height = photo.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_right,dst_top,dst_bottom;
        if(width<=height){
            roundPx = width/2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        }else{
            roundPx = height /2;
            float clip = (width - height)/2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left,(int)top,(int)right,(int)bottom);
        final Rect dst = new Rect((int)dst_left,(int)dst_top,(int)dst_bottom,(int)dst_right);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF,roundPx,roundPx,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(photo,src,dst,paint);
        return output;
    }

    /*
    * 判断EditText里面是否有类容
    * */
    public static boolean isEditTextContent(EditText text){
        if(text.getText().toString()=="")
            return false;
        else return true;
    }

}
