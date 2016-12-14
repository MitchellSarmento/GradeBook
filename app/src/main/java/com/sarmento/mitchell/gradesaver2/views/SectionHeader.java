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
    private TextView[] gradeThresholdViews;
    private TextView[] scoreViews;
    private TextView grade;

    public SectionHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, R.layout.header_section, this);

        gradeThresholdViews = new TextView[] {
                (TextView) findViewById(R.id.grade_threshold_a),
                (TextView) findViewById(R.id.grade_threshold_b),
                (TextView) findViewById(R.id.grade_threshold_c),
                (TextView) findViewById(R.id.grade_threshold_d),
                (TextView) findViewById(R.id.grade_threshold_f)
        };
        scoreViews          = new TextView[] {
                (TextView) findViewById(R.id.score_homework),
                (TextView) findViewById(R.id.score_quiz),
                (TextView) findViewById(R.id.score_midterm),
                (TextView) findViewById(R.id.score_final),
                (TextView) findViewById(R.id.score_project),
                (TextView) findViewById(R.id.score_other)
        };
        grade               = (TextView) findViewById(R.id.grade);
    }

    public void init(Section section) {
        this.section = section;
        int[] stringIds = {R.string.a, R.string.b, R.string.c, R.string.d, R.string.f};

        SparseArray<Double> gradeThresholds = section.getGradeThresholds();
        for (Section.GradeThreshold threshold : Section.GradeThreshold.values()) {
            int thresholdValue = threshold.getValue();
            if (thresholdValue % 2 == 0) {
                gradeThresholdViews[thresholdValue/2].setText(
                        context.getString(stringIds[thresholdValue/2]) + " " +
                        gradeThresholds.get(thresholdValue) + " - " +
                        gradeThresholds.get(thresholdValue+1));
            }
        }

        update(section);
    }

    public void update(Section section) {
        SparseArray<Double> assignmentWeights = section.getAssignmentWeights();
        SparseArray<Double> scores            = section.getScores();
        SparseArray<Double> maxScores         = section.getMaxScores();

        String scoreString;
        for (Section.AssignmentType type : Section.AssignmentType.values()) {
            int typeValue = type.getValue();
            scoreString = scoreToString(scores.get(typeValue),
                    maxScores.get(typeValue), typeValue);
            scoreViews[typeValue].setText(scoreString + "/" +
                    assignmentWeights.get(typeValue) + "%");
        }

        double scorePercent = section.getTotalScore() / section.getMaxScore() * 100;

        scoreString = (Double.isNaN(scorePercent)) ?
                "" :
                String.format(Locale.getDefault(), "%.2f", scorePercent) + "% " + section.getGrade();
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
