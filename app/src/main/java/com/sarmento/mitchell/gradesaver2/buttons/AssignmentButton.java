package com.sarmento.mitchell.gradesaver2.buttons;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.dialogs.OptionsDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Locale;

public class AssignmentButton extends Button implements View.OnLongClickListener {
    private Context context;
    private Assignment assignment;
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;

    public AssignmentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Assignment assignment, int termPosition, int sectionPosition, int assignmentPosition) {
        this.assignment         = assignment;
        this.termPosition       = termPosition;
        this.sectionPosition    = sectionPosition;
        this.assignmentPosition = assignmentPosition;

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
        setOnLongClickListener(this);
        setBackgroundColor(ResourcesCompat.getColor(getResources(),
                getButtonColor(scorePercent), null));
    }

    private int getButtonColor(double scorePercent) {
        Section section = Academics.getInstance().getCurrentTerms().get(termPosition)
                .getSections().get(sectionPosition);
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

    @Override
    public boolean onLongClick(View v) {
        OptionsDialogFragment dialog = new OptionsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Academics.TERM_POSITION, termPosition);
        bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
        bundle.putInt(Academics.ASSIGNMENT_POSITION, assignmentPosition);
        bundle.putInt(OptionsDialogFragment.ITEM_TYPE, OptionsDialogFragment.ASSIGNMENT);
        dialog.setArguments(bundle);
        dialog.show(((Activity) context).getFragmentManager(), context.getString(R.string.options));
        return true;
    }
}
