package com.sarmento.mitchell.gradesaver2.model;



public class Assignment {
    String assignmentName;
    String assignmentType;
    double score;
    double maxScore;
    char grade;

    public Assignment(String assignmentName, String assignmentType, double score, double maxScore, char grade) {
        this.assignmentName = assignmentName;
        this.assignmentType = assignmentType;
        this.score = score;
        this.maxScore = maxScore;
        this.grade = grade;
    }
}
