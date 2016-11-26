package com.sarmento.mitchell.gradesaver2.model;


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

    public Calendar getDate() {
        return date;
    }
}
