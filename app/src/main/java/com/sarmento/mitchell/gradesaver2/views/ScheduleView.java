package com.sarmento.mitchell.gradesaver2.views;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.ScheduleAdapter;
import com.sarmento.mitchell.gradesaver2.model.Schedule;
import com.sarmento.mitchell.gradesaver2.model.Section;
import com.sarmento.mitchell.gradesaver2.model.Term;

import java.util.ArrayList;
import java.util.List;

public class ScheduleView extends LinearLayout {
    Context context;
    List<Schedule> schedules;
    SparseArray<List<String>> locations;
    SparseArray<List<String>> startTimes;
    SparseArray<List<String>> endTimes;

    public ScheduleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, R.layout.view_schedule, this);
    }

    public void init(Term term) {
        schedules  = new ArrayList<>();
        locations  = new SparseArray<>();
        startTimes = new SparseArray<>();
        endTimes   = new SparseArray<>();

        for (Schedule.Day day : Schedule.Day.values()) {
            int dayValue = day.getValue();
            locations.put(dayValue, new ArrayList<String>());
            startTimes.put(dayValue, new ArrayList<String>());
            endTimes.put(dayValue, new ArrayList<String>());
        }

        // get the Schedules for this Term
        List<Section> sections = term.getSections();
        for (Section section : sections) {
            for (Schedule.Day day : Schedule.Day.values()) {
                extractScheduleInfo(day.getValue(), section);
            }
        }

        // set the adapters for relevant days
        int[] headerViewIds  = {R.id.header_monday, R.id.header_tuesday, R.id.header_wednesday,
            R.id.header_thursday, R.id.header_friday, R.id.header_saturday, R.id.header_sunday};
        int[] detailsViewIds = {R.id.details_monday, R.id.details_tuesday, R.id.details_wednesday,
            R.id.details_thursday, R.id.details_friday, R.id.details_saturday, R.id.details_sunday};
        for (Schedule.Day day : Schedule.Day.values()) {
            int dayValue = day.getValue();
            TextView header = (TextView) findViewById(headerViewIds[dayValue]);
            RecyclerView details = (RecyclerView) findViewById(detailsViewIds[dayValue]);
            if (locations.get(dayValue).size() > 0) {
                ScheduleAdapter adapter = new ScheduleAdapter(dayValue, sections);

                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                details.setLayoutManager(layoutManager);
                details.addItemDecoration(new DividerItemDecoration(context,
                        layoutManager.getOrientation()));
                details.setAdapter(adapter);

                if (header.getVisibility() == View.GONE) {
                    header.setVisibility(View.VISIBLE);
                }
            } else {
                header.setVisibility(View.GONE);
                details.setAdapter(new ScheduleAdapter(dayValue, sections));
            }
        }
    }

    private void extractScheduleInfo(int day, Section section) {
        Schedule schedule = section.getSchedule();

        String startTime = schedule.getStartTimes().get(day);
        String endTime   = schedule.getEndTimes().get(day);

        if (!startTime.equals("") && !endTime.equals("")) {
            locations.get(day).add(schedule.getLocation());
            startTimes.get(day).add(startTime);
            endTimes.get(day).add(endTime);
        }
    }
}
