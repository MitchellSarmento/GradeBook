package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.DueDate;

import java.util.Calendar;

public class DueDateButton extends Button implements View.OnClickListener {
    private Context context;
    private DueDate dueDate;
    private int termPosition;
    private int sectionPosition;
    private int dueDatePosition;
    private boolean complete;
    private int daysRemaining;

    public DueDateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(DueDate dueDate, int termPosition, int sectionPosition, int dueDatePosition) {
        this.dueDate         = dueDate;
        this.termPosition    = termPosition;
        this.sectionPosition = sectionPosition;
        this.dueDatePosition = dueDatePosition;
        complete             = dueDate.isComplete();
        daysRemaining        = getDaysRemaining();

        String dueDateName = dueDate.getDueDateName();

        setText(dueDateName + "\n" + daysRemaining);
        setAllCaps(false);
        setOnClickListener(this);
        if (complete) {
            setBackgroundColor(Color.GRAY);
        } else {
            setBackgroundColor(ResourcesCompat.getColor(getResources(),
                    getButtonColor(), null));
        }
    }

    private int getDaysRemaining() {
        Calendar today = Calendar.getInstance();
        Calendar due   = dueDate.getDate();

        return (int) Math.ceil((float) (due.getTimeInMillis() - today.getTimeInMillis())
                / (24 * 60 * 60 * 1000));
    }

    private int getButtonColor() {
        if (daysRemaining >= 7) {
            return R.color.color_a;
        } else if (daysRemaining >= 5) {
            return R.color.color_b;
        } else if (daysRemaining >= 3) {
            return R.color.color_c;
        } else if (daysRemaining >= 2) {
            return R.color.color_d;
        } else {
            return R.color.color_f;
        }
    }

    @Override
    public void onClick(View v) {
        if (complete) {
            setBackgroundColor(ResourcesCompat.getColor(getResources(),
                    getButtonColor(), null));
            complete = !complete;
        } else {
            setBackgroundColor(Color.GRAY);
            complete = !complete;
        }
        dueDate.setComplete(context, complete, termPosition, sectionPosition, dueDatePosition);
    }
}