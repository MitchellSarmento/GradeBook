package com.sarmento.mitchell.gradesaver2.model;

import android.content.ContentValues;
import android.content.Context;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.sarmento.mitchell.gradesaver2.adapters.ScheduleEditAdapter;

import java.util.Locale;

public class Schedule {
    public static final int MONDAY    = 0;
    public static final int TUESDAY   = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY  = 3;
    public static final int FRIDAY    = 4;
    public static final int SATURDAY  = 5;
    public static final int SUNDAY    = 6;

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

        active.put(MONDAY, false);
        active.put(TUESDAY, false);
        active.put(WEDNESDAY, false);
        active.put(THURSDAY, false);
        active.put(FRIDAY, false);
        active.put(SATURDAY, false);
        active.put(SUNDAY, false);
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
        updateDay(Schedule.MONDAY, holder);
        updateDay(Schedule.TUESDAY, holder);
        updateDay(Schedule.WEDNESDAY, holder);
        updateDay(Schedule.THURSDAY, holder);
        updateDay(Schedule.FRIDAY, holder);
        updateDay(Schedule.SATURDAY, holder);
        updateDay(Schedule.SUNDAY, holder);

        // update the Schedule in the database
        DBHelper db = new DBHelper(context);
        ContentValues updateValues = new ContentValues();

        updateValues.put(DBHelper.KEY_SCHEDULES_ON_MONDAY, active.get(MONDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_ON_TUESDAY, active.get(TUESDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_ON_WEDNESDAY, active.get(WEDNESDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_ON_THURSDAY, active.get(THURSDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_ON_FRIDAY, active.get(FRIDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_ON_SATURDAY, active.get(SATURDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_ON_SUNDAY, active.get(SUNDAY));

        updateValues.put(DBHelper.KEY_SCHEDULES_LOCATION_MONDAY, locations.get(MONDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_LOCATION_TUESDAY, locations.get(TUESDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_LOCATION_WEDNESDAY, locations.get(WEDNESDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_LOCATION_THURSDAY, locations.get(THURSDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_LOCATION_FRIDAY, locations.get(FRIDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_LOCATION_SATURDAY, locations.get(SATURDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_LOCATION_SUNDAY, locations.get(SUNDAY));

        updateValues.put(DBHelper.KEY_SCHEDULES_START_MONDAY, startTimes.get(MONDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_START_TUESDAY, startTimes.get(TUESDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_START_WEDNESDAY, startTimes.get(WEDNESDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_START_THURSDAY, startTimes.get(THURSDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_START_FRIDAY, startTimes.get(FRIDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_START_SATURDAY, startTimes.get(SATURDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_START_SUNDAY, startTimes.get(SUNDAY));

        updateValues.put(DBHelper.KEY_SCHEDULES_END_MONDAY, endTimes.get(MONDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_END_TUESDAY, endTimes.get(TUESDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_END_WEDNESDAY, endTimes.get(WEDNESDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_END_THURSDAY, endTimes.get(THURSDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_END_FRIDAY, endTimes.get(FRIDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_END_SATURDAY, endTimes.get(SATURDAY));
        updateValues.put(DBHelper.KEY_SCHEDULES_END_SUNDAY, endTimes.get(SUNDAY));

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
