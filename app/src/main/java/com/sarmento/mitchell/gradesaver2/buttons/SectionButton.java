package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.activities.SectionActivity;
import com.sarmento.mitchell.gradesaver2.activities.TermActivity;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Section;

public class SectionButton extends Button implements View.OnClickListener {
    private Context context;
    int sectionPosition;

    public SectionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Section section, final int position) {
        sectionPosition = position;
        setText(section.getSectionName() + "\n" +
            Double.toString(section.getTotalScore()) + "/" +
            Double.toString(section.getMaxScore()) + "  " +
            section.getGrade());
        setAllCaps(false);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, SectionActivity.class);
        intent.putExtra(Academics.TERM_POSITION, ((TermActivity) context).getTermPosition());
        intent.putExtra(Academics.SECTION_POSITION, sectionPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
