package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.model.DueDate;

public class DueDateButton extends Button implements View.OnClickListener {
    private Context context;
    private int dueDatePosition;

    public DueDateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(DueDate dueDate, int position) {
        dueDatePosition = position;

        String dueDateName = dueDate.getDueDateName();

        setText(dueDateName);
        setAllCaps(false);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}