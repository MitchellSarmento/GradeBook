package com.sarmento.mitchell.gradesaver2.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Schedule;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.ArrayList;
import java.util.List;

public class ScheduleEditAdapter extends RecyclerView.Adapter<ScheduleEditAdapter.ViewHolder> {
    private Activity activity;
    private List<Section> sections;
    private int termPosition;

    private SparseBooleanArray expandState = new SparseBooleanArray();
    private List<ViewHolder> holders       = new ArrayList<>();

    public ScheduleEditAdapter(Activity activity, List<Section> sections, int termPosition) {
        this.activity     = activity;
        this.sections     = sections;
        this.termPosition = termPosition;

        for (int i = 0; i < sections.size(); i++) {
            expandState.put(i, false);
        }
    }

    @Override
    public ScheduleEditAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_schedule_edit, parent, false);
        ViewHolder holder = new ViewHolder(inflatedView);
        holders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ScheduleEditAdapter.ViewHolder holder, int sectionPosition) {
        final Section section = sections.get(sectionPosition);

        holder.sectionHeader.setText(section.getSectionName());
        holder.sectionHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                holder.expandableLayout.toggle();
            }
        });

        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setExpanded(expandState.get(sectionPosition));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                expandState.put(holder.getAdapterPosition(), true);
            }

            @Override
            public void onPreClose() {
                expandState.put(holder.getAdapterPosition(), false);

                // update relevant schedule information
                section.getSchedule().updateSchedule(activity, holder, termPosition,
                        holder.getAdapterPosition());
            }
        });

        // set fields
        Schedule schedule = section.getSchedule();
        for (Schedule.Day day : Schedule.Day.values()) {
            setFields(schedule, holder, day.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public void closeAll() {
        for (ViewHolder holder : holders) {
            holder.expandableLayout.collapse();
        }
    }

    private void setFields(Schedule schedule, ViewHolder holder, int day) {
        holder.switches.get(day).setChecked(schedule.getActive().get(day));
        holder.startTimes.get(day).setText(schedule.getStartTimes().get(day));
        holder.endTimes.get(day).setText(schedule.getEndTimes().get(day));
        holder.locations.get(day).setText(schedule.getLocations().get(day));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionHeader;
        private ExpandableLinearLayout expandableLayout;

        public SparseArray<Switch> switches     = new SparseArray<>();
        public SparseArray<EditText> startTimes = new SparseArray<>();
        public SparseArray<EditText> endTimes   = new SparseArray<>();
        public SparseArray<EditText> locations  = new SparseArray<>();

        private ViewHolder(View v) {
            super(v);
            sectionHeader    = (TextView) v.findViewById(R.id.header_schedule_edit);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.details_schedule_edit);

            int[] switchIds    = {R.id.monday_switch, R.id.tuesday_switch, R.id.wednesday_switch,
                    R.id.thursday_switch, R.id.friday_switch, R.id.saturday_switch, R.id.sunday_switch};
            int[] startTimeIds = {R.id.monday_start, R.id.tuesday_start, R.id.wednesday_start,
                    R.id.thursday_start, R.id.friday_start, R.id.saturday_start, R.id.sunday_start};
            int[] endTimeIds   = {R.id.monday_end, R.id.tuesday_end, R.id.wednesday_end,
                    R.id.thursday_end, R.id.friday_end, R.id.saturday_end, R.id.sunday_end};
            int[] locationIds  = {R.id.monday_location, R.id.tuesday_location, R.id.wednesday_location,
                    R.id.thursday_location, R.id.friday_location, R.id.saturday_location,
                    R.id.sunday_location};

            for (Schedule.Day day : Schedule.Day.values()) {
                int dayValue = day.getValue();
                switches.put(dayValue, (Switch) v.findViewById(switchIds[dayValue]));
                startTimes.put(dayValue, (EditText) v.findViewById(startTimeIds[dayValue]));
                endTimes.put(dayValue, (EditText) v.findViewById(endTimeIds[dayValue]));
                locations.put(dayValue, (EditText) v.findViewById(locationIds[dayValue]));
            }
        }
    }
}
