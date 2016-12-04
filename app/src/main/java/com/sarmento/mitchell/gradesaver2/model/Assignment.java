package com.sarmento.mitchell.gradesaver2.model;


import android.content.ContentValues;
import android.content.Context;

public class Assignment {
    private String assignmentName;
    private String assignmentType;
    private double score;
    private double maxScore;
    private String grade;

    public Assignment(String assignmentName, String assignmentType, double score, double maxScore, String grade) {
        this.assignmentName = assignmentName;
        this.assignmentType = assignmentType;
        this.score = score;
        this.maxScore = maxScore;
        this.grade = grade;
    }

    public void updateAssignment(Context context, String assignmentName,
                                 double score, double maxScore, String assignmentType,
                                 int termPosition, int sectionPosition, int assignmentPosition) {
        DBHelper db = new DBHelper(context);
        this.assignmentName = assignmentName;
        this.score = score;
        this.maxScore = maxScore;
        this.assignmentType = assignmentType;

        // update the Assignment in the database
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_NAME, assignmentName);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_SCORE, score);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_MAX_SCORE, maxScore);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_TYPE, assignmentType);
        db.updateAssignment(updateValues, termPosition, sectionPosition, assignmentPosition);
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public double getScore() {
        return score;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public String getGrade() {
        return grade;
    }
}
