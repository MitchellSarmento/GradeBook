package com.sarmento.mitchell.gradesaver2.widgets;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.activities.TermsActivity;

import java.util.Calendar;
import java.util.Locale;

public class DueDatesWidgetProvider extends AppWidgetProvider {
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

        // set AlarmManager to update the widget when the date changes
        setAlarm(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] widgetIds) {
        // create the remote adapter
        Intent adapter = new Intent(context, DueDatesWidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
        adapter.setData(Uri.parse(adapter.toUri(Intent.URI_INTENT_SCHEME)));

        // set the remote adapter
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_due_dates);
        views.setRemoteAdapter(R.id.list_due_dates, adapter);

        // set the header text
        setHeaderText(context, views);

        // set the header to open the app when clicked
        setHeaderClick(context, views);

        // update the widgets
        ComponentName component = new ComponentName(context, DueDatesWidgetProvider.class);
        manager.updateAppWidget(component, views);
        manager.notifyAppWidgetViewDataChanged(widgetIds, R.id.list_due_dates);
        super.onUpdate(context, manager, widgetIds);
    }

    private static void setHeaderText(Context context, RemoteViews views) {
        // get the text to display on the header
        Calendar today   = Calendar.getInstance();
        Locale locale    = Locale.getDefault();
        String dayOfWeek = today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale);
        String month     = today.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
        int dayOfMonth   = today.get(Calendar.DAY_OF_MONTH);

        // set the header text
        views.setTextViewText(R.id.widget_week_day, dayOfWeek);
        views.setTextViewText(R.id.widget_month_day, month + " " + dayOfMonth);
    }

    private void setHeaderClick(Context context, RemoteViews views) {
        // set the PendingIntent
        Intent headerIntent = new Intent("android.intent.action.MAIN");
        headerIntent.addCategory("android.intent.category.LAUNCHER");
        headerIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        headerIntent.setComponent(new ComponentName(context.getPackageName(),
                TermsActivity.class.getName()));
        PendingIntent openApp = PendingIntent.getActivity(context, 0, headerIntent, 0);
        views.setOnClickPendingIntent(R.id.widget_header, openApp);
    }

    private static void setAlarm(Context context) {
        // set the alarm for the start of the next day
        Calendar alarmDate = Calendar.getInstance();
        alarmDate.setTimeInMillis(System.currentTimeMillis());
        alarmDate.set(Calendar.SECOND, 0);
        alarmDate.set(Calendar.MINUTE, 0);
        alarmDate.set(Calendar.HOUR, 0);
        alarmDate.set(Calendar.AM_PM, Calendar.AM);
        alarmDate.add(Calendar.DAY_OF_MONTH, 1);

        // create the alarm and set the PendingIntent
        AlarmManager alarm   = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent        = new Intent(context, DueDatesWidgetReceiver.class);
        PendingIntent update = PendingIntent.getBroadcast(context, 0, intent, 0);

        // set the alarm as repeating or exact depending on version
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarm.setRepeating(AlarmManager.RTC, alarmDate.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, update);
        } else {
            alarm.setExact(AlarmManager.RTC, alarmDate.getTimeInMillis(), update);
        }
    }

    /*
     * Used to update the widget from an Activity or an AlarmManager
     */
    public static void updateWidget(Context context, boolean dateChanged) {
        // update the ListViews
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int widgetIds[] = manager.getAppWidgetIds(
                new ComponentName(context, DueDatesWidgetProvider.class));
        manager.notifyAppWidgetViewDataChanged(widgetIds, R.id.list_due_dates);

        // update the header text if the date has changed
        if (dateChanged) {
            // set the header text
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_due_dates);
            setHeaderText(context, views);

            // update the widgets
            ComponentName component = new ComponentName(context, DueDatesWidgetProvider.class);
            manager.updateAppWidget(component, views);

            // reset the alarm if version requires setExact
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setAlarm(context);
            }
        }
    }
}
