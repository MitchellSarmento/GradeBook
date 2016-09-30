package com.sarmento.mitchell.gradesaver.model;



public class Assignment {
    String assignmentName;
    String assignmentCategory;
    double maxScore;
    double score;
    char grade;

    public Assignment(String assignmentName, String assignmentCategory, double maxScore, double score, char grade) {
        this.assignmentName = assignmentName;
        this.assignmentCategory = assignmentCategory;
        this.maxScore = maxScore;
        this.score = score;
        this.grade = grade;
    }
}
