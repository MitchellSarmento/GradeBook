package com.sarmento.mitchell.gradesaver2.buttons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentImagesActivity;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentsActivity;
import com.sarmento.mitchell.gradesaver2.dialogs.OptionsDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Locale;

public class AssignmentButton extends Button implements View.OnClickListener, View.OnLongClickListener {
    private Context context;
    private Assignment assignment;
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;

    public AssignmentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Assignment assignment, int termPosition,
                     int sectionPosition, int assignmentPosition) {
        this.assignment         = assignment;
        this.termPosition       = termPosition;
        this.sectionPosition    = sectionPosition;
        this.assignmentPosition = assignmentPosition;

        double score        = assignment.getScore();
        double maxScore     = assignment.getMaxScore();
        double scorePercent = score / maxScore * 100;

        setButtonText(score, maxScore, scorePercent);
        setOnClickListener(this);
        if (!Academics.getInstance().inArchive()) {
            setOnLongClickListener(this);
        }
        setBackgroundColor(ResourcesCompat.getColor(getResources(),
                getButtonColor(scorePercent), null));
    }

    private void setButtonText(double score, double maxScore, double scorePercent) {
        String assignmentType = assignment.getAssignmentType();
        String grade          = assignment.getGrade();

        // first line
        Spannable assignmentTypeSpan = new SpannableString(assignmentType);
        assignmentTypeSpan.setSpan(new RelativeSizeSpan(0.8f), 0, assignmentType.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        assignmentTypeSpan.setSpan(new ForegroundColorSpan(Color.GRAY), 0, assignmentType.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // second line
        String assignmentName = assignment.getAssignmentName();

        // third line
        String scoreString = String.valueOf(score) + "/" + String.valueOf(maxScore) + "  " +
                String.format(Locale.getDefault(), "%.2f", scorePercent) + "% " + grade;

        setText(assignmentTypeSpan);
        append("\n" + assignmentName + "\n" + scoreString);
    }

    private int getButtonColor(double scorePercent) {
        Academics academics = Academics.getInstance();
        Section section;

        if (academics.inArchive()) {
            section = academics.getArchivedTerms().get(termPosition)
                    .getSections().get(sectionPosition);
        } else {
            section = academics.getCurrentTerms().get(termPosition)
                    .getSections().get(sectionPosition);
        }

        SparseArray<Double> gradeThresholds = section.getGradeThresholds();
        if (scorePercent >= gradeThresholds.get(Section.GradeThreshold.LOW_A.getValue())) {
            return R.color.color_a;
        } else if (scorePercent >= gradeThresholds.get(Section.GradeThreshold.LOW_B.getValue())) {
            return R.color.color_b;
        } else if (scorePercent >= gradeThresholds.get(Section.GradeThreshold.LOW_C.getValue())) {
            return R.color.color_c;
        } else if (scorePercent >= gradeThresholds.get(Section.GradeThreshold.LOW_D.getValue())) {
            return R.color.color_d;
        } else {
            return R.color.color_f;
        }
    }

    @Override
    public void onClick(View v) {
        if (assignment.getImages().size() > 0) {
            Intent intent = new Intent(context, AssignmentImagesActivity.class);
            intent.putExtra(Academics.TERM_POSITION, termPosition);
            intent.putExtra(Academics.SECTION_POSITION, sectionPosition);
            intent.putExtra(Academics.ASSIGNMENT_POSITION, assignmentPosition);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
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
        dialog.show(((Activity)context).getFragmentManager(), context.getString(R.string.options));
        return true;
    }
}
