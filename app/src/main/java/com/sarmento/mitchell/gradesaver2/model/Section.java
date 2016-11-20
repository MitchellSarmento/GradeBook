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

    String sectionName;
    double maxScore;
    double totalScore;
    char grade;
    SparseArray<Double> gradeThresholds;
    Map<String, Double> assignmentWeights;
    Map<String, Double> scores;
    List<Assignment> assignments;
    List<DueDate> dueDates;

    public Section(Context context, String sectionName) {
        this.sectionName  = sectionName;
        maxScore          = 0.0;
        totalScore        = 0.0;
        grade             = 'A';
        gradeThresholds   = new SparseArray<>();
        assignmentWeights = new HashMap<>();
        scores            = new HashMap<>();
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

        assignmentWeights.put(context.getString(R.string.homework), 30.0);
        assignmentWeights.put(context.getString(R.string.quizzes), 10.0);
        assignmentWeights.put(context.getString(R.string.midterm), 20.0);
        assignmentWeights.put(context.getString(R.string.string_final), 30.0);
        assignmentWeights.put(context.getString(R.string.project), 10.0);

        scores.put(context.getString(R.string.homework), -1.0);
        scores.put(context.getString(R.string.quizzes), -1.0);
        scores.put(context.getString(R.string.midterm), -1.0);
        scores.put(context.getString(R.string.string_final), -1.0);
        scores.put(context.getString(R.string.project), -1.0);
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
