package com.sarmento.mitchell.gradesaver2.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sarmento.mitchell.gradesaver2.model.Section;

public class SectionButton extends Button implements View.OnClickListener {
    private Context context;

    public SectionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Section section, final int position) {
        setText(section.getSectionName() + "\n" +
        Double.toString(section.getTotalScore()) + "/" +
        Double.toString(section.getMaxScore()) + "  " +
        section.getGrade());
        setAllCaps(false);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
