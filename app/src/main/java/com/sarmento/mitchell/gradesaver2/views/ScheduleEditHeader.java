package com.sarmento.mitchell.gradesaver2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.adapters.ScheduleEditAdapter;
import com.sarmento.mitchell.gradesaver2.model.Schedule;
import com.sarmento.mitchell.gradesaver2.model.Section;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ScheduleEditHeader extends TextView {
    private Context context;
    private ScheduleEditAdapter.ViewHolder holder;
    private Section section;
    private int termPosition;
    private int sectionPosition;

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

    /*public TermButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(Term term, int termPosition) {
        this.termPosition = termPosition;

        String termName = term.getTermName();

        setText(termName);
        setOnClickListener(this);
        setOnLongClickListener(this);
        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_a, null));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, SectionsActivity.class);
        intent.putExtra(Academics.TERM_POSITION, termPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }*/
}
