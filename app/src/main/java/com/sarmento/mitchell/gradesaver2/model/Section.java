package com.sarmento.mitchell.gradesaver2.model;


import android.content.Context;
import android.util.Log;
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

        double assignmentScore    = assignment.getScore();
        double assignmentMaxScore = assignment.getMaxScore();

        // get the current total score for this assignment type
        Double currentScore    = scores.get(type);
        Double currentMaxScore = maxScores.get(type);

        // if there is not current score then initialize the score and max score to 0
        if (currentScore == null) {
            currentScore = 0.0;
            currentMaxScore = 0.0;
        }

        // add the assignment scores to the total
        scores.put(type, currentScore + assignmentScore);
        maxScores.put(type, currentMaxScore + assignmentMaxScore);

        // get the weighted scores for each assignment type
        totalScore = 0;
        maxScore = 0;
        int[] assignmentTypes = {HOMEWORK, QUIZ, MIDTERM, FINAL, PROJECT, OTHER};
        for (int assignmentType : assignmentTypes) {
            // skip this type if there are no scores for it
            Double typeScore = scores.get(assignmentType);
            if (typeScore != null) {
                Double maxTypeScore = maxScores.get(assignmentType);
                totalScore += typeScore / maxTypeScore * assignmentWeights.get(assignmentType);
                maxScore += assignmentWeights.get(assignmentType);
            }
        }

        // calculate the overall grade for this section
        grade = calculateGrade(totalScore, maxScore);

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
