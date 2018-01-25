package com.example.charlene.fkc.pickers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by Charlene on 2017/9/21.
 *
 * 日期选择器
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText edit = null;
    public DatePickerFragment(EditText editText){
        edit = editText;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int year = c.get(java.util.Calendar.YEAR);
        int month = c.get(java.util.Calendar.MONTH);
        int day = c.get(java.util.Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = year+"-"+month+"-"+dayOfMonth;
        edit.setText(date);
    }
}
