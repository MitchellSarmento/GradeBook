package com.sarmento.mitchell.gradesaver2.model;


import android.content.Context;
import android.util.SparseArray;

import com.sarmento.mitchell.gradesaver2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    public static final int QUIZ     = 1;
    public static final int MIDTERM  = 2;
    public static final int FINAL    = 3;
    public static final int PROJECT  = 4;
    public static final int OTHER    = 5;

    private String sectionName;
    private double maxScore;
    private double totalScore;
    private String grade;
    private SparseArray<Double> gradeThresholds;
    private SparseArray<Double> assignmentWeights;
    private SparseArray<Double> scores;
    private SparseArray<Double> maxScores;
    private List<Assignment> assignments;
    private List<DueDate> dueDates;

    // constructor for creating a new section
    public Section(String sectionName, SparseArray<Double> assignmentWeights, SparseArray<Double> gradeThresholds) {
        this.sectionName       = sectionName;
        maxScore               = 0.0;
        totalScore             = 0.0;
        grade                  = "A";
        this.assignmentWeights = assignmentWeights;
        this.gradeThresholds   = gradeThresholds;
        scores                 = new SparseArray<>();
        maxScores              = new SparseArray<>();
        assignments            = new ArrayList<>();
        dueDates               = new ArrayList<>();
    }

    // constructor for loading an existing section
    public Section(String sectionName, SparseArray<Double> gradeThresholds,
                   SparseArray<Double> assignmentWeights,
                   SparseArray<Double> scores, SparseArray<Double> maxScores,
                   double totalScore, double maxScore, String grade,
                   List<Assignment> assignments, List<DueDate> dueDates) {
        this.sectionName = sectionName;
        this.maxScore = maxScore;
        this.totalScore = totalScore;
        this.grade = grade;
        this.gradeThresholds = gradeThresholds;
        this.assignmentWeights = assignmentWeights;
        this.scores = scores;
        this.maxScores = maxScores;
        this.assignments = assignments;
        this.dueDates = dueDates;
    }

    public String getSectionName() {
        return sectionName;
    }

    public SparseArray<Double> getGradeThresholds() {
        return gradeThresholds;
    }

    public SparseArray<Double> getAssignmentWeights() {
        return assignmentWeights;
    }

    public SparseArray<Double> getScores() {
        return scores;
    }

    public SparseArray<Double> getMaxScores() {
        return maxScores;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public String getGrade() {
        return grade;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void addAssignment(Context context, Assignment assignment, int type, int sectionPosition) {
        DBHelper db = new DBHelper(context);
        assignments.add(assignment);
        db.addAssignment(assignment, sectionPosition+1);

        double score    = assignment.getScore();
        double maxScore = assignment.getMaxScore();

        totalScore += score;
        this.maxScore += maxScore;
        grade = calculateGrade(totalScore, this.maxScore);

        Double currentScore;
        Double currentMaxScore;
        switch (type) {
            case HOMEWORK:
                currentScore = scores.get(HOMEWORK);
                if (currentScore == null) { currentScore = 0.0; }
                scores.put(HOMEWORK, currentScore + score);
                currentMaxScore = maxScores.get(HOMEWORK);
                if (currentMaxScore == null) { currentMaxScore = 0.0; }
                maxScores.put(HOMEWORK, currentMaxScore + maxScore);
                break;
            case QUIZ:
                currentScore = scores.get(QUIZ);
                if (currentScore == null) { currentScore = 0.0; }
                scores.put(QUIZ, currentScore + score);
                currentMaxScore = maxScores.get(QUIZ);
                if (currentMaxScore == null) { currentMaxScore = 0.0; }
                maxScores.put(QUIZ, currentMaxScore + maxScore);
                break;
            case MIDTERM:
                currentScore = scores.get(MIDTERM);
                if (currentScore == null) { currentScore = 0.0; }
                scores.put(MIDTERM, currentScore + score);
                currentMaxScore = maxScores.get(MIDTERM);
                if (currentMaxScore == null) { currentMaxScore = 0.0; }
                maxScores.put(MIDTERM, currentMaxScore + maxScore);
                break;
            case FINAL:
                currentScore = scores.get(FINAL);
                if (currentScore == null) { currentScore = 0.0; }
                scores.put(FINAL, currentScore + score);
                currentMaxScore = maxScores.get(FINAL);
                if (currentMaxScore == null) { currentMaxScore = 0.0; }
                maxScores.put(FINAL, currentMaxScore + maxScore);
                break;
            case PROJECT:
                currentScore = scores.get(PROJECT);
                if (currentScore == null) { currentScore = 0.0; }
                scores.put(PROJECT, currentScore + score);
                currentMaxScore = maxScores.get(PROJECT);
                if (currentMaxScore == null) { currentMaxScore = 0.0; }
                maxScores.put(PROJECT, currentMaxScore + maxScore);
                break;
            case OTHER:
                currentScore = scores.get(OTHER);
                if (currentScore == null) { currentScore = 0.0; }
                scores.put(OTHER, currentScore + score);
                currentMaxScore = maxScores.get(OTHER);
                if (currentMaxScore == null) { currentMaxScore = 0.0; }
                maxScores.put(OTHER, currentMaxScore + maxScore);
                break;
            default:
                break;
        }

        //HashMap<String, Object> updateValues = new HashMap<>();
        //updateValues.put(DBHelper.KEY_SECTIONS_SCORE_)
        //db.updateSection()
    }

    public List<Integer> getRelevantAssignmentTypes() {
        List<Integer> assignmentTypes = new ArrayList<>();
        if (assignmentWeights.get(HOMEWORK) != 0) {
            assignmentTypes.add(HOMEWORK);
        }
        if (assignmentWeights.get(QUIZ) != 0) {
            assignmentTypes.add(QUIZ);
        }
        if (assignmentWeights.get(MIDTERM) != 0) {
            assignmentTypes.add(MIDTERM);
        }
        if (assignmentWeights.get(FINAL) != 0) {
            assignmentTypes.add(FINAL);
        }
        if (assignmentWeights.get(PROJECT) != 0) {
            assignmentTypes.add(PROJECT);
        }
        if (assignmentWeights.get(OTHER) != 0) {
            assignmentTypes.add(OTHER);
        }
        return assignmentTypes;
    }

    public String scoreToString(Double score, Double maxScore, int assignmentType) {
        if (score == null || maxScore == null) {
            return "-";
        }
        return String.format(Locale.getDefault(), "%.2f",
                score / maxScore * assignmentWeights.get(assignmentType));
    }

    public String calculateGrade(double score, double maxScore) {
        double gradePercent = score / maxScore * 100;

        if (gradePercent >= gradeThresholds.get(LOW_A)) {
            return "A";
        } else if (gradePercent >= gradeThresholds.get(LOW_B)) {
            return "B";
        } else if (gradePercent >= gradeThresholds.get(LOW_C)) {
            return "C";
        } else if (gradePercent >= gradeThresholds.get(LOW_D)) {
            return "D";
        } else {
            return "F";
        }
    }
}
