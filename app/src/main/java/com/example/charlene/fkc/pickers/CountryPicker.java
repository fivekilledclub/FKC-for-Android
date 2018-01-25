package com.example.charlene.fkc.pickers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.example.charlene.fkc.temporarydatastorage.RegisterAnAccountData;

/**
 * Created by Charlene on 2017/9/21.
 * 国家选择器
 */



public class CountryPicker extends DialogFragment{

    public final String[] COUNTRY = new String[]{City.COUNTRY[0],City.COUNTRY[1]};

    private EditText edit;

    public CountryPicker(EditText edit){
        this.edit = edit;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select country");
        builder.setItems(COUNTRY, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit.setText(COUNTRY[which]);
                RegisterAnAccountData.COUNTRY=COUNTRY[which];
            }
        });
        return builder.create();
    }

}
