package com.sarmento.mitchell.gradesaver2.model;


import android.content.Context;
import android.util.SparseArray;

import com.sarmento.mitchell.gradesaver2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Section {
    public static final int HIGH_A = 0;
    public static final int LOW_A  = 1;
    public static final int HIGH_B = 2;
    public static final int LOW_B  = 3;
    public static final int HIGH_C = 4;
    public static final int LOW_C  = 5;
    public static final int HIGH_D = 6;
    public static final int LOW_D  = 7;
    public static final int HIGH_F = 8;
    public static final int LOW_F  = 9;

    public static final int HOMEWORK = 0;
    public static final int QUIZZES  = 1;
    public static final int MIDTERM  = 2;
    public static final int FINAL    = 3;
    public static final int PROJECT  = 4;
    public static final int OTHER    = 5;

    String sectionName;
    double maxScore;
    double totalScore;
    char grade;
    SparseArray<Double> gradeThresholds;
    SparseArray<Double> assignmentWeights;
    SparseArray<Double> scores;
    List<Assignment> assignments;
    List<DueDate> dueDates;

    // constructor for creating a new section
    public Section(Context context, String sectionName) {
        this.sectionName  = sectionName;
        maxScore          = 0.0;
        totalScore        = 0.0;
        grade             = 'A';
        gradeThresholds   = new SparseArray<>();
        assignmentWeights = new SparseArray<>();
        scores            = new SparseArray<>();
        assignments       = new ArrayList<>();
        dueDates          = new ArrayList<>();

        gradeThresholds.put(HIGH_A, 100.0);
        gradeThresholds.put(LOW_A, 90.0);
        gradeThresholds.put(HIGH_B, 89.0);
        gradeThresholds.put(LOW_B, 80.0);
        gradeThresholds.put(HIGH_C, 79.0);
        gradeThresholds.put(LOW_C, 70.0);
        gradeThresholds.put(HIGH_D, 69.0);
        gradeThresholds.put(LOW_D, 60.0);
        gradeThresholds.put(HIGH_F, 59.0);
        gradeThresholds.put(LOW_F, 0.0);

        assignmentWeights.put(HOMEWORK, 30.0);
        assignmentWeights.put(QUIZZES, 10.0);
        assignmentWeights.put(MIDTERM, 20.0);
        assignmentWeights.put(FINAL, 30.0);
        assignmentWeights.put(PROJECT, 10.0);
        assignmentWeights.put(OTHER, 0.0);

        scores.put(HOMEWORK, -1.0);
        scores.put(QUIZZES, -1.0);
        scores.put(MIDTERM, -1.0);
        scores.put(FINAL, -1.0);
        scores.put(PROJECT, -1.0);
        scores.put(OTHER, -1.0);
    }

    // constructor for loading an existing section
    public Section(String sectionName, SparseArray<Double> gradeThresholds, SparseArray<Double> assignmentWeights,
                   SparseArray<Double> scores, double totalScore, double maxScore, char grade,
                   List<Assignment> assignments, List<DueDate> dueDates) {

    }

    public String getSectionName() {
        return sectionName;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public char getGrade() {
        return grade;
    }
}
