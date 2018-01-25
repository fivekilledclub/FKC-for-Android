package com.example.charlene.fkc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.charlene.fkc.listview.FriendListView;
import com.example.charlene.fkc.R;

import java.util.List;

public class FriendAdapter  extends ArrayAdapter<FriendListView>
        implements SectionIndexer{

    List<FriendListView> list;

    public FriendAdapter(@NonNull Context context, @LayoutRes int resource, List<FriendListView> objects) {
        super(context, resource, objects);
        this.list = objects;
    }

    Bitmap avatar = null;
    String name = null;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View view, @NonNull final ViewGroup parent) {
        ViewHolder viewHolder = null;
        FriendListView friendListView = getItem(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_friend, null);
            viewHolder.avatar = (ImageView) view.findViewById(R.id.friend_head_image);
            viewHolder.name = (TextView) view.findViewById(R.id.frinend_name);
            viewHolder.sortLetters = (TextView) view.findViewById(R.id.friend_Sorting);
            viewHolder.email = (TextView) view.findViewById(R.id.friend_email);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.avatar.setImageBitmap(friendListView.getBitmap());
        viewHolder.name.setText(friendListView.getName());
        viewHolder.sortLetters.setText(friendListView.getSortLetters());
        viewHolder.email.setText(friendListView.getEmail());

        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            viewHolder.sortLetters.setVisibility(View.VISIBLE);
            viewHolder.sortLetters.setText(friendListView.getSortLetters());
        } else {
            viewHolder.sortLetters.setVisibility(View.GONE);
        }

        avatar = friendListView.getBitmap();
        name = friendListView.getName();
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                avatar.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                byte [] bitmapByte =baos.toByteArray();
//                Intent intent = new Intent(getContext(), ChatActivity.class);
//                intent.putExtra("avatar", bitmapByte);
//                intent.putExtra("name",name);
//                getContext().startActivity(intent);
//            }
//        });
        return view;
    }

    final static class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView sortLetters;
        TextView email;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

}
