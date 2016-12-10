package com.sarmento.mitchell.gradesaver2.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Schedule;
import com.sarmento.mitchell.gradesaver2.model.Section;
import com.sarmento.mitchell.gradesaver2.views.ScheduleEditHeader;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class ScheduleEditAdapter extends RecyclerView.Adapter<ScheduleEditAdapter.ViewHolder> {
    private static final int UNSELECTED = -1;

    private Activity activity;
    private RecyclerView recyclerView;
    private List<Section> sections;
    private int termPosition;
    public int sectionPosition = UNSELECTED;

    private ScheduleEditHeader header;
    private Section section;
    private Schedule schedule;

    public ScheduleEditAdapter(Activity activity, RecyclerView recyclerView, List<Section> sections,
                               int termPosition) {
        this.activity     = activity;
        this.recyclerView = recyclerView;
        this.sections     = sections;
        this.termPosition = termPosition;
    }

    @Override
    public ScheduleEditAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_schedule_edit, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final ScheduleEditAdapter.ViewHolder holder, int sectionPosition) {
        holder.bind(sectionPosition);
        section = sections.get(sectionPosition);
        holder.scheduleEditHeader.init(holder, section, termPosition, sectionPosition);
        for (Schedule.Day day : Schedule.Day.values()) {
            setFields(holder, day.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    // used to save changes when user presses the back button without closing the ExpandableLayout
    public void updateSchedule() {
        header.updateSchedule();
    }

    private void setFields(ViewHolder holder, int day) {
        schedule = section.getSchedule();
        holder.scheduleEditHeader.setText(section.getSectionName());
        holder.location.setText(schedule.getLocation());
        holder.startTimes.get(day).setText(schedule.getStartTimes().get(day));
        holder.endTimes.get(day).setText(schedule.getEndTimes().get(day));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ScheduleEditHeader scheduleEditHeader;
        private ExpandableLayout expandableLayout;
        private int position;

        public EditText location;
        public SparseArray<TextView> startTimes = new SparseArray<>();
        public SparseArray<TextView> endTimes   = new SparseArray<>();

        private ViewHolder(View v) {
            super(v);
            scheduleEditHeader = (ScheduleEditHeader) v.findViewById(R.id.header_schedule_edit);
            expandableLayout   = (ExpandableLayout) v.findViewById(R.id.details_schedule_edit);

            int[] startTimeIds = {R.id.monday_start, R.id.tuesday_start, R.id.wednesday_start,
                    R.id.thursday_start, R.id.friday_start, R.id.saturday_start, R.id.sunday_start};
            int[] endTimeIds   = {R.id.monday_end, R.id.tuesday_end, R.id.wednesday_end,
                    R.id.thursday_end, R.id.friday_end, R.id.saturday_end, R.id.sunday_end};

            location = (EditText) v.findViewById(R.id.location);
            for (Schedule.Day day : Schedule.Day.values()) {
                int dayValue = day.getValue();
                startTimes.put(dayValue, (TextView) v.findViewById(startTimeIds[dayValue]));
                endTimes.put(dayValue, (TextView) v.findViewById(endTimeIds[dayValue]));
            }

            scheduleEditHeader.setOnClickListener(this);
        }

        public void bind(int position) {
            this.position = position;
            expandableLayout.collapse(false);
        }

        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) recyclerView
                    .findViewHolderForAdapterPosition(sectionPosition);
            if (holder != null) {
                holder.expandableLayout.collapse();

                // hide the keyboard if it was open
                InputMethodManager imm = (InputMethodManager)activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

            if (position == sectionPosition) {
                scheduleEditHeader.updateSchedule();
                sectionPosition = UNSELECTED;
            } else {
                expandableLayout.expand();
                header = scheduleEditHeader;
                sectionPosition = position;
            }
        }
    }
}
