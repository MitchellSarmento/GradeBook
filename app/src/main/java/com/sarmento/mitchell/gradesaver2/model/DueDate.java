package com.sarmento.mitchell.gradesaver2.model;


import android.content.ContentValues;
import android.content.Context;

import java.util.Calendar;

public class DueDate {
    private String dueDateName;
    private boolean complete;
    private Calendar date;

    public DueDate(String dueDateName, boolean complete, Calendar date) {
        this.dueDateName = dueDateName;
        this.complete    = complete;
        this.date        = date;
    }

    public void updateDueDate(Context context, String dueDateName, Calendar date,
                              int termPosition, int sectionPosition, int dueDatePosition) {
        this.dueDateName = dueDateName;
        this.date        = date;

        // update the DueDate in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_DUE_DATES_NAME, dueDateName);
        updateValues.put(DBHelper.KEY_DUE_DATES_YEAR, date.get(Calendar.YEAR));
        updateValues.put(DBHelper.KEY_DUE_DATES_MONTH, date.get(Calendar.MONTH));
        updateValues.put(DBHelper.KEY_DUE_DATES_DAY, date.get(Calendar.DAY_OF_MONTH));
        db.updateDueDate(updateValues, termPosition, sectionPosition, dueDatePosition);
    }

    public String getDueDateName() {
        return dueDateName;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(Context context, boolean complete, int termPosition,
                            int sectionPosition, int dueDatePosition) {
        this.complete = complete;

        // update the DueDate in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_DUE_DATES_COMPLETE, complete);
        db.updateDueDate(updateValues, termPosition, sectionPosition, dueDatePosition);
    }

    public Calendar getDate() {
        return date;
    }
}
