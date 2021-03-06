package com.sarmento.mitchell.gradesaver2.buttons;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.dialogs.OptionsDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.DueDate;
import com.sarmento.mitchell.gradesaver2.widgets.DueDatesWidgetProvider;

import java.util.Calendar;
import java.util.Locale;

public class DueDateButton extends Button implements View.OnClickListener, View.OnLongClickListener {
    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;
    private int dueDatePosition;

    private Context context;
    private DueDate dueDate;
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

        complete      = dueDate.isComplete();
        daysRemaining = getDaysRemaining(dueDate.getDate());

        setButtonText();
        setButtonColor();

        if (!academics.inArchive()) {
            setOnClickListener(this);
            setOnLongClickListener(this);
        }
    }

    private void setButtonText() {
        Calendar due = dueDate.getDate();

        // first line
        String dueDateName = dueDate.getDueDateName();

        // second line
        Locale locale = Locale.getDefault();
        String dateString = due.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale) +
                ", " + due.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale) +
                " " + due.get(Calendar.DAY_OF_MONTH) + ", " + due.get(Calendar.YEAR);

        // third line
        String daysRemainingString = daysRemainingToString();

        setText(dueDateName + "\n" + dateString + "\n" + daysRemainingString);
    }

    private void setButtonColor() {
        if (complete) {
            setBackgroundColor(Color.LTGRAY);
        } else {
            setBackgroundColor(ResourcesCompat.getColor(getResources(),
                    getButtonColor(), null));
        }
    }

    private int getDaysRemaining(Calendar due) {
        Calendar today = Calendar.getInstance();

        return (int) Math.ceil((float) (due.getTimeInMillis() - today.getTimeInMillis())
                / (24 * 60 * 60 * 1000));
    }

    private String daysRemainingToString() {
        if (complete) {
            return "Complete";
        }

        if (daysRemaining < 0) {
            return "Past Due!";
        } else if (daysRemaining == 0) {
            return "Due Today!";
        } else if (daysRemaining == 1) {
            return "Due Tomorrow!";
        } else {
            return "Due in " + daysRemaining + " days.";
        }
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
        complete = !complete;
        setButtonText();
        setButtonColor();
        dueDate.setComplete(context, complete, termPosition, sectionPosition, dueDatePosition);
        DueDatesWidgetProvider.updateWidget(context, false);
    }

    @Override
    public boolean onLongClick(View v) {
        OptionsDialogFragment dialog = new OptionsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Academics.TERM_POSITION, termPosition);
        bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
        bundle.putInt(Academics.DUE_DATE_POSITION, dueDatePosition);
        bundle.putInt(OptionsDialogFragment.ITEM_TYPE, OptionsDialogFragment.DUE_DATE);
        dialog.setArguments(bundle);
        dialog.show(((Activity) context).getFragmentManager(), context.getString(R.string.options));
        return true;
    }
}