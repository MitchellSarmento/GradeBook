package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.AssignmentsActivity;
import com.sarmento.mitchell.gradesaver2.activities.SectionsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Locale;

public class SectionButton extends Button implements View.OnClickListener {
    private Context context;
    private Section section;
    private int sectionPosition;

    public SectionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Section section, int position) {
        this.section    = section;
        sectionPosition = position;

        String sectionName  = section.getSectionName();
        double scorePercent = section.getTotalScore() / section.getMaxScore() * 100;
        if (Double.isNaN(scorePercent)) {
            scorePercent = 100.0;
        }
        String grade        = section.getGrade();

        setText(sectionName + "\n" + String.format(Locale.getDefault(), "%.2f", scorePercent) +
                "% " + grade);
        setAllCaps(false);
        setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, AssignmentsActivity.class);
        intent.putExtra(Academics.TERM_POSITION, ((SectionsActivity) context).getTermPosition());
        intent.putExtra(Academics.SECTION_POSITION, sectionPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
