package com.sarmento.mitchell.gradesaver2.model;


import android.content.ContentValues;
import android.content.Context;
import android.util.SparseArray;

import com.sarmento.mitchell.gradesaver2.R;

import java.util.ArrayList;
import java.util.List;

public class Section {
    public enum GradeThreshold {
        HIGH_A(0), LOW_A(1), HIGH_B(2), LOW_B(3), HIGH_C(4), LOW_C(5),
        HIGH_D(6), LOW_D(7), HIGH_F(8), LOW_F(9);

        private int value;

        GradeThreshold(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum AssignmentType {
        HOMEWORK(0), QUIZ(1), MIDTERM(2), FINAL(3), PROJECT(4), OTHER(5);

        private int value;

        AssignmentType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private String sectionName;
    private double maxScore;
    private double totalScore;
    private String grade;
    private String finalGrade;
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
        finalGrade             = "";
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
                   double totalScore, double maxScore, String grade, String finalGrade,
                   List<Assignment> assignments, List<DueDate> dueDates, Schedule schedule) {
        this.sectionName       = sectionName;
        this.maxScore          = maxScore;
        this.totalScore        = totalScore;
        this.grade             = grade;
        this.finalGrade        = finalGrade;
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
        for (AssignmentType type : AssignmentType.values()) {
            int typeValue = type.getValue();
            updateValues.put(DBHelper.KEY_SECTIONS_WEIGHTS[typeValue],
                    assignmentWeights.get(typeValue));
        }
        for (GradeThreshold threshold : GradeThreshold.values()) {
            int thresholdValue = threshold.getValue();
            updateValues.put(DBHelper.KEY_SECTIONS_GRADE_THRESHOLDS[thresholdValue],
                    gradeThresholds.get(thresholdValue));
        }
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

    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(Context context, String finalGrade, int termPosition,
                              int sectionPosition) {
        this.finalGrade = finalGrade;

        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_SECTIONS_FINAL_GRADE, finalGrade);
        db.updateSection(updateValues, termPosition, sectionPosition);
    }

    // convert the Assignment type from String to int
    private int convertAssignmentType(Context context, String assignmentType) {
        int type;
        if (assignmentType.equals(context.getString(R.string.homework))) {
            type = AssignmentType.HOMEWORK.getValue();
        } else if (assignmentType.equals(context.getString(R.string.quiz))) {
            type = AssignmentType.QUIZ.getValue();
        } else if (assignmentType.equals(context.getString(R.string.midterm))) {
            type = AssignmentType.MIDTERM.getValue();
        } else if (assignmentType.equals(context.getString(R.string.string_final))) {
            type = AssignmentType.FINAL.getValue();
        } else if (assignmentType.equals(context.getString(R.string.project))) {
            type = AssignmentType.PROJECT.getValue();
        } else {
            type = AssignmentType.OTHER.getValue();
        }
        return type;
    }

    // get the database keys associated with this Assignment type
    private String[] getColumnKeys(int type) {
        return new String[]{DBHelper.KEY_SECTIONS_SCORES[type],
                DBHelper.KEY_SECTIONS_SCORES[type+1]};
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

    public void updateAssignment(Context context, String assignmentName,
                                 double score, double maxScore, String assignmentType,
                                 int termPosition, int sectionPosition, int assignmentPosition) {
        // get the Assignment to be updated
        Assignment assignment = assignments.get(assignmentPosition);

        // remove the old values
        int oldAssignmentType = convertAssignmentType(context, assignment.getAssignmentType());
        double oldScore       = assignment.getScore();
        double oldMaxScore    = assignment.getMaxScore();
        scores.put(oldAssignmentType, scores.get(oldAssignmentType) - oldScore);
        maxScores.put(oldAssignmentType, maxScores.get(oldAssignmentType) - oldMaxScore);

        // add the new values
        int newAssignmentType = convertAssignmentType(context, assignmentType);
        scores.put(newAssignmentType, score);
        maxScores.put(newAssignmentType, maxScore);

        // update the Assignment
        assignment.setAssignmentName(assignmentName);
        assignment.setScore(score);
        assignment.setMaxScore(maxScore);
        assignment.setAssignmentType(assignmentType);

        // recalculate the overall Section grade
        grade = calculateGrade();

        // update the Assignment in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_NAME, assignmentName);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_SCORE, score);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_MAX_SCORE, maxScore);
        updateValues.put(DBHelper.KEY_ASSIGNMENTS_TYPE, assignmentType);
        db.updateAssignment(updateValues, termPosition, sectionPosition, assignmentPosition);
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
        for (AssignmentType type : AssignmentType.values()) {
            int typeValue = type.getValue();
            if (assignmentWeights.get(typeValue) != 0) {
                assignmentTypes.add(typeValue);
            }
        }
        return assignmentTypes;
    }

    private String calculateGrade() {
        // get the weighted scores for each assignment type
        totalScore = 0;
        maxScore   = 0;
        for (AssignmentType type : AssignmentType.values()) {
            int typeValue = type.getValue();
            // skip this type if there are no scores for it
            Double typeScore    = scores.get(typeValue);
            Double typeMaxScore = maxScores.get(typeValue);
            if (typeMaxScore != null && typeMaxScore != 0) {
                totalScore += typeScore / typeMaxScore * assignmentWeights.get(typeValue);
                maxScore   += assignmentWeights.get(typeValue);
            }
        }

        double scorePercent = totalScore / maxScore * 100;

        if (scorePercent >= gradeThresholds.get(GradeThreshold.LOW_A.getValue()) ||
                Double.isNaN(scorePercent)) {
            return "A";
        } else if (scorePercent >= gradeThresholds.get(GradeThreshold.LOW_B.getValue())) {
            return "B";
        } else if (scorePercent >= gradeThresholds.get(GradeThreshold.LOW_C.getValue())) {
            return "C";
        } else if (scorePercent >= gradeThresholds.get(GradeThreshold.LOW_D.getValue())) {
            return "D";
        } else {
            return "F";
        }
    }

    public String calculateAssignmentGrade(double myScore, double maxScore) {
        double scorePercent = myScore / maxScore * 100;

        if (scorePercent >= gradeThresholds.get(GradeThreshold.LOW_A.getValue()) ||
                Double.isNaN(scorePercent)) {
            return "A";
        } else if (scorePercent >= gradeThresholds.get(GradeThreshold.LOW_B.getValue())) {
            return "B";
        } else if (scorePercent >= gradeThresholds.get(GradeThreshold.LOW_C.getValue())) {
            return "C";
        } else if (scorePercent >= gradeThresholds.get(GradeThreshold.LOW_D.getValue())) {
            return "D";
        } else {
            return "F";
        }
    }
}
