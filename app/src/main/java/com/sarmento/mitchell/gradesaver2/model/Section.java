package com.sarmento.mitchell.gradesaver2.model;


import android.content.ContentValues;
import android.content.Context;
import android.util.SparseArray;

import com.sarmento.mitchell.gradesaver2.R;

import java.util.ArrayList;
import java.util.List;

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
    private Schedule schedule;

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
        schedule               = new Schedule();
    }

    // constructor for loading an existing section
    public Section(String sectionName, SparseArray<Double> gradeThresholds,
                   SparseArray<Double> assignmentWeights,
                   SparseArray<Double> scores, SparseArray<Double> maxScores,
                   double totalScore, double maxScore, String grade,
                   List<Assignment> assignments, List<DueDate> dueDates, Schedule schedule) {
        this.sectionName       = sectionName;
        this.maxScore          = maxScore;
        this.totalScore        = totalScore;
        this.grade             = grade;
        this.gradeThresholds   = gradeThresholds;
        this.assignmentWeights = assignmentWeights;
        this.scores            = scores;
        this.maxScores         = maxScores;
        this.assignments       = assignments;
        this.dueDates          = dueDates;
        this.schedule          = schedule;
    }

    public void updateSection(Context context, String sectionName,
                              SparseArray<Double> assignmentWeights,
                              SparseArray<Double> gradeThresholds,
                              int termPosition, int sectionPosition) {
        this.sectionName       = sectionName;
        this.assignmentWeights = assignmentWeights;
        this.gradeThresholds   = gradeThresholds;

        // update the Section in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_SECTIONS_NAME, sectionName);
        updateValues.put(DBHelper.KEY_SECTIONS_WEIGHT_HOMEWORK,
                assignmentWeights.get(HOMEWORK));
        updateValues.put(DBHelper.KEY_SECTIONS_WEIGHT_QUIZ,
                assignmentWeights.get(QUIZ));
        updateValues.put(DBHelper.KEY_SECTIONS_WEIGHT_MIDTERM,
                assignmentWeights.get(MIDTERM));
        updateValues.put(DBHelper.KEY_SECTIONS_WEIGHT_FINAL,
                assignmentWeights.get(FINAL));
        updateValues.put(DBHelper.KEY_SECTIONS_WEIGHT_PROJECT,
                assignmentWeights.get(PROJECT));
        updateValues.put(DBHelper.KEY_SECTIONS_WEIGHT_OTHER,
                assignmentWeights.get(OTHER));
        updateValues.put(DBHelper.KEY_SECTIONS_HIGH_A,
                gradeThresholds.get(HIGH_A));
        updateValues.put(DBHelper.KEY_SECTIONS_LOW_A,
                gradeThresholds.get(LOW_A));
        updateValues.put(DBHelper.KEY_SECTIONS_HIGH_B,
                gradeThresholds.get(HIGH_B));
        updateValues.put(DBHelper.KEY_SECTIONS_LOW_B,
                gradeThresholds.get(LOW_B));
        updateValues.put(DBHelper.KEY_SECTIONS_HIGH_C,
                gradeThresholds.get(HIGH_C));
        updateValues.put(DBHelper.KEY_SECTIONS_LOW_C,
                gradeThresholds.get(LOW_C));
        updateValues.put(DBHelper.KEY_SECTIONS_HIGH_D,
                gradeThresholds.get(HIGH_D));
        updateValues.put(DBHelper.KEY_SECTIONS_LOW_D,
                gradeThresholds.get(LOW_D));
        updateValues.put(DBHelper.KEY_SECTIONS_HIGH_F,
                gradeThresholds.get(HIGH_F));
        updateValues.put(DBHelper.KEY_SECTIONS_LOW_F,
                gradeThresholds.get(LOW_F));
        db.updateSection(updateValues, termPosition, sectionPosition);
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

    public List<DueDate> getDueDates() {
        return dueDates;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    // convert the Assignment type from String to int
    private int convertAssignmentType(Context context, String assignmentType) {
        int type;
        if (assignmentType.equals(context.getString(R.string.homework))) {
            type = Section.HOMEWORK;
        } else if (assignmentType.equals(context.getString(R.string.quiz))) {
            type = Section.QUIZ;
        } else if (assignmentType.equals(context.getString(R.string.midterm))) {
            type = Section.MIDTERM;
        } else if (assignmentType.equals(context.getString(R.string.string_final))) {
            type = Section.FINAL;
        } else if (assignmentType.equals(context.getString(R.string.project))) {
            type = Section.PROJECT;
        } else {
            type = Section.OTHER;
        }
        return type;
    }

    // get the database keys associated with this Assignment type
    private String[] getColumnKeys(int type) {
        String keyScore = "";
        String keyMaxScore = "";
        switch (type) {
            case HOMEWORK:
                keyScore    = DBHelper.KEY_SECTIONS_SCORE_HOMEWORK;
                keyMaxScore = DBHelper.KEY_SECTIONS_MAX_SCORE_HOMEWORK;
                break;
            case QUIZ:
                keyScore    = DBHelper.KEY_SECTIONS_SCORE_QUIZ;
                keyMaxScore = DBHelper.KEY_SECTIONS_MAX_SCORE_QUIZ;
                break;
            case MIDTERM:
                keyScore    = DBHelper.KEY_SECTIONS_SCORE_MIDTERM;
                keyMaxScore = DBHelper.KEY_SECTIONS_MAX_SCORE_MIDTERM;
                break;
            case FINAL:
                keyScore    = DBHelper.KEY_SECTIONS_SCORE_FINAL;
                keyMaxScore = DBHelper.KEY_SECTIONS_MAX_SCORE_FINAL;
                break;
            case PROJECT:
                keyScore    = DBHelper.KEY_SECTIONS_SCORE_PROJECT;
                keyMaxScore = DBHelper.KEY_SECTIONS_MAX_SCORE_PROJECT;
                break;
            case OTHER:
                keyScore    = DBHelper.KEY_SECTIONS_SCORE_OTHER;
                keyMaxScore = DBHelper.KEY_SECTIONS_MAX_SCORE_OTHER;
                break;
            default:
                break;
        }
        return new String[]{keyScore, keyMaxScore};
    }

    public void addAssignment(Context context, Assignment assignment, int termPosition,
                              int sectionPosition, int assignmentPosition) {
        assignments.add(assignment);
        DBHelper db = new DBHelper(context);
        db.addAssignment(assignment, termPosition, sectionPosition, assignmentPosition);

        // convert the assignment type from String to int
        int type = convertAssignmentType(context, assignment.getAssignmentType());

        double assignmentScore    = assignment.getScore();
        double assignmentMaxScore = assignment.getMaxScore();

        // get the current total score for this assignment type
        Double currentScore    = scores.get(type);
        Double currentMaxScore = maxScores.get(type);

        // if there is not current score then initialize the score and max score to 0
        if (currentScore == null) {
            currentScore    = 0.0;
            currentMaxScore = 0.0;
        }

        // add the assignment scores to the total
        scores.put(type, currentScore + assignmentScore);
        maxScores.put(type, currentMaxScore + assignmentMaxScore);

        // calculate the overall grade for this section
        grade = calculateGrade();

        // get the columns to update
        String[] columns = getColumnKeys(type);

        // update the Section in the database
        ContentValues updateValues = new ContentValues();
        updateValues.put(columns[0], scores.get(type));
        updateValues.put(columns[1], maxScores.get(type));
        updateValues.put(DBHelper.KEY_SECTIONS_SCORE_TOTAL, totalScore);
        updateValues.put(DBHelper.KEY_SECTIONS_MAX_SCORE_TOTAL, maxScore);
        updateValues.put(DBHelper.KEY_SECTIONS_GRADE, grade);
        db.updateSection(updateValues, termPosition, sectionPosition);
    }

    public void removeAssignment(Context context, int termPosition, int sectionPosition,
                                 int assignmentPosition) {
        Assignment assignment = assignments.get(assignmentPosition);

        // convert the assignment type from String to int
        int type = convertAssignmentType(context, assignment.getAssignmentType());

        Double currentScore    = scores.get(type);
        Double currentMaxScore = maxScores.get(type);
        double assignmentScore    = assignment.getScore();
        double assignmentMaxScore = assignment.getMaxScore();
        double newScore = currentScore - assignmentScore;
        double newMaxScore = currentMaxScore - assignmentMaxScore;

        if (newMaxScore == 0) {
            scores.delete(type);
            maxScores.delete(type);
        } else {
            scores.put(type, newScore);
            maxScores.put(type, newMaxScore);
        }

        // calculate the overall grade for this section
        grade = calculateGrade();

        assignments.remove(assignmentPosition);

        DBHelper db = new DBHelper(context);
        db.removeAssignment(termPosition, sectionPosition, assignmentPosition);

        // get the columns to update
        String[] columns = getColumnKeys(type);

        // update the Section in the database
        ContentValues updateValues = new ContentValues();
        updateValues.put(columns[0], scores.get(type));
        updateValues.put(columns[1], maxScores.get(type));
        updateValues.put(DBHelper.KEY_SECTIONS_SCORE_TOTAL, totalScore);
        updateValues.put(DBHelper.KEY_SECTIONS_MAX_SCORE_TOTAL, maxScore);
        updateValues.put(DBHelper.KEY_SECTIONS_GRADE, grade);
        db.updateSection(updateValues, termPosition, sectionPosition);
    }

    public void addDueDate(Context context, DueDate dueDate, int termPosition,
                           int sectionPosition, int dueDatePosition) {
        dueDates.add(dueDate);
        DBHelper db = new DBHelper(context);
        db.addDueDate(dueDate, termPosition, sectionPosition, dueDatePosition);
    }

    public void removeDueDate(Context context, int termPosition, int sectionPosition,
                              int dueDatePosition) {
        dueDates.remove(dueDatePosition);
        DBHelper db = new DBHelper(context);
        db.removeDueDate(termPosition, sectionPosition, dueDatePosition);
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

    private String calculateGrade() {
        // get the weighted scores for each assignment type
        totalScore = 0;
        maxScore   = 0;
        int[] assignmentTypes = {HOMEWORK, QUIZ, MIDTERM, FINAL, PROJECT, OTHER};
        for (int assignmentType : assignmentTypes) {
            // skip this type if there are no scores for it
            Double typeScore    = scores.get(assignmentType);
            Double typeMaxScore = maxScores.get(assignmentType);
            if (typeMaxScore != null && typeMaxScore != 0) {
                totalScore += typeScore / typeMaxScore * assignmentWeights.get(assignmentType);
                maxScore += assignmentWeights.get(assignmentType);
            }
        }

        double scorePercent = totalScore / maxScore * 100;

        if (scorePercent >= gradeThresholds.get(LOW_A) ||
                Double.isNaN(scorePercent)) {
            return "A";
        } else if (scorePercent >= gradeThresholds.get(LOW_B)) {
            return "B";
        } else if (scorePercent >= gradeThresholds.get(LOW_C)) {
            return "C";
        } else if (scorePercent >= gradeThresholds.get(LOW_D)) {
            return "D";
        } else {
            return "F";
        }
    }

    public String calculateAssignmentGrade(double myScore, double maxScore) {
        double scorePercent = myScore / maxScore * 100;

        if (scorePercent >= gradeThresholds.get(LOW_A) ||
                Double.isNaN(scorePercent)) {
            return "A";
        } else if (scorePercent >= gradeThresholds.get(LOW_B)) {
            return "B";
        } else if (scorePercent >= gradeThresholds.get(LOW_C)) {
            return "C";
        } else if (scorePercent >= gradeThresholds.get(LOW_D)) {
            return "D";
        } else {
            return "F";
        }
    }
}
