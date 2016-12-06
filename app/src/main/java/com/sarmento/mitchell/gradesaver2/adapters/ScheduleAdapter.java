package com.sarmento.mitchell.gradesaver2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Schedule;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    private List<String> sectionNames;
    private List<String> locations;
    private List<String> startTimes;
    private List<String> endTimes;

    public ScheduleAdapter(int day, List<Section> sections) {
        sectionNames = new ArrayList<>();
        locations    = new ArrayList<>();
        startTimes   = new ArrayList<>();
        endTimes     = new ArrayList<>();

        init(day, sections);
    }

    private void init(int day, List<Section> sections) {
        for (Section section : sections) {
            Schedule schedule = section.getSchedule();
            if (schedule.getActive().get(day)) {
                sectionNames.add(section.getSectionName());
                locations.add(schedule.getLocations().get(day));
                startTimes.add(schedule.getStartTimes().get(day));
                endTimes.add(schedule.getEndTimes().get(day));
            }
        }
    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_schedule, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position) {
        holder.scheduleView.setText(sectionNames.get(position) + "\n" +
                startTimes.get(position) + " - " + endTimes.get(position) + "\n" +
                locations.get(position));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView scheduleView;

        private ViewHolder(View v) {
            super(v);
            scheduleView = (TextView) v.findViewById(R.id.view_schedule);
        }
    }
}
