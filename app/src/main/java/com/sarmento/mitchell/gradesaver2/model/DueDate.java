package com.sarmento.mitchell.gradesaver2.model;


import java.util.Date;

public class DueDate {
    private String assignmentName;
    private boolean complete;
    private Date dueDate;

    public DueDate(String assignmentName, boolean complete, Date dueDate) {
        this.assignmentName = assignmentName;
        this.complete = complete;
        this.dueDate = dueDate;
    }
}
