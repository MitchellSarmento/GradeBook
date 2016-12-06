package com.sarmento.mitchell.gradesaver2.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
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
    int day;
    List<Section> sections;
    private List<String> sectionNames;
    private List<String> locations;
    private List<String> startTimes;
    private List<String> endTimes;

    public ScheduleAdapter(int day, List<Section> sections) {
        this.day      = day;
        this.sections = sections;
        sectionNames  = new ArrayList<>();
        locations     = new ArrayList<>();
        startTimes    = new ArrayList<>();
        endTimes      = new ArrayList<>();

        init();
    }

    private void init() {
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

    /*public void init(Term term) {
        schedules  = new ArrayList<>();
        locations  = new SparseArray<>();
        startTimes = new SparseArray<>();
        endTimes   = new SparseArray<>();

        locations.put(Schedule.MONDAY, new ArrayList<String>());
        locations.put(Schedule.TUESDAY, new ArrayList<String>());
        locations.put(Schedule.WEDNESDAY, new ArrayList<String>());
        locations.put(Schedule.THURSDAY, new ArrayList<String>());
        locations.put(Schedule.FRIDAY, new ArrayList<String>());
        locations.put(Schedule.SATURDAY, new ArrayList<String>());
        locations.put(Schedule.SUNDAY, new ArrayList<String>());

        startTimes.put(Schedule.MONDAY, new ArrayList<String>());
        startTimes.put(Schedule.TUESDAY, new ArrayList<String>());
        startTimes.put(Schedule.WEDNESDAY, new ArrayList<String>());
        startTimes.put(Schedule.THURSDAY, new ArrayList<String>());
        startTimes.put(Schedule.FRIDAY, new ArrayList<String>());
        startTimes.put(Schedule.SATURDAY, new ArrayList<String>());
        startTimes.put(Schedule.SUNDAY, new ArrayList<String>());

        endTimes.put(Schedule.MONDAY, new ArrayList<String>());
        endTimes.put(Schedule.TUESDAY, new ArrayList<String>());
        endTimes.put(Schedule.WEDNESDAY, new ArrayList<String>());
        endTimes.put(Schedule.THURSDAY, new ArrayList<String>());
        endTimes.put(Schedule.FRIDAY, new ArrayList<String>());
        endTimes.put(Schedule.SATURDAY, new ArrayList<String>());
        endTimes.put(Schedule.SUNDAY, new ArrayList<String>());

        // get the Schedules for this Term
        List<Section> sections = term.getSections();
        for (Section section : sections) {
            extractScheduleInfo(Schedule.MONDAY, section);
            extractScheduleInfo(Schedule.TUESDAY, section);
            extractScheduleInfo(Schedule.WEDNESDAY, section);
            extractScheduleInfo(Schedule.THURSDAY, section);
            extractScheduleInfo(Schedule.FRIDAY, section);
            extractScheduleInfo(Schedule.SATURDAY, section);
            extractScheduleInfo(Schedule.SUNDAY, section);
        }

        // set the adapters for relevant days
        List<String> locationsMo = locations.get(Schedule.MONDAY);
        if (locationsMo.size() > 0) {
            List<String> startTimesMo = startTimes.get(Schedule.MONDAY);
            List<String> endTimesMo   = endTimes.get(Schedule.MONDAY);
            ScheduleAdapter adapter = new ScheduleAdapter(locationsMo, startTimesMo, endTimesMo);

            RecyclerView detailsMo = (RecyclerView) findViewById(R.id.details_monday);
            detailsMo.setLayoutManager(new LinearLayoutManager(context));
            detailsMo.setAdapter(adapter);
        }
    }

    private void extractScheduleInfo(int day, Section section) {
        Schedule schedule = section.getSchedule();

        boolean active   = schedule.getActive().get(day);
        String location  = schedule.getLocations().get(day);
        String startTime = schedule.getStartTimes().get(day);
        String endTime   = schedule.getEndTimes().get(day);

        if (active) {
            locations.get(day).add(location);
            startTimes.get(day).add(startTime);
            endTimes.get(day).add(endTime);
        }
    }*/

    /*private List<Term> terms;

    public TermAdapter(List<Term> terms) {
        this.terms = terms;
    }

    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_terms, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TermAdapter.ViewHolder holder, int termPosition) {
        TermButton button = holder.button;
        button.init(terms.get(termPosition), termPosition);
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TermButton button;

        private ViewHolder(View v) {
            super(v);
            button = (TermButton) v.findViewById(R.id.button_term);
        }
    }*/
}
