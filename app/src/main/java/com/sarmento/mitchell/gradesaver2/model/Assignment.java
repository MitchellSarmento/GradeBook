package com.sarmento.mitchell.gradesaver2.model;



public class Assignment {
    String assignmentName;
    String assignmentType;
    double score;
    double maxScore;
    String grade;

    public Assignment(String assignmentName, String assignmentType, double score, double maxScore, String grade) {
        this.assignmentName = assignmentName;
        this.assignmentType = assignmentType;
        this.score = score;
        this.maxScore = maxScore;
        this.grade = grade;
    }
}
