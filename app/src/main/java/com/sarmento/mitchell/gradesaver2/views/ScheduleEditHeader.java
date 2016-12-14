package com.sarmento.mitchell.gradesaver2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.adapters.ScheduleEditAdapter;
import com.sarmento.mitchell.gradesaver2.model.Section;

public class ScheduleEditHeader extends TextView {
    private int termPosition;
    private int sectionPosition;

    private Context context;
    private ScheduleEditAdapter.ViewHolder holder;
    private Section section;

    public ScheduleEditHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(ScheduleEditAdapter.ViewHolder holder, Section section, int termPosition,
                     int sectionPosition) {
        this.holder          = holder;
        this.section         = section;
        this.termPosition    = termPosition;
        this.sectionPosition = sectionPosition;
    }

    public void updateSchedule() {
        section.getSchedule().updateSchedule(context, holder, termPosition,
                sectionPosition);
    }
}
