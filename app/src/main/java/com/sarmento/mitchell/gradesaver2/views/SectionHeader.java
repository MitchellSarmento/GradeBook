package com.sarmento.mitchell.gradesaver2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Locale;

public class SectionHeader extends LinearLayout {
    private Context context;
    private Section section;
    private TextView gradeThresholdA;
    private TextView gradeThresholdB;
    private TextView gradeThresholdC;
    private TextView gradeThresholdD;
    private TextView gradeThresholdF;
    private TextView scoreHomework;
    private TextView scoreQuiz;
    private TextView scoreMidterm;
    private TextView scoreFinal;
    private TextView scoreProject;
    private TextView scoreOther;
    private TextView grade;

    public SectionHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, R.layout.header_section, this);
        gradeThresholdA = (TextView) findViewById(R.id.grade_threshold_a);
        gradeThresholdB = (TextView) findViewById(R.id.grade_threshold_b);
        gradeThresholdC = (TextView) findViewById(R.id.grade_threshold_c);
        gradeThresholdD = (TextView) findViewById(R.id.grade_threshold_d);
        gradeThresholdF = (TextView) findViewById(R.id.grade_threshold_f);
        scoreHomework   = (TextView) findViewById(R.id.score_homework);
        scoreQuiz       = (TextView) findViewById(R.id.score_quiz);
        scoreMidterm    = (TextView) findViewById(R.id.score_midterm);
        scoreFinal      = (TextView) findViewById(R.id.score_final);
        scoreProject    = (TextView) findViewById(R.id.score_project);
        scoreOther      = (TextView) findViewById(R.id.score_other);
        grade           = (TextView) findViewById(R.id.grade);
    }

    public void init(Section section) {
        this.section = section;

        SparseArray<Double> gradeThresholds = section.getGradeThresholds();
        gradeThresholdA.setText(context.getString(R.string.a) + " " +
                gradeThresholds.get(Section.HIGH_A) + " - " + gradeThresholds.get(Section.LOW_A));
        gradeThresholdB.setText(context.getString(R.string.b) + " " +
                gradeThresholds.get(Section.HIGH_B) + " - " + gradeThresholds.get(Section.LOW_B));
        gradeThresholdC.setText(context.getString(R.string.c) + " " +
                gradeThresholds.get(Section.HIGH_C) + " - " + gradeThresholds.get(Section.LOW_C));
        gradeThresholdD.setText(context.getString(R.string.d) + " " +
                gradeThresholds.get(Section.HIGH_D) + " - " + gradeThresholds.get(Section.LOW_D));
        gradeThresholdF.setText(context.getString(R.string.f) + " " +
                gradeThresholds.get(Section.HIGH_F) + " - " + gradeThresholds.get(Section.LOW_F));

        update(section);
    }

    public void update(Section section) {
        SparseArray<Double> assignmentWeights = section.getAssignmentWeights();
        SparseArray<Double> scores            = section.getScores();
        SparseArray<Double> maxScores         = section.getMaxScores();

        String scoreString;

        scoreString = scoreToString(scores.get(Section.HOMEWORK),
                maxScores.get(Section.HOMEWORK), Section.HOMEWORK);
        scoreHomework.setText(scoreString + "/" + assignmentWeights.get(Section.HOMEWORK) + "%");

        scoreString = scoreToString(scores.get(Section.QUIZ),
                maxScores.get(Section.QUIZ), Section.QUIZ);
        scoreQuiz.setText(scoreString + "/" + assignmentWeights.get(Section.QUIZ) + "%");

        scoreString = scoreToString(scores.get(Section.MIDTERM),
                maxScores.get(Section.MIDTERM), Section.MIDTERM);
        scoreMidterm.setText(scoreString + "/" + assignmentWeights.get(Section.MIDTERM) + "%");

        scoreString = scoreToString(scores.get(Section.FINAL),
                maxScores.get(Section.FINAL), Section.FINAL);
        scoreFinal.setText(scoreString + "/" + assignmentWeights.get(Section.FINAL) + "%");

        scoreString = scoreToString(scores.get(Section.PROJECT),
                maxScores.get(Section.PROJECT), Section.PROJECT);
        scoreProject.setText(scoreString + "/" + assignmentWeights.get(Section.PROJECT) + "%");

        scoreString = scoreToString(scores.get(Section.OTHER),
                maxScores.get(Section.OTHER), Section.OTHER);
        scoreOther.setText(scoreString + "/" + assignmentWeights.get(Section.OTHER) + "%");

        double scorePercent = section.getTotalScore() / section.getMaxScore() * 100;

        if (Double.isNaN(scorePercent)) {
            scoreString = "";
        } else {
            scoreString = String.format(Locale.getDefault(), "%.2f", scorePercent) + "% " +
                    section.getGrade();
        }
        grade.setText(context.getText(R.string.total) + " " + scoreString);
    }

    public String scoreToString(Double score, Double maxScore, int assignmentType) {
        if (score == null || maxScore == null || maxScore == 0) {
            return "-";
        }
        return String.format(Locale.getDefault(), "%.2f",
                score / maxScore * section.getAssignmentWeights().get(assignmentType));
    }
}
