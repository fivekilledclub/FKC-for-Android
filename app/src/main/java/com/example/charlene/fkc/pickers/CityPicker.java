package com.example.charlene.fkc.pickers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.charlene.fkc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charlene on 2017/9/22.
 * 作者：刘洋标
 * QQ：1605155603
 * QQ公众号：五杀俱乐部
 * 邮箱：1605155603@qq.com
 * 微信：ruimengshen
 * 新浪微博：http://weibo.com/u/5686401277
 * Android API：https://developer.android.google.cn/guide/index.html
 */

public class CityPicker extends DialogFragment {
    private EditText city = null;
    ScrollPickerView cityPicker = null;
    List<String> data;

    private CityPicker context= this;

    public CityPicker(String country,EditText city){
        this.city = city;
        data = new ArrayList<String>();
        if(country.equals(City.COUNTRY[0])){
            for(int i=0;i<City.CHINA.length;i++)data.add(City.CHINA[i]);
        }else if(country.equals(City.COUNTRY[1])){
            for(int i=0;i<City.AMERICA.length;i++)data.add(City.AMERICA[i]);
        }else data.add("请选择国家");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.picker_city,null);
        builder.setView(view);
        cityPicker = (ScrollPickerView) view.findViewById(R.id.signup3_content);
        cityPicker.setData(data);
        cityPicker.setOnSelectListener(new ScrollPickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                city.setText(text);
            }
        });

        Button ok = (Button) view.findViewById(R.id.signup3_ok_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.dismiss();
            }
        });
        return builder.create();
    }
}
