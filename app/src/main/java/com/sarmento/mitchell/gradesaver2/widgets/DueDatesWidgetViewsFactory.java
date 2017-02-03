package com.sarmento.mitchell.gradesaver2.widgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.DBHelper;
import com.sarmento.mitchell.gradesaver2.model.DueDate;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DueDatesWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private DBHelper db;
    private List<DueDate> dueDates;
    private Locale locale = Locale.getDefault();

    public DueDatesWidgetViewsFactory(Context context, Intent intent) {
        this.context = context;
        db           = new DBHelper(context);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // get and sort upcoming due dates
        dueDates = db.getUpcomingDueDates();
        Collections.sort(dueDates);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return dueDates.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        DueDate dueDate = dueDates.get(i);
        Calendar date   = dueDate.getDate();

        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_due_dates_list_item);
        views.setTextViewText(R.id.widget_section_name, dueDate.getSectionName());
        views.setTextViewText(R.id.widget_assignment_name, dueDate.getDueDateName());

        // remove irrelevant time data from date for comparison
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        // get date of previous DueDate and remove irrelevant time data
        Calendar previousDate = null;
        if (i != 0) {
            previousDate = dueDates.get(i - 1).getDate();
            previousDate.set(Calendar.HOUR_OF_DAY, 0);
            previousDate.set(Calendar.MINUTE, 0);
            previousDate.set(Calendar.SECOND, 0);
            previousDate.set(Calendar.MILLISECOND, 0);
        }

        if (i == 0 || previousDate.before(date)) {
            String dayOfWeek = date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale);
            String month     = date.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale);
            int dayOfMonth   = date.get(Calendar.DAY_OF_MONTH);

            views.setViewVisibility(R.id.widget_item_header, View.VISIBLE);
            views.setTextViewText(R.id.widget_item_header, dayOfWeek + ", " + month + " " + dayOfMonth);
        } else {
            views.setViewVisibility(R.id.widget_item_header, View.GONE);
        }

        if (dueDate.isComplete()) {
            views.setInt(R.id.widget_item, "setBackgroundColor", Color.LTGRAY);
        } else {
            views.setInt(R.id.widget_item, "setBackgroundColor",
                    ContextCompat.getColor(context, R.color.color_a));
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
