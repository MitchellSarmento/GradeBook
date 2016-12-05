package com.sarmento.mitchell.gradesaver2.model;

import android.util.SparseArray;
import android.util.SparseBooleanArray;

public class Schedule {
    public static final int MONDAY    = 0;
    public static final int TUESDAY   = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY  = 3;
    public static final int FRIDAY    = 4;
    public static final int SATURDAY  = 5;
    public static final int SUNDAY    = 6;

    private SparseBooleanArray active;
    private SparseArray<String> startTimes;
    private SparseArray<String> endTimes;
    private SparseArray<String> locations;

    public Schedule() {
        active     = new SparseBooleanArray();
        startTimes = new SparseArray<>();
        endTimes   = new SparseArray<>();
        locations  = new SparseArray<>();
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

    public void updateSchedule(int day, String startTime, String endTime, String location) {
        startTimes.put(day, startTime);
        endTimes.put(day, endTime);
        locations.put(day, location);
    }
}
