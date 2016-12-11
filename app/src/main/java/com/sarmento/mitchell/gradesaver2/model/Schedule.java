package com.sarmento.mitchell.gradesaver2.model;

import android.content.ContentValues;
import android.content.Context;
import android.util.SparseArray;

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

    private String location;
    private SparseArray<String> startTimes;
    private SparseArray<String> endTimes;

    // constructor for creating a new Schedule
    public Schedule() {
        startTimes = new SparseArray<>();
        endTimes   = new SparseArray<>();

        for (Day day : Day.values()) {
            int dayValue = day.getValue();
            startTimes.put(dayValue, "");
            endTimes.put(dayValue, "");
        }
    }

    // constructor for loading an existing Schedule
    public Schedule(String location, SparseArray<String> startTimes, SparseArray<String> endTimes) {
        this.location   = location;
        this.startTimes = startTimes;
        this.endTimes   = endTimes;
    }

    public SparseArray<String> getStartTimes() {
        return startTimes;
    }

    public SparseArray<String> getEndTimes() {
        return endTimes;
    }

    public String getLocation() {
        return location;
    }

    public void updateSchedule(Context context, ScheduleEditAdapter.ViewHolder holder,
                               int termPosition, int sectionPosition) {
        for (Day day : Day.values()) {
            updateDay(day.getValue(), holder);
        }

        // update the Schedule in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();

        String[] startKeys = {DBHelper.KEY_SCHEDULES_START_MONDAY,
                DBHelper.KEY_SCHEDULES_START_TUESDAY, DBHelper.KEY_SCHEDULES_START_WEDNESDAY,
                DBHelper.KEY_SCHEDULES_START_THURSDAY, DBHelper.KEY_SCHEDULES_START_FRIDAY,
                DBHelper.KEY_SCHEDULES_START_SATURDAY, DBHelper.KEY_SCHEDULES_START_SUNDAY};
        String[] endKeys = {DBHelper.KEY_SCHEDULES_END_MONDAY,
                DBHelper.KEY_SCHEDULES_END_TUESDAY, DBHelper.KEY_SCHEDULES_END_WEDNESDAY,
                DBHelper.KEY_SCHEDULES_END_THURSDAY, DBHelper.KEY_SCHEDULES_END_FRIDAY,
                DBHelper.KEY_SCHEDULES_END_SATURDAY, DBHelper.KEY_SCHEDULES_END_SUNDAY};

        updateValues.put(DBHelper.KEY_SCHEDULES_LOCATION, location);
        for (Day day : Day.values()) {
            int dayValue = day.getValue();
            updateValues.put(startKeys[dayValue], startTimes.get(dayValue));
            updateValues.put(endKeys[dayValue], endTimes.get(dayValue));
        }

        db.updateSchedule(updateValues, termPosition, sectionPosition);
    }

    private void updateDay(int day, ScheduleEditAdapter.ViewHolder holder) {
        location = holder.location.getText().toString();
        startTimes.put(day, holder.startTimes.get(day).getText().toString());
        endTimes.put(day, holder.endTimes.get(day).getText().toString());
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
