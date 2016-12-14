package com.sarmento.mitchell.gradesaver2.views;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sarmento.mitchell.gradesaver2.model.Schedule;

public class TimePickerView extends TextView implements View.OnClickListener,
        View.OnLongClickListener{
    private Context context;

    public TimePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        // hide the keyboard if it was open
        InputMethodManager inputManager = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

        final boolean is24HourFormat = DateFormat.is24HourFormat(context);

        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                ((TimePickerView) v).setText(Schedule.timeToString(hour, minute, is24HourFormat));
            }
        }, 0, 0, is24HourFormat).show();
    }

    @Override
    public boolean onLongClick(View v) {
        ((TimePickerView) v).setText("");
        return true;
    }
}
