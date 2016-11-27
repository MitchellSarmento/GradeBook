package com.sarmento.mitchell.gradesaver2.model;


import android.content.ContentValues;
import android.content.Context;

import java.util.Calendar;
import java.util.Date;

public class DueDate {
    private String dueDateName;
    private boolean complete;
    private Calendar date;

    public DueDate(String dueDateName, boolean complete, Calendar date) {
        this.dueDateName = dueDateName;
        this.complete = complete;
        this.date = date;
    }

    public String getDueDateName() {
        return dueDateName;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(Context context, boolean complete, int termPosition,
                            int sectionPosition, int dueDatePosition) {
        DBHelper db = new DBHelper(context);
        this.complete = complete;

        // update the DueDate in the database
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_DUE_DATES_COMPLETE, complete);
        db.updateDueDate(updateValues, termPosition, sectionPosition, dueDatePosition);
    }

    public Calendar getDate() {
        return date;
    }
}
