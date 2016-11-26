package com.sarmento.mitchell.gradesaver2.model;


import java.util.Date;

public class DueDate {
    private String dueDateName;
    private boolean complete;
    private Date dueDate;

    public DueDate(String dueDateName, boolean complete, Date dueDate) {
        this.dueDateName = dueDateName;
        this.complete = complete;
        this.dueDate = dueDate;
    }

    public String getDueDateName() {
        return dueDateName;
    }
}
