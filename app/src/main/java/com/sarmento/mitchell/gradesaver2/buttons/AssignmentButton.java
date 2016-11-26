package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.model.Assignment;

import java.util.Locale;

public class AssignmentButton extends Button {

    public AssignmentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Assignment assignment, final int position) {
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
    }
}
