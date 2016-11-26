package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.activities.AssignmentsActivity;
import com.sarmento.mitchell.gradesaver2.activities.SectionsActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.Locale;

public class SectionButton extends Button implements View.OnClickListener {
    private Context context;
    private int sectionPosition;

    public SectionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Section section, int position) {
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
