package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.model.Assignment;

public class AssignmentButton extends Button {

    public AssignmentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Assignment assignment, final int position) {
        setText(assignment.getAssignmentType() + "\n" +
            assignment.getAssignmentName() + "\n" +
            Double.toString(assignment.getScore()) + "/" +
            Double.toString(assignment.getMaxScore()) + "  " +
            assignment.getGrade());
        setAllCaps(false);
    }
}
