package com.sarmento.mitchell.gradesaver2.model;

import android.content.ContentValues;
import android.content.Context;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.sarmento.mitchell.gradesaver2.adapters.ScheduleEditAdapter;

import java.util.Locale;

public class Schedule {
    public enum Day {
        MONDAY(0), TUESDAY(1), WEDNESDAY(2), THURSDAY(3), FRIDAY(4), SATURDAY(5), SUNDAY(6);

        private int value;

        Day(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private SparseBooleanArray active;
    private SparseArray<String> locations;
    private SparseArray<String> startTimes;
    private SparseArray<String> endTimes;

    // constructor for creating a new Schedule
    public Schedule() {
        active     = new SparseBooleanArray();
        locations  = new SparseArray<>();
        startTimes = new SparseArray<>();
        endTimes   = new SparseArray<>();

        for (Day day : Day.values()) {
            active.put(day.getValue(), false);
        }
    }

    // constructor for loading an existing Schedule
    public Schedule(SparseBooleanArray active, SparseArray<String> locations,
                    SparseArray<String> startTimes, SparseArray<String> endTimes) {
        this.active     = active;
        this.locations  = locations;
        this.startTimes = startTimes;
        this.endTimes   = endTimes;
    }

    public SparseBooleanArray getActive() {
        return active;
    }

    public SparseArray<String> getStartTimes() {
        return startTimes;
    }

    public SparseArray<String> getEndTimes() {
        return endTimes;
    }

    public SparseArray<String> getLocations() {
        return locations;
    }

    public void updateSchedule(Context context, ScheduleEditAdapter.ViewHolder holder,
                               int termPosition, int sectionPosition) {
        for (Day day : Day.values()) {
            updateDay(day.getValue(), holder);
        }

        // update the Schedule in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();

        String[] onKeys = {DBHelper.KEY_SCHEDULES_ON_MONDAY, DBHelper.KEY_SCHEDULES_ON_TUESDAY,
                DBHelper.KEY_SCHEDULES_ON_WEDNESDAY, DBHelper.KEY_SCHEDULES_ON_THURSDAY,
                DBHelper.KEY_SCHEDULES_ON_FRIDAY, DBHelper.KEY_SCHEDULES_ON_SATURDAY,
                DBHelper.KEY_SCHEDULES_ON_SUNDAY};
        String[] locationKeys = {DBHelper.KEY_SCHEDULES_LOCATION_MONDAY,
                DBHelper.KEY_SCHEDULES_LOCATION_TUESDAY, DBHelper.KEY_SCHEDULES_LOCATION_WEDNESDAY,
                DBHelper.KEY_SCHEDULES_LOCATION_THURSDAY, DBHelper.KEY_SCHEDULES_LOCATION_FRIDAY,
                DBHelper.KEY_SCHEDULES_LOCATION_SATURDAY, DBHelper.KEY_SCHEDULES_LOCATION_SUNDAY};
        String[] startKeys = {DBHelper.KEY_SCHEDULES_START_MONDAY,
                DBHelper.KEY_SCHEDULES_START_TUESDAY, DBHelper.KEY_SCHEDULES_START_WEDNESDAY,
                DBHelper.KEY_SCHEDULES_START_THURSDAY, DBHelper.KEY_SCHEDULES_START_FRIDAY,
                DBHelper.KEY_SCHEDULES_START_SATURDAY, DBHelper.KEY_SCHEDULES_START_SUNDAY};
        String[] endKeys = {DBHelper.KEY_SCHEDULES_END_MONDAY,
                DBHelper.KEY_SCHEDULES_END_TUESDAY, DBHelper.KEY_SCHEDULES_END_WEDNESDAY,
                DBHelper.KEY_SCHEDULES_END_THURSDAY, DBHelper.KEY_SCHEDULES_END_FRIDAY,
                DBHelper.KEY_SCHEDULES_END_SATURDAY, DBHelper.KEY_SCHEDULES_END_SUNDAY};

        for (Day day : Day.values()) {
            int dayValue = day.getValue();
            updateValues.put(onKeys[dayValue], active.get(dayValue));
            updateValues.put(locationKeys[dayValue], locations.get(dayValue));
            updateValues.put(startKeys[dayValue], startTimes.get(dayValue));
            updateValues.put(endKeys[dayValue], endTimes.get(dayValue));
        }

        db.updateSchedule(updateValues, termPosition, sectionPosition);
    }

    private void updateDay(int day, ScheduleEditAdapter.ViewHolder holder) {
        boolean isActive = holder.switches.get(day).isChecked();
        active.put(day, isActive);

        if (isActive) {
            locations.put(day, holder.locations.get(day).getText().toString());
            startTimes.put(day, holder.startTimes.get(day).getText().toString());
            endTimes.put(day, holder.endTimes.get(day).getText().toString());
        } else {
            locations.delete(day);
            startTimes.delete(day);
            endTimes.delete(day);
        }
    }

    // used to convert TimePicker output to a String
    public static String timeToString(int hour, int minute, boolean is24HourView) {
        if (is24HourView) {
            return String.format(Locale.getDefault(), "%02d", hour) + ":" +
                    String.format(Locale.getDefault(), "%02d", minute);
        } else {
            String amPm = " AM";

            if (hour == 0) {
                hour = 12;
            }
            else if (hour >= 12) {
                amPm = " PM";
                if (hour > 12) {
                    hour -= 12;
                }
            }

            return String.valueOf(hour) + ":" +
                    String.format(Locale.getDefault(), "%02d", minute) + amPm;
        }
    }
}
