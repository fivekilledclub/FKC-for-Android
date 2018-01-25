package com.example.charlene.fkc.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.charlene.fkc.listview.DynamicListView;
import com.example.charlene.fkc.R;

import java.util.List;

/**
 * Created by Charlene on 2017/7/5.
 *
 * 主页里面好友动态Adapter
 */

public class DynamicAdapter extends ArrayAdapter<DynamicListView> implements View.OnClickListener{

    private ViewHolder viewHolder;

    private int resourceID;

    private boolean isHeart = false;

    public DynamicAdapter(@NonNull Context context, @LayoutRes int resource, List<DynamicListView> objects) {
        super(context, resource,objects);
        this.resourceID = resource;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dynamic_heart:
                if(isHeart == false) {
                    viewHolder.heart.setImageResource(R.mipmap.heart);
                    isHeart = true;
                }else {
                    viewHolder.heart.setImageResource(R.mipmap.heart2);
                    isHeart = false;
                }
                break;
        }
    }


    final static class ViewHolder{
        TextView name;
        ImageView avarate;
        TextView timePoint;
        TextView info;
        TextView comment;
        ImageView photh1;
        ImageView heart;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        DynamicListView dynamicListView = getItem(position);
        if(view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(resourceID,null);
            viewHolder.name = (TextView) view.findViewById(R.id.dynamic_name);
            viewHolder.avarate = (ImageView) view.findViewById(R.id.dynamic_head_image);
            viewHolder.timePoint = (TextView) view.findViewById(R.id.dynamic_timepoint);
            viewHolder.info = (TextView) view.findViewById(R.id.dynamic_info);
            viewHolder.comment = (TextView) view.findViewById(R.id.dynamic_comment);
            viewHolder.photh1 = (ImageView) view.findViewById(R.id.photo1);
            viewHolder.heart = (ImageView) view.findViewById(R.id.dynamic_heart);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.heart.setOnClickListener(this);
        viewHolder.avarate.setImageBitmap(dynamicListView.getAvatar());
        viewHolder.name.setText(dynamicListView.getName());
        viewHolder.timePoint.setText(dynamicListView.getTimePoint());
        viewHolder.info.setText(dynamicListView.getInfo());
        viewHolder.comment.setText(dynamicListView.getComment());
        viewHolder.photh1.setImageBitmap(dynamicListView.getPhoth1());
        return view;
    }
}