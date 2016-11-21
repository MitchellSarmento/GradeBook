package com.sarmento.mitchell.gradesaver2.model;


import java.util.Date;

public class DueDate {
    String assignmentName;
    boolean complete;
    Date dueDate;

    public DueDate(String assignmentName, boolean complete, Date dueDate) {
        this.assignmentName = assignmentName;
        this.complete = complete;
        this.dueDate = dueDate;
    }
}
