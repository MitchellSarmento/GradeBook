package com.sarmento.mitchell.gradesaver2.buttons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentsActivity;
import com.sarmento.mitchell.gradesaver2.dialogs.OptionsDialogFragment;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Locale;

public class SectionButton extends Button implements View.OnClickListener, View.OnLongClickListener {
    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;

    private Context context;
    private Section section;

    public SectionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Section section, int termPosition, int sectionPosition) {
        this.section         = section;
        this.termPosition    = termPosition;
        this.sectionPosition = sectionPosition;

        double scorePercent = section.getTotalScore() / section.getMaxScore() * 100;

        setButtonText(scorePercent);
        setBackgroundColor(ResourcesCompat.getColor(getResources(),
                getButtonColor(scorePercent), null));

        setOnClickListener(this);
        if (!academics.inArchive()) {
            setOnLongClickListener(this);
        }
    }

    private void setButtonText(double scorePercent) {
        if (Double.isNaN(scorePercent)) {
            scorePercent = 100.0;
        }

        String grade = section.getGrade();

        // first line
        String sectionName = section.getSectionName();

        // second line
        String scoreString = String.format(Locale.getDefault(), "%.2f", scorePercent) + "% " +
                grade;

        // third line
        String finalGrade = section.getFinalGrade();
        if (!finalGrade.equals("")) {
            finalGrade = "\n" + context.getString(R.string.final_grade) + ": " + finalGrade;
        }

        setText(sectionName + "\n" + scoreString + finalGrade);
    }

    private int getButtonColor(double scorePercent) {
        SparseArray<Double> gradeThresholds = section.getGradeThresholds();

        if (scorePercent >= gradeThresholds.get(Section.GradeThreshold.LOW_A.getValue()) ||
                Double.isNaN(scorePercent)) {
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
        Intent intent = new Intent(context, AssignmentsActivity.class);
        intent.putExtra(Academics.TERM_POSITION, termPosition);
        intent.putExtra(Academics.SECTION_POSITION, sectionPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        OptionsDialogFragment dialog = new OptionsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Academics.TERM_POSITION, termPosition);
        bundle.putInt(Academics.SECTION_POSITION, sectionPosition);
        bundle.putInt(OptionsDialogFragment.ITEM_TYPE, OptionsDialogFragment.SECTION);
        dialog.setArguments(bundle);
        dialog.show(((Activity) context).getFragmentManager(), context.getString(R.string.options));
        return true;
    }
}
