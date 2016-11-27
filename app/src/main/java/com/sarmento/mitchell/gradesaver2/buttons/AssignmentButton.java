package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Assignment;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Locale;

public class AssignmentButton extends Button {
    private Section section;

    public AssignmentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Assignment assignment, Section section, int position) {
        this.section = section;

        String assignmentType = assignment.getAssignmentType();
        String assignmentName = assignment.getAssignmentName();
        double score          = assignment.getScore();
        double maxScore       = assignment.getMaxScore();
        double scorePercent   = score / maxScore * 100;
        String grade          = assignment.getGrade();

        setText(assignmentType + "\n" + assignmentName + "\n" +
                String.valueOf(score) + "/" + String.valueOf(maxScore) + "  " +
                String.format(Locale.getDefault(), "%.2f", scorePercent) + "%  " + grade);
        setAllCaps(false);
        setBackgroundColor(ResourcesCompat.getColor(getResources(),
                getButtonColor(scorePercent), null));
    }

    private int getButtonColor(double scorePercent) {
        SparseArray<Double> gradeThresholds = section.getGradeThresholds();

        if (scorePercent >= gradeThresholds.get(Section.LOW_A)) {
            return R.color.color_a;
        } else if (scorePercent >= gradeThresholds.get(Section.LOW_B)) {
            return R.color.color_b;
        } else if (scorePercent >= gradeThresholds.get(Section.LOW_C)) {
            return R.color.color_c;
        } else if (scorePercent >= gradeThresholds.get(Section.LOW_D)) {
            return R.color.color_d;
        } else {
            return R.color.color_f;
        }
    }
}
