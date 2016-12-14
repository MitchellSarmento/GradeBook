package com.sarmento.mitchell.gradesaver2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Schedule;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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

            String startTime = schedule.getStartTimes().get(day);
            String endTime   = schedule.getEndTimes().get(day);

            if (!startTime.equals("") && !endTime.equals("")) {
                sectionNames.add(section.getSectionName());
                locations.add(schedule.getLocation());
                startTimes.add(startTime);
                endTimes.add(endTime);
            }
        }

        sort();
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

    private void sort() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < startTimes.size()-1; i++) {
                try {
                    if (dateFormat.parse(startTimes.get(i+1))
                            .before(dateFormat.parse(startTimes.get(i)))) {
                        sorted = false;
                        Collections.swap(sectionNames, i, i+1);
                        Collections.swap(locations, i, i+1);
                        Collections.swap(startTimes, i, i+1);
                        Collections.swap(endTimes, i, i+1);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView scheduleView;

        private ViewHolder(View v) {
            super(v);
            scheduleView = (TextView) v.findViewById(R.id.view_schedule);
        }
    }
}
