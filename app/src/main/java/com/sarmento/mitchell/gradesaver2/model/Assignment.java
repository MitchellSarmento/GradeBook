package com.sarmento.mitchell.gradesaver2.model;



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
