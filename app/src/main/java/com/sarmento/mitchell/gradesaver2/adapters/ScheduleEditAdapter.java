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
        setFields(schedule, holder, Schedule.MONDAY);
        setFields(schedule, holder, Schedule.TUESDAY);
        setFields(schedule, holder, Schedule.WEDNESDAY);
        setFields(schedule, holder, Schedule.THURSDAY);
        setFields(schedule, holder, Schedule.FRIDAY);
        setFields(schedule, holder, Schedule.SATURDAY);
        setFields(schedule, holder, Schedule.SUNDAY);
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

            // get switches
            switches.put(Schedule.MONDAY, (Switch) v.findViewById(R.id.monday_switch));
            switches.put(Schedule.TUESDAY, (Switch) v.findViewById(R.id.tuesday_switch));
            switches.put(Schedule.WEDNESDAY, (Switch) v.findViewById(R.id.wednesday_switch));
            switches.put(Schedule.THURSDAY, (Switch) v.findViewById(R.id.thursday_switch));
            switches.put(Schedule.FRIDAY, (Switch) v.findViewById(R.id.friday_switch));
            switches.put(Schedule.SATURDAY, (Switch) v.findViewById(R.id.saturday_switch));
            switches.put(Schedule.SUNDAY, (Switch) v.findViewById(R.id.sunday_switch));

            // get start time views
            startTimes.put(Schedule.MONDAY, (EditText) v.findViewById(R.id.monday_start));
            startTimes.put(Schedule.TUESDAY, (EditText) v.findViewById(R.id.tuesday_start));
            startTimes.put(Schedule.WEDNESDAY, (EditText) v.findViewById(R.id.wednesday_start));
            startTimes.put(Schedule.THURSDAY, (EditText) v.findViewById(R.id.thursday_start));
            startTimes.put(Schedule.FRIDAY, (EditText) v.findViewById(R.id.friday_start));
            startTimes.put(Schedule.SATURDAY, (EditText) v.findViewById(R.id.saturday_start));
            startTimes.put(Schedule.SUNDAY, (EditText) v.findViewById(R.id.sunday_start));

            // get end time views
            endTimes.put(Schedule.MONDAY, (EditText) v.findViewById(R.id.monday_end));
            endTimes.put(Schedule.TUESDAY, (EditText) v.findViewById(R.id.tuesday_end));
            endTimes.put(Schedule.WEDNESDAY, (EditText) v.findViewById(R.id.wednesday_end));
            endTimes.put(Schedule.THURSDAY, (EditText) v.findViewById(R.id.thursday_end));
            endTimes.put(Schedule.FRIDAY, (EditText) v.findViewById(R.id.friday_end));
            endTimes.put(Schedule.SATURDAY, (EditText) v.findViewById(R.id.saturday_end));
            endTimes.put(Schedule.SUNDAY, (EditText) v.findViewById(R.id.sunday_end));

            // get location views
            locations.put(Schedule.MONDAY, (EditText) v.findViewById(R.id.monday_location));
            locations.put(Schedule.TUESDAY, (EditText) v.findViewById(R.id.tuesday_location));
            locations.put(Schedule.WEDNESDAY, (EditText) v.findViewById(R.id.wednesday_location));
            locations.put(Schedule.THURSDAY, (EditText) v.findViewById(R.id.thursday_location));
            locations.put(Schedule.FRIDAY, (EditText) v.findViewById(R.id.friday_location));
            locations.put(Schedule.SATURDAY, (EditText) v.findViewById(R.id.saturday_location));
            locations.put(Schedule.SUNDAY, (EditText) v.findViewById(R.id.sunday_location));
        }
    }
}
